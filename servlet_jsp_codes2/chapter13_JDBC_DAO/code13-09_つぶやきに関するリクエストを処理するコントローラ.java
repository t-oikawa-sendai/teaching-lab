// コード13-9 つぶやきに関するリクエストを処理するコントローラ
package servlet; 

// サーブレット関連（jakarta.servlet.* など）のインポート文は省略しています
import model.GetMutterListLogic;
import model.Mutter;
import model.PostMutterLogic;
import model.User;
08
@WebServlet("/Main")
public class Main extends HttpServlet {
private static final long serialVersionUID = 1L;
12
protected void doGet(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// つぶやきリストを取得して、リクエストスコープに保存
GetMutterListLogic getMutterListLogic = 
 new GetMutterListLogic();
List<Mutter> mutterList = getMutterListLogic.execute();
request.setAttribute("mutterList", mutterList);
18
// ログインしているか確認するため
// セッションスコープからユーザー情報を取得
HttpSession session = request.getSession();
User loginUser = (User)session.getAttribute("loginUser");

if (loginUser == null) { // ログインしていない
Main.java
（servlet パッケージ）
追加

396
第 IV
部
// リダイレクト
response.sendRedirect("index.jsp");
return;
} else { // ログイン済み
// フォワード
RequestDispatcher dispatcher = 
 request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
dispatcher.forward(request, response);
}
}
protected void doPost(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// リクエストパラメータの取得
request.setCharacterEncoding("UTF-8");
String text = request.getParameter("text");
38
// 入力値チェック
if (text != null && text.length() != 0) {
// セッションスコープに保存されたユーザー情報を取得
HttpSession session = request.getSession();
User loginUser = (User)session.getAttribute("loginUser");
44
// つぶやきを作成してつぶやきリストに追加
Mutter mutter = new Mutter(loginUser.getName(), text);
PostMutterLogic postMutterLogic = new PostMutterLogic();
postMutterLogic.execute(mutter);
} else {
// エラーメッセージをリクエストスコープに保存
request.setAttribute("errorMsg", 
 "つぶやきが入力されていません");