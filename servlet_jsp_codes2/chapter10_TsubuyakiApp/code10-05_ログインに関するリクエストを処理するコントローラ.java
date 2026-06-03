// コード10-5 ログインに関するリクエストを処理するコントローラ
package servlet; 

// サーブレット関連（jakarta.servlet.* など）のインポート文は省略しています
import model.LoginLogic;
import model.User;

LoginLogic.java
（model パッケージ）
Login.java
（servletパッケージ）

272
第 III
部
@WebServlet("/Login")
public class Login extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// リクエストパラメータの取得
request.setCharacterEncoding("UTF-8");
String name = request.getParameter("name");
String pass = request.getParameter("pass");
// Userインスタンス（ユーザー情報）の生成
User user = new User(name, pass);
// ログイン処理
LoginLogic loginLogic = new LoginLogic();
boolean isLogin = loginLogic.execute(user);
21
// ログイン成功時の処理
if (isLogin) { 
// ユーザー情報をセッションスコープに保存
HttpSession session = request.getSession();
session.setAttribute("loginUser", user);
} 
// ログイン結果画面にフォワード
RequestDispatcher dispatcher = 
 request.getRequestDispatcher(
 "WEB-INF/jsp/loginResult.jsp");
dispatcher.forward(request, response); 
}
}