// コード10-16 つぶやきに関するリクエストを処理するコントローラ（修正）
package servlet; 

// サーブレット関連（jakarta.servlet.* など）のインポート文は省略しています
import java.util.ArrayList;
import java.util.List;
import model.Mutter;
import model.PostMutterLogic;
import model.User;

@WebServlet("/Main")
public class Main extends HttpServlet {
private static final long serialVersionUID = 1L;
13
… …（doGet()は変更がないため省略）…

protected void doPost(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// リクエストパラメータの取得
request.setCharacterEncoding("UTF-8");
String text = request.getParameter("text");

// 入力値チェック
Main.java
（servlet パッケージ）