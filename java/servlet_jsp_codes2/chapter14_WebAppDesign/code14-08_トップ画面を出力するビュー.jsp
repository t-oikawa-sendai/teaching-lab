<%-- コード14-8 トップ画面を出力するビュー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>スッキリ商店</title>
</head>
<body>
<ul>
<li><a href="LoginServlet">ログイン</a></li>
<li>ユーザー登録</li>
</ul>
</body>
</html>
　ログインを制御するサーブレットは、前述したように、基本ルートである
ログイン成功時の画面遷移のみを実装します（次のコード14-9）。