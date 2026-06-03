// コード3-1 サーブレットクラスの基本形
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
protected void doGet(HttpServletRequest request,
 HttpServletResponse response)
 throws ServletException, IOException {
}
}
　サーブレットでは以下の3つのルール（コード3-1の①～③部分）に従って
クラス定義をします。これらは「お約束」なので難しく考えず、割り切って
覚えましょう。しかも、Eclipseでは、これらの「お約束」があらかじめ自
動で記述されますので、細かく覚える必要はありません。