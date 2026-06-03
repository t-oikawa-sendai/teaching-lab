<%-- コード4-1 JSPファイルの例 --%>
<% 
String name = "湊　雄輔";
int age = 23;
%>
<!DOCTYPE html>

108
第 II 部
<html>
<head>
<meta charset="UTF-8">
<title>JSPのサンプル</title>
</head>
<body>
私の名前は<%= name %>。年齢は<%= age %>才です。
</body>
</html>
　見慣れない記号を含んでいますが、文字が青色の箇所はJavaのコード、そ
れ以外はHTMLで記述されています。このように、JSPファイルはHTMLの
中にJavaのコードを埋め込んで作成します。このJSPファイルをリクエス
トして実行すると、次のコード4-2のHTMLが出力されます。