#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Faker の profile() を使ってダミープロファイルデータを生成し、CSV に逐次出力するスクリプト。

ポイント:
  - macOS(Homebrew) + PEP 668 で pip install が弾かれやすいので、同一フォルダの .venv があれば自動でそちらで再実行する
  - faker.profile() の戻り値を CSV 化しやすい形に正規化して出力する

依存:
  - faker
"""

from __future__ import annotations

import csv
import os
import random
import sys
import time
from datetime import date, datetime
from pathlib import Path
from typing import Any, Dict, Iterable

_SCRIPT_DIR = Path(__file__).resolve().parent
_VENV_DIR = _SCRIPT_DIR / ".venv"
_VENV_PY = _VENV_DIR / "bin" / "python"

# .venv があるのに system Python で実行されている場合は venv 側へ再実行する。
if _VENV_PY.exists() and os.environ.get("FAKER_TEST_VENV_REEXEC") != "1":
    if Path(sys.prefix).resolve() != _VENV_DIR.resolve():
        os.environ["FAKER_TEST_VENV_REEXEC"] = "1"
        os.execv(str(_VENV_PY), [str(_VENV_PY), *sys.argv])

try:
    from faker import Faker
except ModuleNotFoundError as e:
    raise ModuleNotFoundError(
        "このスクリプトの実行には faker が必要です。\n"
        "\n"
        "macOS(Homebrew) の Python では system-wide の pip install が禁止(PEP 668)されることがあるため、"
        "このフォルダ配下で venv を作って入れてください:\n"
        "\n"
        "  /opt/homebrew/bin/python3 -m venv python/faker-test/.venv\n"
        "  python/faker-test/.venv/bin/python -m pip install -U pip\n"
        "  python/faker-test/.venv/bin/python -m pip install -r python/faker-test/requirements.txt\n"
        "  python/faker-test/.venv/bin/python python/faker-test/dummy_data_mysql_loder_ver2.py\n"
    ) from e

# ---------------------------------------------------------------------------
# 設定
# ---------------------------------------------------------------------------

RECORD_COUNT = 100_000
PROGRESS_INTERVAL = 10_000
CSV_FILENAME = "dummy_profile_data.csv"

# locale を増やしたい場合はここに追加
LOCALES: tuple[str, ...] = ("ja_JP", "en_US", "fr_FR", "de_DE", "en_IN")

# faker.profile(fields=...) で指定するフィールド（辞書のキー）
# ※ address は改行を含み得るので後で 1 行に正規化する
PROFILE_FIELDS: tuple[str, ...] = (
    "username",
    "name",
    "sex",
    "address",
    "mail",
    "birthdate",
    "job",
    "company",
)

# CSV の列順（先頭は連番）
CSV_COLUMNS: tuple[str, ...] = ("SeqNo", *PROFILE_FIELDS)


def _one_line(value: Any) -> str:
    s = "" if value is None else str(value)
    return s.replace("\r", " ").replace("\n", " ").strip()


def _to_iso_date(value: Any) -> str:
    if value is None:
        return ""
    if isinstance(value, date) and not isinstance(value, datetime):
        return value.isoformat()
    if isinstance(value, datetime):
        return value.date().isoformat()
    return _one_line(value)


def _normalize_profile(profile: Dict[str, Any]) -> Dict[str, str]:
    """
    faker.profile() の戻り値（値に date や改行を含む可能性あり）を
    CSV に書きやすい str へ正規化する。
    """
    normalized: Dict[str, str] = {}
    for k in PROFILE_FIELDS:
        v = profile.get(k)
        if k == "birthdate":
            normalized[k] = _to_iso_date(v)
        else:
            normalized[k] = _one_line(v)
    return normalized


def _profile_generator() -> Iterable[Dict[str, str]]:
    """
    locale をランダムに選びつつ profile を生成し、正規化した辞書を返すジェネレータ。
    """
    fakers = {loc: Faker(loc) for loc in LOCALES}
    locales = tuple(fakers.keys())

    while True:
        loc = random.choice(locales)
        faker = fakers[loc]
        raw = faker.profile(fields=list(PROFILE_FIELDS))
        yield _normalize_profile(raw)


def generate_csv() -> Path:
    script_dir = Path(__file__).resolve().parent
    csv_path = script_dir / CSV_FILENAME

    t_start = time.perf_counter()
    print(f"Profile CSV 生成開始: 目標 {RECORD_COUNT:,} 件（{PROGRESS_INTERVAL:,} 件ごとに進捗表示）")

    gen = _profile_generator()

    with csv_path.open("w", encoding="utf-8-sig", newline="") as fp:
        writer = csv.writer(fp, quoting=csv.QUOTE_MINIMAL, lineterminator="\n")
        writer.writerow(CSV_COLUMNS)

        for i in range(1, RECORD_COUNT + 1):
            prof = next(gen)
            row = [f"{i:07d}"] + [prof[col] for col in PROFILE_FIELDS]
            writer.writerow(row)

            if i % PROGRESS_INTERVAL == 0:
                elapsed = time.perf_counter() - t_start
                print(f"  進捗: {i:,} / {RECORD_COUNT:,} 件  経過 {elapsed:.2f} 秒")

    elapsed_total = time.perf_counter() - t_start
    print(f"Profile CSV 生成完了: {csv_path.resolve()}  合計経過 {elapsed_total:.2f} 秒")
    return csv_path.resolve()


def main() -> None:
    print("=== Profile ダミーデータ生成 開始 ===")
    generate_csv()
    print("=== Profile ダミーデータ生成 終了 ===")


if __name__ == "__main__":
    main()

