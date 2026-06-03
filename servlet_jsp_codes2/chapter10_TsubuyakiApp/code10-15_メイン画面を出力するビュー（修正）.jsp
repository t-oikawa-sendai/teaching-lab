<%-- コード10-15 メイン画面を出力するビュー（修正） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page import="model.User, model.Mutter, java.util.List" %>
<%
// セッションスコープに保存されたユーザー情報を取得
User loginUser = (User)session.getAttribute("loginUser");
// アプリケーションスコープに保存されたつぶやきリストを取得
List<Mutter> mutterList =
 (List<Mutter>)application.getAttribute("mutterList");
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
main.jsp（src/main/webapp/WEB-INF/jsp ディレクトリ）
インポートするクラスを追加 つぶやきリスト
の取得を追加