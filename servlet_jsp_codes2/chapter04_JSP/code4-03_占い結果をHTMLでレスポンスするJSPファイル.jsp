<%-- コード4-3 占い結果をHTMLでレスポンスするJSPファイル --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ page import="java.time.LocalDate,
 java.time.format.DateTimeFormatter" %>
<%
// 運勢をランダムで決定
String[] luckArray = { "超スッキリ", "スッキリ", "最悪" };
int index = (int)(Math.random() * 3);
String luck = luckArray[index];

// 実行日を取得
LocalDate date = LocalDate.now();
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM月dd日");
String today = date.format(formatter);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スッキリ占い</title>
</head>
<body>
<p><%= today %>の運勢は「<%= luck %>」です</p>
</body>
</html>
uranai.jsp（src/main/webappディレクトリ）
解説①