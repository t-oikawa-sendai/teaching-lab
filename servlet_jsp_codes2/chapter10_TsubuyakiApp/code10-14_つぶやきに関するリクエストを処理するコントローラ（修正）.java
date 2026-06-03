// コード10-14 つぶやきに関するリクエストを処理するコントローラ（修正）
package servlet; 

// サーブレット関連（jakarta.servlet.* など）のインポート文は省略しています
import java.util.ArrayList;
import java.util.List;
import model.Mutter;
import model.PostMutterLogic;
import model.User;
09
@WebServlet("/Main")
public class Main extends HttpServlet {
private static final long serialVersionUID = 1L;
13
解説①
Main.java
（servletパッケージ）