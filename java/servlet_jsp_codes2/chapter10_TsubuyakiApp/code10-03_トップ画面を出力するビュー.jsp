<%-- コード10-3 トップ画面を出力するビュー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>
<h1>どこつぶへようこそ</h1>
</body>
</html>
10.2.4 デフォルトページ
よし完成っと。アクセスするURLは、http://localhost:8080/doko
Tsubu/index.jspやな。
あ、でもそういえば、普段僕らがいろんなサイトを使うとき、
末尾に「index.jsp」なんて付けなくてもいいよね？
index.jsp（src/main/webappディレクトリ）

268
第 III
部
Web アプリケーション（Webサイト）で最初にリクエストする画面は、URL
の末尾からファイル名を省略してもリクエストできるのが一般的です。たと
えば、株式会社インプレスのトップページは本来「https://www.impress.
co.jp/index.html」ですが、末尾のindex.htmlを省略してもアクセス可能です。
　今回作成する「どこつぶ」でも、トップ画面を「http://localhost:8080/
dokoTsubu/」でリクエストできるようにしてみましょう。
さっき作ったindex.jspを修正しなくちゃいけないのかな？
実は、ファイルを修正する必要はないんだ。
末尾のファイル名を省略したURLでリクエストできるようにするしくみは、
プログラムではなく、アプリケーションサーバ（Webサーバ）の機能で実現
します。アプリケーションサーバは、ファイル名を省略したURLでリクエス
トされた場合、あらかじめ設定しておいたファイルを自動的に探してくれる
のです。
　たとえばApache Tomcatの場合、デフォルトでは「index.html→index.
htm→index.jsp→default.html→default.htm→default.jsp」の順にファイ
ルを探して、最初に見つかったファイルを使用する、という設定になってい
ます。そ の た め、トップ 画 面 を「index.jsp」で 作 成 す れ ば、「http://
localhost:8080/dokoTsubu/」でトップ画面にアクセスできるのです（次ペー
ジの図10-2）。
　このように、ファイル名やURLパターンを省略したURLでリクエストで
きるファイルのことをデフォルトページと呼びます。
　それでは、次の方法でデフォルトページにアクセスし、トップ画面が表示
されることを確認しましょう。
・「http://localhost:8080/dokoTsubu/」にブラウザでリクエストする。
・動的Webプロジェクト「dokoTsubu」を選択し、Eclipseの実行機能で実
行する。