# HealthCheck（スッキリ健康診断）

## 概要

Jakarta Servlet + JSP で、身長・体重を入力して BMI と体型（痩せ型/普通/肥満）を表示します。

## 構成

```text
HealthCheck/
└── src/
    └── main/
        ├── java/
        │   ├── model/
        │   │   ├── Health.java
        │   │   └── HealthCheckLogic.java
        │   └── servlet/
        │       └── HealthCheck.java
        └── webapp/
            └── WEB-INF/
                └── jsp/
                    ├── healthCheck.jsp
                    └── healthCheckResult.jsp
```

## 動作確認（Eclipse + Tomcat 10+）

- Eclipse でプロジェクトを作成/インポートして「サーバーで実行」
- ブラウザで `http://localhost:8080/[プロジェクト名]/HealthCheck` にアクセス
- 身長・体重を入力して「診断」を押す

## 注意

- Tomcat 10以降は `jakarta.servlet.*` を使用します（`javax.servlet.*` は不可）
- JSP は `WEB-INF/jsp/` 配下に配置しています（直接URLアクセス不可）
- 数値チェックは省略しているため、数値以外の入力で 500 エラーになります

