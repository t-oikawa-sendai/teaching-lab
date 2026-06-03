// コード11-5 フィルタの動作を確認するサーブレットクラス（コード5-4）
 …（省略）…
@WebServlet("/FormServlet")
public class FormServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// リクエストパラメータを取得
// request.setCharacterEncoding("UTF-8");
String name = request.getParameter("name");
String gender = request.getParameter("gender");
 …（省略）…
11.3.3 フィルタの作り方
フィルタを作成する手順も確認しておこう。
　フィルタは、次の4つの作業で作成できます。
① @WebFilterアノテーションを付与し、設定するサーブレットクラスを指
定する。
② jakarta.servlet.HttpFilterクラスを継承する。
③ doFilter( )を実装する。
④ doFilter( )の「chain.doFilter(request, response);」の前後に前処理、後
処理を記述する。
Eclipseではフィルタを簡単に作成できます。その場合、①から③は自動
FormServlet.java
（servletパッケージ）
コメントアウト