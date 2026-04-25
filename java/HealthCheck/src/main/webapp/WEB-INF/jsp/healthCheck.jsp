<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スッキリ健康診断</title>
</head>
<body>
<h1>スッキリ健康診断</h1>
<form action="HealthCheck" method="post">
身長：<input type="text" name="height">(cm)<br>
体重：<input type="text" name="weight">(kg)<br>
<%-- 注意：Type="button" value="診断"にするとボタンは表示するが画面変遷はしない、エラーにもならない --%>
<input type="submit" value="診断">
</form>
</body>　
</html>

