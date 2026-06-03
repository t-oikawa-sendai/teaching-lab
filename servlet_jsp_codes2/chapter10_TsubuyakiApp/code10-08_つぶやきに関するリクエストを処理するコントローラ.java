// コード10-8 つぶやきに関するリクエストを処理するコントローラ
package servlet; 

// サーブレット関連（jakarta.servlet.* など）のインポート文は省略しています
import java.util.ArrayList;
import java.util.List;
import model.Mutter;
import model.User;

@WebServlet("/Main")
public class Main extends HttpServlet {
private static final long serialVersionUID = 1L;
12
protected void doGet(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// つぶやきリストをアプリケーションスコープから取得
ServletContext application = this.getServletContext();
List<Mutter> mutterList = 
 (List<Mutter>)application.getAttribute("mutterList");
// 取得できなかった場合は、つぶやきリストを新規作成して
// アプリケーションスコープに保存
if (mutterList == null) {
mutterList = new ArrayList<>();
Main.java
（servlet パッケージ）
解説①
解説②

280
第 III
部
application.setAttribute("mutterList", mutterList);
}

// ログインしているか確認するため
// セッションスコープからユーザー情報を取得
HttpSession session = request.getSession();
User loginUser = (User)session.getAttribute("loginUser");
28
if (loginUser == null) { // ログインしていない場合
// リダイレクト
response.sendRedirect("index.jsp");
return;
} else { // ログイン済みの場合
// フォワード
RequestDispatcher dispatcher = 
 request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
dispatcher.forward(request, response);
}
}
}
解説①　アプリケーションスコープからつぶやきリストを取得
　アプリケーションスコープから、つぶやきリスト（ArrayListインスタン
ス）を取得しています。リストに格納する型には、ジェネリクスを使い、つ
ぶやき情報を持つJavaBeansであるMutterを指定します。なお、ArrayList
クラスとListインタフェースは、java.utilパッケージに所属しているので、と
もにインポートする必要があります（4～5行目）。
解説②　つぶやきリストの作成
　アプリケーションスコープから取得できなかった（nullだった）場合は、つ
ぶやきリストがまだ存在していないことになります。そこで、つぶやきリス
解説③