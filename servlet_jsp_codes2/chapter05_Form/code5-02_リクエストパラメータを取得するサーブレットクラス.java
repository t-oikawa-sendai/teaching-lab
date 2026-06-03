// コード5-2 リクエストパラメータを取得するサーブレットクラス
 …（import文は省略）…
@WebServlet("/FormServlet")
public class FormServlet extends HttpServlet {
protected void doPost(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// リクエストパラメータの文字コードを指定
request.setCharacterEncoding("UTF-8");

// リクエストパラメータの取得
String name = request.getParameter("name");
String gender = request.getParameter("gender");
 …（以降は省略）…
}
}
解説①　リクエストパラメータの文字コードを指定する
URLエンコードで変換されたリクエストパラメータを元に戻すため、URL
エンコードで使用した文字コードを、setCharacterEncoding( )の引数に指定
します。これはお約束の処理と考えればよいでしょう。
解説②　リクエストパラメータを取得する
getParameter( )でリクエストパラメータの値を取得します。たとえば、リ
クエストパラメータ「name=minato」の値「minato」を取得するには、リ
クエストパラメータの名前である「name」を引数に指定します。
　リクエストパラメータはひんぱんに取得するので、次ページの構文を確認
しておきましょう。
注意①
注意②
解説①
解説②