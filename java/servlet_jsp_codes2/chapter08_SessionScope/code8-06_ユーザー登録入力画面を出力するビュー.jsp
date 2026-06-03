<%-- コード8-6 ユーザー登録入力画面を出力するビュー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー登録</title> 
</head>
<body>
<form action="RegisterUser" method="post">
ログインID：<input type="text" name="id"><br>
パスワード：<input type="password" name="pass"><br>
名前：<input type="text" name="name"><br>
<input type="submit" value="確認">
</form>
registerForm.jsp（src/main/webapp/WEB-INF/jsp ディレクトリ）
解説②

230
第 III
部
</body>
</html>
解説②　パスワードボックス
inputタグのtype属性の値をpasswordにすると、パスワードボックスが作
成できます。パスワードボックスに入力した文字列は「●●●」のように伏
せ字で表示されます。