// コード14-9 ログインに関するリクエストを処理するコントローラ（遷移のみ）
package servlet;

// サーブレット関連（jakarta.servlet.* など）のインポート文は省略しています

welcome.jsp（src/main/webapp/WEB-INF/jsp ディレクトリ）
LoginServlet.java
（servlet パッケージ）

432
第
V
部
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
RequestDispatcher dispatcher = request.getRequestDispatcher(
 "WEB-INF/jsp/login.jsp");
dispatcher.forward(request, response);
}
protected void doPost(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
RequestDispatcher dispatcher = request.getRequestDispatcher(
 "WEB-INF/jsp/loginOK.jsp");
dispatcher.forward(request, response);
}
}