<%-- コード9-6 サイトのトップ画面を出力するビュー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page import="model.SiteEV" %>
<% 
minatoIndex.jsp（src/main/webapp/WEB-INF/jsp ディレクトリ）

254
第 III
部
SiteEV siteEV = (SiteEV)application.getAttribute("siteEV"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>湊くんのページ</title>
</head>
<body>
<h1>湊くんのページへようこそ</h1>
<p>
<a href="MinatoIndex?action=like">よいね</a>：
<%= siteEV.getLike() %>人
<a href="MinatoIndex?action=dislike">よくないね</a>：
<%= siteEV.getDislike() %>人
</p>
<h2>湊くんとは！？</h2>
<p>・・・</p>
</body>
</html>
それそれっ！
あー！　「よくないね」をいっぱいにするなー！！