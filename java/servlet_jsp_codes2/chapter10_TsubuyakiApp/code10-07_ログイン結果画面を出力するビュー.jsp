<%-- コード10-7 ログイン結果画面を出力するビュー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
// セッションスコープからユーザー情報を取得
User loginUser = (User)session.getAttribute("loginUser");
index.jsp（src/main/webappディレクトリ）
ログイン用の
フォームを
追加
loginResult.jsp（src/main/webapp/WEB-INF/jsp ディレクトリ）

274
第 III
部
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>
<h1>どこつぶログイン</h1>
<% if (loginUser != null) { %>
<p>ログインに成功しました</p>
<p>ようこそ<%= loginUser.getName() %>さん</p>
<a href="Main">つぶやき投稿・閲覧へ</a>
<% } else { %>
<p>ログインに失敗しました</p>
<a href="index.jsp">トップへ</a> 
<% } %>
</body>
</html>
解説①　メイン画面へのリンク
　ここではまだメイン画面を作成していないので、このリンクをクリックす
ると「404ページ」が表示されます。
10.3.3 ログイン機能の動作確認
　ここまでのプログラムが作成できたら、次の手順で動作を確認しましょう。
［ログイン成功時の動作を確認する手順］
① トップ画面を表示する（10.2.4項）。
解説①