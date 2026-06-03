// コード14-7 トップに関するリクエストを処理するコントローラ（遷移のみ）
package servlet; 

// サーブレット関連（jakarta.servlet.* など）のインポート文は省略しています

@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
RequestDispatcher dispatcher = request.getRequestDispatcher(
 "WEB-INF/jsp/welcome.jsp");
WelcomeServlet.java
（servlet パッケージ）