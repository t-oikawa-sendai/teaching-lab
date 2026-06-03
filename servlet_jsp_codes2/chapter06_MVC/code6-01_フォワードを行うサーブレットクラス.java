// コード6-1 フォワードを行うサーブレットクラス
package servlet; 

import java.io.IOException;
import jakarta.servlet.RequestDispatcher; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
10
@WebServlet("/ForwardServlet")
public class ForwardServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

prot ected void doGet(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// フォワード
Requ estDispatcher dispatcher =
 request.getRequestDispatcher("WEB-INF/jsp/forward.jsp");
dispatcher.forward(request, response);
}
} 
　次ページのコード6-2のJSPファイルは、「WEB-INF/jspディレクトリ」内
ForwardServlet.java
（servlet パッケージ）
追加する部分（Eclipseが
自動でインポートする）