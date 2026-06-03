// コード15-3 リクエストパラメータの取得（パラメータ名を指定）
public String handle(
@RequestParam("user_name") String userName) { 
…
}
@RequestParamアノテーション
@RequestParam("パラメータ名") 変数の型 変数名
※ 変数名がリクエストパラメータ名と一致している場合、("パラメータ名")は省略可能
※ org.springframework.web.bind.annotation.RequestParamをインポートする必要がある
　なお、引数の型は、リクエストパラメータの値が代入できるものを指定し
ます。
 表15-2 リクエストパラメータの値と引数の型の対応
リクエストパラメータの値 ハンドラメソッドの引数型
文字列（例 : "abc"） String
整数（例 : "123"） int, Integer
小数（例 : "3.14"） double, Double
論理値（例 : "true" / "false"`） boolean, Boolean 
日付（例 : "2025-09-18"） LocalDate
　また、サーブレットでおなじみのHttpServletRequest、HttpServlet
Response、HttpSessionなどのオブジェクトも、ハンドラメソッドの引数で
受け取ることができます。
　受け取りたいオブジェクトの型を引数に定義しておくだけで、Springが自
動的に代入してくれます。
userNameにuser_nameの値が代入される