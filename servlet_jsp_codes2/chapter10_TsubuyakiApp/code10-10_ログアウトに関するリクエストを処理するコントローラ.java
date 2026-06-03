// コード10-10 ログアウトに関するリクエストを処理するコントローラ
package servlet; 

// サーブレット関連（jakarta.servlet.* など）のインポート文は省略しています

@WebServlet("/Logout")
public class Logout extends HttpServlet {
private static final long serialVersionUID = 1L;
08
protected void doGet(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {

// セッションスコープを破棄
HttpSession session = request.getSession();
session.invalidate();

// ログアウト画面にフォワード
RequestDispatcher dispatcher = 
 request.getRequestDispatcher("WEB-INF/jsp/logout.jsp");
dispatcher.forward(request, response);
}
}
　メイン画面については、コード10-9（p.281）にログアウト用のリンクを
追加します（コード10-11）。
Logout.java
（servlet パッケージ）