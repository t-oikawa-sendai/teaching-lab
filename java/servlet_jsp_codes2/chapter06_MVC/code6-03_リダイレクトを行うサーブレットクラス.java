// コード6-3 リダイレクトを行うサーブレットクラス
package servlet; 

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RedirectServlet")
public class RedirectServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

prot ected void doGet(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// リダイレクト
response.sendRedirect("UranaiServlet"); 
return; 
}
}
RedirectServlet.java
（servletパッケージ）
解説①
解説②

178
第 III
部
解説①　リダイレクト先の指定
　次のように、URLを使ったリダイレクト先の指定もできます。
response.sendRedirect("http://localhost:8080/example/UranaiServlet");
解説②　リダイレクトの安全な使い方
sendRedirect( )を呼び出した後も、サーブレットの処理はただちに終了せ
ず、そのまま後続の処理が実行されます。
　このとき、後続の処理の中にフォワード処理が含まれているとエラーが発
生します（付録A.2.5の4参照）。
　本書のコードではエラーは発生しませんが、将来的に処理内容が変更され
た場合に備えて、リダイレクトの直後にはreturn 文を記述し、処理を明示的
に終了させるのが一般的な慣習です。
　このようにしておくことで、意図しない処理の実行や例外の発生を防ぐこ
とができます。
6.2.8 フォワードとリダイレクトの使い分け
結局、フォワードとリダイレクトのどっちを使っても同じこと
なのかな？
「処理が転送される」という結果は同じだけど、転送の内容が
違うんだよ。ちょっと振り返ってみよう。
　フォワードとリダイレクトの動作や転送を比較すると、それぞれ次のよう
な特徴があります。