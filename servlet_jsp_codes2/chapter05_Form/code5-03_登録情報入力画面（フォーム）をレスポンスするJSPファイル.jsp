<%-- コード5-3 登録情報入力画面（フォーム）をレスポンスするJSPファイル --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
form.jsp（src/main/webappディレクトリ）

148
第 II 部
<title>ユーザー登録もどき</title>
</head>
<body>
<form action="FormServlet" method="post">
名前：<br>
<input type="text" name="name"><br>
会員種別：<br>
一般<input type="radio" name="plan" value="regular">
プレミアム<input type="radio" name="plan" value="premium"><br>
<input type="submit" value="登録">
</form>
</body>
</html>
　次に、動的Webプロジェクト「example」に「servlet.FormServlet」を作
成します（コード5-4）。Eclipseを用いてdoPost( )を持つサーブレットクラ
スを作成するには、サーブレットクラスの作成時にdoGet( )ではなく、doPost( )
にチェックを入れてください（Web付録を参照）。