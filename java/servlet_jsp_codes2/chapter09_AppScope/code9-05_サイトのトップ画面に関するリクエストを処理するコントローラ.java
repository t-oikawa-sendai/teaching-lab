// コード9-5 サイトのトップ画面に関するリクエストを処理するコントローラ
package servlet; 

SiteEVLogic.java
（model パッケージ）
MinatoIndex.java
（servlet パッケージ）

252
第 III
部
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.SiteEV;
import model.SiteEVLogic;

@WebServlet("/MinatoIndex")
public class MinatoIndex extends HttpServlet {
private static final long serialVersionUID = 1L;
17
protected void doGet(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// アプリケーションスコープに保存されたサイト評価を取得
ServletContext application = this.getServletContext();
SiteEV siteEV = (SiteEV)application.getAttribute("siteEV");
22
// サイト評価の初期化（初回リクエスト時実行）
if (siteEV == null) {
siteEV = new SiteEV();
}
27
// リクエストパラメータの取得
request.setCharacterEncoding("UTF-8");
String action = request.getParameter("action");
解説①