// コード11-2 フィールドを使用するサーブレットクラス
 …（省略）…
@WebServlet("/FieldCounterServlet")
public class FieldCounterServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
private Integer count;

public void init(ServletConfig config) throws ServletException {
super.init(config);
// 訪問回数を初期化
count = 0;
}
 22
protected void doGet(HttpServletRequest request, 
 HttpServletResponse response) 
 throws ServletException, IOException {
// 訪問回数を増加
count++;
… …（以下の処理は省略）…
}
}
　ただし、このようにサーブレットクラスのフィールドを利用するときは、次
の2点に注意する必要があります。
注意①　フィールドは外から参照できない
　サーブレットクラスのフィールドは、ほかのサーブレットクラスやJSPファ
イルから使用できません。たとえば、JSPファイルにフォワードして訪問回
数を出力する場合でも、フォワード先のJSPファイルでは訪問回数（Integer
インスタンス）を直接参照できません。
FieldCounterServlet.java
（servletパッケージ）
訪問回数を保存するフィールドを作成
init( )の処理を訪問回数の初期化のみに変更
init( )の処理を訪問回数の初期化のみに変更