# dynamic-web-project-openai

## プロジェクト概要

- **プロジェクト名**：dynamic-web-project-openai  
- **概要**：Java Servlet/JSP + Tomcat10 を使ったWebアプリケーション  
- **構成**：MVCパターンで構成（タイマー計測機能）

この教材は、ブラウザ上で「重い処理」を実行し、その前後で **3種類のタイマー**（壁時計 / JST / ストップウォッチ）を使って計測結果を表示します。  
JSP（画面）と Java（処理）の役割を分けて、読みやすい構造にしています。

## 技術スタック

- Java 21（Eclipse Adoptium Temurin 25）
- Jakarta Servlet 6.0 / Jakarta JSP 3.1
- Tomcat 10.x（Homebrew経由）
- Maven（ビルドツール）
- Cursor（開発エディタ）

## プロジェクト構成（ディレクトリツリー形式）

```text
dynamic-web-project-openai/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── jp/ac/teachinglab/timer/
        │       ├── web/TimerMeasureServlet.java
        │       ├── logic/HeavyLoopProcessor.java
        │       ├── model/MeasureResult.java
        │       └── util/（TimeProvider.java, StopwatchUtil.java）
        └── webapp/
            ├── index.jsp
            ├── WEB-INF/
            │   ├── web.xml
            │   └── jsp/result.jsp
            └── assets/css/style.css
```

## 環境構築手順（Mac M4 MacBook Air想定）

### 1) Homebrew で Tomcat 10 をインストール

```bash
brew install tomcat
```

補足：
- Homebrew の Tomcat はバージョンが更新されることがあります（10.x/11.xなど）。
- **Tomcat 10以降**であれば Jakarta Servlet（`jakarta.servlet.*`）が使えます。

### 2) JAVA_HOME の設定（~/.zshrc）

```bash
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 21)' >> ~/.zshrc
source ~/.zshrc
java -version
echo $JAVA_HOME
```

補足：
- `JAVA_HOME` が未設定だと、Tomcat側で **JSPのコンパイルに失敗**することがあります。
- Java 21 を使う前提の例です。別バージョンを使う場合は `-v` を調整してください。

### 3) Tomcat の起動・停止コマンド

```bash
brew services start tomcat
brew services stop tomcat
```

補足：
- すでに Eclipse の内蔵Tomcatを起動している場合、ポート（8080）が競合しやすいです。
- 競合すると起動に失敗するので、どちらか片方を止めてください。

### 4) Maven でビルド（war作成）

プロジェクトルートで実行します。

```bash
mvn clean package
```

補足：
- 成功すると `target/dynamic-web-project-openai.war` が生成されます。
- `provided` 指定の依存（Servlet/JSP API）は、Tomcat側にある前提です（warに含めません）。

### 5) warファイルのデプロイ

Tomcat の `webapps` ディレクトリへ war を配置します（Tomcat起動中でも自動で展開されることがあります）。

```bash
cp target/dynamic-web-project-openai.war /opt/homebrew/opt/tomcat/libexec/webapps/
```

補足：
- Homebrew環境によりパスが異なる場合があります。その場合は、Tomcat の実体パスを確認してください。
- 配置後、ブラウザでアクセスして動作確認します。

## 動作確認

ブラウザで以下にアクセスします。

```text
http://localhost:8080/dynamic-web-project-openai/
```

補足：
- 表示された開始画面で「計測スタート」を押すと、重い処理が走り、結果画面に遷移します。

## 注意事項

- Tomcat 10以降は `jakarta.servlet` が前提です（`javax.servlet` は不可）
- `JAVA_HOME` が未設定だと JSP コンパイルエラーになることがあります
- Eclipse内蔵Tomcatと競合しないよう注意（主に **8080番ポート競合**）

