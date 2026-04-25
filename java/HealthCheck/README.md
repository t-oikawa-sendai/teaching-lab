# HealthCheck（スッキリ健康診断）

## 概要

Jakarta Servlet と JSP で、身長・体重から BMI と体型（痩せ型 / 普通 / 肥満）を表示するサンプルです。  
**書籍『スッキリわかるサーブレット＆JSP入門』掲載の MVC・健康診断例をもとに**、本リポジトリでは Maven・Eclipse（WTP）・Jetty など教材運用向けの改変を加えています。

---

## 出典（書誌・公式ページ）

| 項目 | 内容 |
|------|------|
| 書名 | **スッキリわかるサーブレット＆JSP入門 第4版** |
| 著者 | **国本 大悟** 著 |
| 監修 | **株式会社フレアリンク** |
| 出版社 | **株式会社インプレス**（インプレスブック事業部） |
| 発売 | 2024年4月（紙版の例: 2024年4月3日／インプレスブックス商品ページ記載） |
| ISBN | **978-4-295-01878-0**（9784295018780） |
| シリーズ公式（書籍案内・付属ソースの案内） | [https://sukkiri.jp/books/sukkiri_servlet4](https://sukkiri.jp/books/sukkiri_servlet4) |
| 出版社商品ページ（試し読み・誤植一覧など） | [https://book.impress.co.jp/books/1123101133](https://book.impress.co.jp/books/1123101133) |

### 書籍内の位置（章・節・ページ・コードリスト）

第4版の目次では、本サンプルに相当する内容は **第Ⅲ部** の次の流れで扱われます。

- **第6章 MVCモデルと処理の遷移**
- **第7章 リクエストスコープ**（節 **7.3 リクエストスコープを使ったプログラムの作成** で、身長・体重・BMI を扱う「スッキリ健康診断」の一連の例が掲載）

**紙面の対応箇所（第4版）:** **201ページ** に掲載のコードリスト **コード7-4、コード7-5、コード7-6、コード7-7、コード7-8**（身長・体重入力から BMI 表示までの一連の例）。

別刷・電子版などで**ページ番号がずれる場合**は、手元の書籍で **コード7-4〜7-8** を目印に照合してください。誤植・改ページは [インプレスブックスの商品ページ](https://book.impress.co.jp/books/1123101133) の情報も参照してください。

### 公式の掲載ソース（ZIP）

書籍用の掲載コードは、シリーズ公式ページから入手できる **ZIP（例: `sukkiri-servlet4-codes.zip`）** として配布されています。  
最新のダウンロード先・利用条件は **必ず次を参照**してください。

- [スッキリわかるサーブレット＆JSP入門 第4版（sukkiri.jp）](https://sukkiri.jp/books/sukkiri_servlet4)

---

## ライセンス・利用条件（原著・付属ソースに関する公式の趣旨）

付属ソースの配布ページ（sukkiri.jp）に掲載されている条件の要旨は次のとおりです（**利用前に必ず公式ページの全文を確認してください**）。

1. **著作権** … 掲載コードの著作権は **株式会社フレアリンク** に帰属する旨が示されています。  
2. **ライセンス** … 利用者には **Creative Commons Attribution-ShareAlike 4.0**（**CC BY-SA 4.0**、日本語では「表示—継承」）の**要約**および**当該ページの補足事項**に従う利用が許諾される旨が示されています。  
   - ライセンス条文（日本語リーガルコード）: [https://creativecommons.org/licenses/by-sa/4.0/legalcode.ja](https://creativecommons.org/licenses/by-sa/4.0/legalcode.ja)  
   - 人向けの要約（日本語）: [https://creativecommons.org/licenses/by-sa/4.0/deed.ja](https://creativecommons.org/licenses/by-sa/4.0/deed.ja)  
3. **書籍購入者向け** … 書籍購入者サポートの一環として提供される旨、**未購入者は利用できない**旨の記載があります。  
4. **免責** … **AS-IS・非保証**で提供され、株式会社フレアリンク・著者・出版社に保証・修補義務がない旨が示されています。  
5. **差異** … 編集等の理由により、配布ソフトウェアが書籍掲載と一部異なる場合がある旨、および**書籍の全コードを収録しているわけではない**旨が示されています。

### 本リポジトリ（`teaching-lab` の `HealthCheck`）について

- 上記書籍・付属ソースの**学習用サンプルを参考に改変**したものであり、`pom.xml`、Jetty プラグイン、Eclipse `.settings` / `.classpath`、`index.jsp` の追加、JSP の `action` や `RequestDispatcher` パス、`submit` ボタンなど、**書籍 ZIP とは一致しません**。  
- **再配布・講義利用を行う場合は**、公式サイトの**最新の利用条件**および **CC BY-SA 4.0**（表示・継承・その他条件）に従うこと、**未購入者への配布が公式に禁止されている場合はそれに従う**ことを確認してください。  
- 本 README は法務上のアドバイスではありません。**疑義がある場合は出版社・監修元の利用条件に従ってください。**

---

## ディレクトリ構成（抜粋）

```text
HealthCheck/
├── pom.xml
├── README.md
└── src/main/
    ├── java/
    │   ├── model/          … Health, HealthCheckLogic
    │   └── servlet/        … HealthCheck（@WebServlet("/HealthCheck")）
    └── webapp/
        ├── index.jsp
        └── WEB-INF/jsp/    … healthCheck.jsp, healthCheckResult.jsp
```

---

## 動かし方（要約）

### Maven + Jetty（Cursor / IntelliJ / CLI）

```bash
cd java/HealthCheck   # リポジトリルートからの相対パス
mvn jetty:run
```

ブラウザ: `http://localhost:8080/HealthCheck`（Jetty 設定ではコンテキスト `/` の例）

### Eclipse + Tomcat 10 以降

- **ファイル → インポート → Maven → 既存の Maven プロジェクト** で本フォルダを指定。  
- Tomcat にプロジェクトを追加して実行。  
- コンテキストが `HealthCheck` の場合の例: `http://localhost:8080/HealthCheck/HealthCheck`

---

## 技術上の注意

- Tomcat 10 以降は **`jakarta.servlet.*`**（`javax.servlet.*` は不可）。  
- JSP は `WEB-INF/jsp/` 配下のため、**URL から JSP には直接アクセスできません**（サーブレットから forward）。  
- 入力検証は教材レベルに留まっており、数値以外の入力などで **500 エラー** になり得ます。
