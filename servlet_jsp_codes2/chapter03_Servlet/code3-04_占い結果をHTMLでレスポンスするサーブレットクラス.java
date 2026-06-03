// コード3-4 占い結果をHTMLでレスポンスするサーブレットクラス
package servlet; 

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UranaiServlet")
public class UranaiServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request,
 HttpServletResponse response) 
 throws ServletException, IOException {
// 運勢をランダムで決定
String[] luckArray = { "超スッキリ", "スッキリ", "最悪" };
int index = (int)(Math.random() * 3);
String luck = luckArray[index];
// 実行日を取得
LocalDate date = LocalDate.now();
UranaiServlet.java
（servletパッケージ）
基本的にEclipseが
自動で追加する
解説①
解説②
補足①