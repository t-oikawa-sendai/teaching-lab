#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
ダミー顧客データ（10万件）を UTF-8 BOM 付き CSV に逐次出力し、
任意で MySQL の LOAD DATA INFILE で取り込むためのスクリプト。

作成日: 2026-04-26

使用ライブラリ:
    - faker: 国籍別ロケールに応じた架空データ生成
    - mysql-connector-python: MySQL 接続および LOAD DATA INFILE 実行
    - 標準ライブラリ: csv, datetime, random, time
"""

from __future__ import annotations

import csv
import random
import re
import time
from datetime import date, timedelta
from pathlib import Path
from typing import Any, Dict

import mysql.connector
from faker import Faker

# ---------------------------------------------------------------------------
# 定数定義
# ---------------------------------------------------------------------------
# RECORD_COUNT: 生成する CSV の行数（ヘッダー除く）。負荷試験などで件数を変えたい場合は
#   この値だけを変更すればよい。
RECORD_COUNT = 100_000

# PROGRESS_INTERVAL: 進捗をコンソールに出す間隔（件数）。10000 ごとに表示する。
PROGRESS_INTERVAL = 10_000

# CSV_FILENAME: スクリプト配置ディレクトリに保存する CSV のファイル名。
CSV_FILENAME = "dummy_data.csv"

# MySQL 接続情報（環境に合わせてプレースホルダーを書き換える）
HOST = "localhost"
PORT = 3306
USER = "your_user"
PASSWORD = "your_password"
DATABASE = "sampledb"

# 国籍（日本語表記）と Faker の locale 文字列の対応。
# 新しい国を追加する場合は、この辞書に行を足し create_faker_instances が参照する。
NATIONALITY_TO_LOCALE: Dict[str, str] = {
    "日本": "ja_JP",
    "フランス": "fr_FR",
    "ドイツ": "de_DE",
    "米国": "en_US",
    "インド": "en_IN",
}

# E-mail のドメイン部分に使う、実在サービスに近いドメインの一覧。
# ユーザー名部分は Faker でロケールに沿った文字列を整形して付与する。
EMAIL_DOMAINS = (
    "gmail.com",
    "yahoo.co.jp",
    "yahoo.com",
    "outlook.com",
    "hotmail.com",
    "icloud.com",
    "live.com",
    "proton.me",
)

# CSV ヘッダーおよび LOAD DATA 時の列順（CREATE TABLE main の定義順と一致させる）
CSV_COLUMNS = (
    "SeqNo",
    "名前",
    "国籍",
    "性別",
    "email",
    "住所",
    "年齢",
    "携帯番号",
    "郵便番号",
    "生年月日",
)


def create_faker_instances() -> Dict[str, Faker]:
    """
    国籍ごとに Faker インスタンスを1つずつ生成し、辞書で返す。

    戻り値:
        キーが国籍（日本語）、値がそのロケール専用の Faker インスタンスの辞書。
        レコード生成のたびに Faker(...) を呼ばないため、10万件規模でも高速に近い。

    処理概要:
        NATIONALITY_TO_LOCALE を走査し、locale を指定した Faker を構築する。
    """
    # 各国籍1ロケール1インスタンスにまとめ、以降は辞書参照のみとする
    return {
        nationality: Faker(locale)
        for nationality, locale in NATIONALITY_TO_LOCALE.items()
    }


def _pick_nationality() -> str:
    """レコード1件分の国籍を等確率で選ぶ。"""
    return random.choice(tuple(NATIONALITY_TO_LOCALE.keys()))


def _sanitize_email_local_part(raw: str) -> str:
    """
    メールアドレスのローカル部に使えるよう、空白や記号を除去・置換する。

    Faker の user_name 等にスペースが含まれる場合があるため、
    実在ドメイン風の見た目を保ちつつ ASCII の安全な文字列に寄せる。
    """
    # 英数字とドット・アンダースコア・ハイフン以外をアンダースコアに
    cleaned = re.sub(r"[^a-zA-Z0-9._-]+", ".", raw.strip())
    cleaned = cleaned.strip("._-") or "user"
    return cleaned[:64]


def _random_birth_date(age: int, today: date) -> date:
    """
    今日基準で逆算し、指定年齢と整合する生年月日を一様ランダムに返す。

    0歳は「今日から過去1年以内」の誕生日とし、概ね0歳のままに収める。
    1歳以上は「今日時点でちょうど age 歳」となる日付範囲の中から選ぶ。
    うるう日などで date 構築が失敗する場合は日を 28 に落として調整する。
    """
    if age == 0:
        # 誕生日から今日までが1年未満になるよう日数オフセットで表現する
        return today - timedelta(days=random.randint(0, 364))

    def _shift_years(d: date, delta_years: int) -> date:
        y = d.year + delta_years
        try:
            return d.replace(year=y)
        except ValueError:
            # 2/29 などが存在しない年へのずれは 2/28 に寄せる
            return d.replace(year=y, month=2, day=28)

    # 直近の誕生日が今日以前であるような誕生日の上限（まだ age 歳）
    latest = _shift_years(today, -age)
    # その前年の同月同日の「翌日」が、まだ age 歳である範囲の下限
    earliest = _shift_years(latest, -1) + timedelta(days=1)
    span_days = (latest - earliest).days
    return earliest + timedelta(days=random.randint(0, max(0, span_days)))


def _build_email(faker: Faker) -> str:
    """ロケールに沿ったユーザー名風文字列と固定ドメイン一覧から E-mail を組み立てる。"""
    local_raw = faker.user_name()
    local = _sanitize_email_local_part(local_raw)
    domain = random.choice(EMAIL_DOMAINS)
    return f"{local}@{domain}"


def generate_record(seq_no: int, faker_instances: Dict[str, Faker]) -> Dict[str, Any]:
    """
    1件分のダミーレコードを生成して辞書で返す。

    引数:
        seq_no: 1始まりの連番。7桁ゼロ埋めの SeqNo に使う。
        faker_instances: create_faker_instances() の戻り値。

    戻り値:
        CSV_COLUMNS のキーに対応するフィールドを持つ辞書。

    処理概要:
        国籍をランダムに決め、対応する Faker で名前・住所・電話等を生成する。
        年齢を先に決め、生年月日は今日との整合を取った上でランダムに決める。
    """
    # 国籍を決め、その国用の Faker を取り出す（インスタンスの使い回し）
    nationality = _pick_nationality()
    faker = faker_instances[nationality]

    # 氏名は選択した国籍のロケールで生成する
    name = faker.name()

    # 性別は仕様どおり日本語ラベル
    gender = random.choice(("男", "女"))

    # メールは実在ドメイン風の一覧からドメインを選ぶ
    email = _build_email(faker)

    # 住所・電話・郵便番号はすべて同一ロケールの Faker に揃える
    address = faker.address().replace("\n", " ").replace("\r", " ")
    phone = faker.phone_number()
    postcode = faker.postcode()

    # 年齢は 0〜99 の一様乱数
    age = random.randint(0, 99)
    today = date.today()
    birth = _random_birth_date(age, today)

    return {
        "SeqNo": f"{seq_no:07d}",
        "名前": name,
        "国籍": nationality,
        "性別": gender,
        "email": email,
        "住所": address,
        "年齢": age,
        "携帯番号": phone,
        "郵便番号": postcode,
        "生年月日": birth.isoformat(),
    }


def generate_csv(faker_instances: Dict[str, Faker]) -> Path:
    """
    RECORD_COUNT 件の CSV を逐次書き込みで生成し、保存したパスを返す。

    引数:
        faker_instances: create_faker_instances() の戻り値。

    戻り値:
        生成した CSV の絶対パス（Path）。

    処理概要:
        utf-8-sig で開き BOM 付き UTF-8 とする。csv モジュールでクォート付き CSV とし、
        PROGRESS_INTERVAL 件ごとに進捗と経過秒を表示する。
    """
    script_dir = Path(__file__).resolve().parent
    csv_path = script_dir / CSV_FILENAME

    # 全体の所要時間も計測する
    t_start = time.perf_counter()
    print(f"CSV 生成開始: 目標 {RECORD_COUNT:,} 件（{PROGRESS_INTERVAL:,} 件ごとに進捗表示）")

    # utf-8-sig は先頭に BOM を付与する（Excel 等との互換も意識）
    with csv_path.open("w", encoding="utf-8-sig", newline="") as fp:
        # MySQL の LINES TERMINATED BY '\n' と一致させる（デフォルトは \\r\\n のため明示）
        writer = csv.writer(fp, quoting=csv.QUOTE_MINIMAL, lineterminator="\n")
        # ヘッダー行を先に1行書き出す
        writer.writerow(CSV_COLUMNS)

        # 1件ずつ生成して都度フラッシュに近い状態で書く（巨大リストは作らない）
        for i in range(1, RECORD_COUNT + 1):
            row_dict = generate_record(i, faker_instances)
            writer.writerow(row_dict[col] for col in CSV_COLUMNS)

            # 進捗表示（10,000 件ごと）
            if i % PROGRESS_INTERVAL == 0:
                elapsed = time.perf_counter() - t_start
                print(f"  CSV 生成進捗: {i:,} / {RECORD_COUNT:,} 件  経過 {elapsed:.2f} 秒")

    elapsed_total = time.perf_counter() - t_start
    print(f"CSV 生成完了: {csv_path}  合計経過 {elapsed_total:.2f} 秒")

    return csv_path.resolve()


def connect_mysql():
    """
    MySQL に接続し connection オブジェクトを返す。

    戻り値:
        mysql.connector.connection の接続オブジェクト。

    処理概要:
        定数 HOST, PORT, USER, PASSWORD, DATABASE を用いて connect する。
        接続はコミット制御のため autocommit=False とする。
    """
    return mysql.connector.connect(
        host=HOST,
        port=PORT,
        user=USER,
        password=PASSWORD,
        database=DATABASE,
        autocommit=False,
    )


def create_table(connection) -> None:
    """
    sampledb 上に main テーブルを CREATE TABLE IF NOT EXISTS で作成する。

    引数:
        connection: connect_mysql() で得た接続。

    戻り値:
        なし。

    処理概要:
        設計書どおりの列定義・utf8mb4 を実行する。
    """
    ddl = """
    CREATE TABLE IF NOT EXISTS main (
      SeqNo       CHAR(7),
      名前         VARCHAR(100),
      国籍         VARCHAR(20),
      性別         CHAR(1),
      email       VARCHAR(100),
      住所         VARCHAR(200),
      年齢         TINYINT UNSIGNED,
      携帯番号      VARCHAR(20),
      郵便番号      VARCHAR(20),
      生年月日      DATE
    ) CHARACTER SET utf8mb4;
    """
    cursor = connection.cursor()
    try:
        # main が無ければ作成する
        cursor.execute(ddl)
        connection.commit()
    finally:
        cursor.close()


def load_data_to_mysql(connection, csv_path: Path) -> None:
    """
    LOAD DATA INFILE で CSV を main に取り込む。

    引数:
        connection: connect_mysql() で得た接続。
        csv_path: 読み込む CSV のパス（絶対パス推奨）。

    戻り値:
        なし。

    処理概要:
        大量投入の前後でインデックス関連の最適化を行う。
        MyISAM では ALTER TABLE ... DISABLE KEYS が有効。
        InnoDB（デフォルトエンジン）では DISABLE KEYS の効果は限定的だが、
        仕様どおり文を発行する。ファイルは MySQL サーバから見えるパスであること。

        クライアントとサーバが別マシンの場合は、CSV をサーバ上のパスに置くか、
        運用ポリシーに応じて LOAD DATA LOCAL INFILE への変更を検討する。
    """
    cursor = connection.cursor()
    # MySQL が解釈しやすいよう POSIX 形式のスラッシュにそろえる
    path_str = str(csv_path.resolve()).replace("\\", "/").replace("'", "''")

    try:
        # 仕様: インデックス無効化（MyISAM で特に有効。InnoDB でも文自体は実行される）
        cursor.execute("ALTER TABLE main DISABLE KEYS")

        # 仕様どおりサーバ側ファイルを LOAD DATA INFILE で読む（パスは絶対パス）
        load_sql = (
            f"LOAD DATA INFILE '{path_str}' "
            "INTO TABLE main "
            "CHARACTER SET utf8mb4 "
            "FIELDS TERMINATED BY ',' "
            "ENCLOSED BY '\"' "
            "LINES TERMINATED BY '\n' "
            "IGNORE 1 LINES"
        )
        cursor.execute(load_sql)

        # インデックス再有効化
        cursor.execute("ALTER TABLE main ENABLE KEYS")

        connection.commit()
    except Exception:
        connection.rollback()
        raise
    finally:
        cursor.close()


def ask_and_load(csv_path: Path) -> None:
    """
    ユーザーに yes/no を尋ね、yes のときだけ MySQL 処理を実行する。

    引数:
        csv_path: 取り込み対象の CSV パス。

    戻り値:
        なし。

    処理概要:
        入力が 'yes'（大文字小文字無視）の場合のみ接続・テーブル作成・LOAD を行う。
        それ以外はメッセージを出して何もしない。
    """
    prompt = "MySQLにLOAD DATA INFILEしますか？(yes/no): "
    answer = input(prompt).strip().lower()

    if answer != "yes":
        # no または想定外の入力は安全側でロードしない
        print("MySQL への取り込みはスキップしました。")
        return

    # yes の場合のみ接続してテーブル作成・ロードを行う
    conn = connect_mysql()
    try:
        create_table(conn)
        load_data_to_mysql(conn, csv_path)
        print("LOAD DATA INFILE が正常に完了しました。")
    finally:
        conn.close()


def main() -> None:
    """
    全体制御: Faker 準備 → CSV 生成 → 対話的に MySQL ロード。

    引数・戻り値: なし。

    処理概要:
        処理開始時刻と終了時刻、および経過時間をコンソールに表示する。
    """
    t_main_start = time.perf_counter()
    print("=== ダミーデータ生成・任意 MySQL 取り込み 開始 ===")

    # 国籍別 Faker を先にすべて用意する
    faker_instances = create_faker_instances()

    # CSV を逐次書き込みで生成する
    csv_path = generate_csv(faker_instances)

    # ユーザーが希望した場合のみ MySQL に投入する
    ask_and_load(csv_path)

    elapsed_main = time.perf_counter() - t_main_start
    print(f"=== 全処理終了  合計経過 {elapsed_main:.2f} 秒 ===")


if __name__ == "__main__":
    main()
