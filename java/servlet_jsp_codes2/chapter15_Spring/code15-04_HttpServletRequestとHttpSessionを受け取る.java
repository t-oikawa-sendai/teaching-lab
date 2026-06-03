// コード15-4 HttpServletRequestとHttpSessionを受け取る
ハンドラメソッド
public String handle(
HttpServletRequest request, HttpSession session) {
…
}
name=湊&age=23
“湊” 23 HttpSession
インスタンス
送信
名前 湊
年齢 23
ハンドラメソッド
受け取る変数を
用意しておくと
❶
実行時に
渡しまーす
❷
DispatcherServlet
( ≒ Spring )
String
name
int
age
HttpSession
session
（ , , ）
 図15-3 ハンドラメソッドの引数
　図15-3のDispatcherServletは、Springがあらかじめ用意している特別な
サーブレットです。そのため、「DispatcherServlet ≒ Spring」とイメージす
ると、理解しやすいでしょう。
15.2.4 ハンドラメソッドの戻り値
ハンドラメソッドの戻り値は、ビューとなるJSPファイルの名前を指定し
ます。これにより、Spring（正確にはDispatcherServlet）が指定した名前
のJSPファイルにフォワードします。

454
第
V
部
フォワード
return "JSPファイルの名前";
※ 拡張子（.jsp）は不要。
※ JSPファイルは設定されたフォルダから検索されます。設定の詳細はWeb付録を参照してください。
　たとえば、"hello.jsp"に表示処理を依頼する場合は、「return "hello"」と
記述します。なお、フォワード先のJSPについては「15.4 ビューの開発」で
解説します。
コントローラ
そのビューに
フォワードしまーす
❷
ビューの名前を
戻すと
DispatcherServlet
( ≒ Spring ) ❶
hello.jsp
“hello”
 図15-4 ハンドラメソッドの戻り値
　フォワードのほかに、リダイレクトを行うこともできます。リダイレクト
を行う場合は、次のように redirect: を付けて指定します。
リダイレクト
return "redirect:URLパターン";
　たとえば、"Main"へリダイレクトする場合は、「return "redirect:Main"」