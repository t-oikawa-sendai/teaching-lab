# Dynamic Web Project（timer デモ）

## プロジェクト概要

- **プロジェクト名**：Dynamic Web Project（Eclipse 上の表示名）
- **概要**：Jakarta Servlet + Tomcat（ブラウザ表示）で「時間計測」を体験する教材用サンプル
- **内容**：ブラウザから「素数探索（重い処理）」を実行し、3種類のタイマーで処理時間を計測して表示

このプロジェクトは **Dynamic Web Project 前提**の構成です。  
ブラウザ表示は、Servlet が **HTML（CSS/JavaScript含む）を生成して返す**方式で、結果は **JSON** を返して画面側（JavaScript）が表示更新します。

## 技術スタック

- Java 21
- Jakarta Servlet（Tomcat 10以降 / `jakarta.servlet.*`）
- Tomcat 10.x / 11.x（Eclipse の Server Runtime で利用）
- Eclipse（Dynamic Web Project）

補足：
- このプロジェクトは **Maven（`pom.xml`）はありません**（Eclipse の Dynamic Web Project として動かす想定です）。

## プロジェクト構成（ディレクトリツリー形式）

```text
dynamic-web-project/
├── .classpath
├── .project
├── .settings/
└── src/
    └── main/
        ├── java/
        │   └── com/example/timer/
        │       └── TimerServlet.java
        └── webapp/
            ├── index.html
            └── META-INF/
                └── MANIFEST.MF
```

## 画面と処理の役割（初学者向け）

### 画面（ブラウザ側）

- `src/main/webapp/index.html`
  - 役割：`timer` へリダイレクトするだけの入口ページ

### 処理（サーバ側：Servlet）

- `src/main/java/com/example/timer/TimerServlet.java`
  - **GET**：画面（HTML + CSS + JavaScript）を返す  
  - **POST**：重い処理（素数探索）を実行し、計測結果を **JSON** で返す

## アクセス方法（URL）

ブラウザで、次のURLにアクセスします。

```text
http://localhost:8080/[プロジェクト名]/timer
```

補足：
- `index.html` は `timer` へ自動遷移します（`meta refresh`）。
- `TimerServlet` 側では `@WebServlet("/")` が指定されているため、Eclipse/Tomcat の設定によってはプロジェクト直下アクセスで動く場合があります。  
  まずは上記の `/timer` を基準にしてください。

## 環境構築・実行手順（dynamic-web-project-openai の手順に準拠）

### 1) Tomcat 10+ を用意する

Eclipse の Servers ビューで Tomcat 10/11 を追加します。  
（Homebrew で入れた Tomcat をランタイムに指定してもOKです）

```text
Eclipse: Servers → New → Apache → Tomcat v10.1 / v11.x
```

補足：
- **Tomcat 10以降は Jakarta** です。`javax.servlet` では動きません。

### 2) プロジェクトをサーバへ追加して起動

```text
Servers ビュー → Tomcat → Add and Remove... → Dynamic Web Project を追加 → Start
```

### 3) ブラウザでアクセス

```text
http://localhost:8080/[プロジェクト名]/timer
```

## 動作の流れ（何が起きているか）

1. ブラウザで `GET /timer`  
   → `TimerServlet` が HTML を返す（画面が表示される）
2. 「計測スタート」ボタン押下  
   → JavaScript が `POST /timer` を送る（`limit` を送信）
3. `TimerServlet` が重い処理（素数探索）を実行し、3種類のタイマーで計測  
   → 計測結果を JSON で返す
4. ブラウザ側が JSON を受け取り、画面の数値を更新する

## 注意事項

- **Tomcat 10以降は `jakarta.servlet` 前提**（`javax.servlet` は不可）
- ポート `8080` が競合すると Tomcat が起動できません  
  - Eclipse 内蔵 Tomcat と Homebrew の Tomcat を同時に動かさないよう注意してください

