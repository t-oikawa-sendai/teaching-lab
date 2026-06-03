<%-- コード10-12 ログアウト画面を出力するビュー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
main.jsp（src/main/webapp/WEB-INF/jsp ディレクトリ）
ログアウト用のリンクを追加
logout.jsp（src/main/webapp/WEB-INF/jsp ディレクトリ）

286
第 III
部
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>
<h1>どこつぶログアウト</h1>
<p>ログアウトしました</p>
<a href="index.jsp">トップへ</a>
</body>
</html>
10.5.3 ログアウト機能の動作確認
　ここまでのプログラムが作成できたら、次の手順で動作を確認しましょう。
［ログアウト時のメイン画面表示を確認する手順］
① トップ画面を表示する（10.2.4項）。
② 任意のユーザーでログインし、メイン画面を表示する。
③ メイン画面でログアウトし、トップ画面が表示されるのを確認する。