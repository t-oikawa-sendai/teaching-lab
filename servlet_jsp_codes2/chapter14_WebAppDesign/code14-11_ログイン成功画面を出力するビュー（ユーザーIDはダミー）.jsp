<%-- コード14-11 ログイン成功画面を出力するビュー（ユーザーIDはダミー） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スッキリ商店</title>
</head>
<body>
<p>ようこそ{ユーザーID}さん</p>
<a href="WelcomeServlet">トップへ</a>
</body>
</html>
⑦ 画面遷移のテスト
　作成した画面遷移が正しく行われるか、ブラウザを使ってテストします。
「http://localhost:8080/sukkiriShop/WelcomeServlet」にリクエストするか、
Eclipseの実行機能で「WelcomeServlet」を実行して、図14-10のように画
loginOK.jsp（src/main/webapp/WEB-INF/jsp ディレクトリ）
ダミーのユーザーID

434
第
V
部
面が遷移するのを確認しましょう。
「ログイン」をクリック
「トップへ」を
クリック
「ログイン」を
クリック
http://･･････
http://･･････ http://･･････
・ログイン
トップへ
ようこそ{ユーザーID}さん
・ユーザー登録
ユーザーID：
パスワード：
ログイン
 図14-10 画面遷移を確認する
14.3.3 コントローラとビューの仕上げ
⑧ サーブレットクラスの仕上げ
doGet()やdoPost( )に、リクエストパラメータを取得する処理、BOを呼び
出す処理、スコープの処理などを追加して、サーブレットクラスを完成させ
ます（コード14-12）。
もし、一度に完成させるのが大変だと感じたら、少しずつ処理を追加して
は実行し、問題が起きないことを確認しながら進めましょう。問題が起きた
場合でも、14.3.1項でDAOとBOは完成しているので、これらは調査の対象
から外せます。サーブレットに新しく追加した処理を中心に、問題の原因を
探しましょう。