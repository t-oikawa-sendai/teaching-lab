<%-- コード10-9 メイン画面を出力するビュー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
// セッションスコープに保存されたユーザー情報を取得
User loginUser = (User)session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>
<h1>どこつぶメイン</h1>
<p>
<%= loginUser.getName() %>さん、ログイン中
</p>
</body>
</html>
main.jsp（src/main/webapp/WEB-INF/jsp ディレクトリ）

282
第 III
部
10.4.3 メイン画面表示の動作確認
　ここまでのプログラムが作成できたら、次の手順でログイン時と未ログイ
ン時のメイン画面表示を確認しましょう。
［ログイン時のメイン画面表示を確認する手順］
① トップ画面を表示する（10.2.4項）。
② 任意のユーザーでログインし、ログイン結果画面を表示する。
③ ログイン結果画面で「つぶやき投稿・閲覧へ」のリンクをクリックする。
④ メイン画面が表示され、正しいユーザー名が出力されるのを確認する。
［未ログイン時のメイン画面表示を確認する手順］
① サーブレットクラスMainを次のいずれかの方法でリクエストする（すべ
てのブラウザを閉じてから行うこと）。
　・「http://localhost:8080/dokoTsubu/Main」にブラウザでリクエストする。
　・Main.javaを選択してEclipseの実行機能で実行する。
② トップ画面が表示されるのを確認する。