// コード11-4 リクエストパラメータの文字コードを指定するフィルタ
package filter; 

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
SetEncodingFilter.java
（filter パッケージ）

326
第 IV
部
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;

@WebFilter("/*")
public class SetEncodingFilter extends HttpFilter implements Filter
public void doFilter(HttpServletRequest request, 
 HttpServletResponse response, FilterChain chain)
throws IOException, ServletException {
request.setCharacterEncoding("UTF-8");
chain.doFilter(request, response);
}
}
解説①　@WebFilterアノテーションを付与
　フィルタには@WebFilterアノテーションを付与する必要があります。こ
のアノテーションが付けられたフィルタは、Webアプリケーション開始時に
インスタンス化されます。
@WebFilterアノテーションの付与
@WebFilter("/設定するサーブレットクラスのURLパターン")
※ jakarta.servlet.annotation.WebFilterをインポートする必要がある。
　フィルタを設定するサーブレットクラスは、@WebFilterアノテーションで
指定します。複数のサーブレットクラスに設定するには「/*」を使用します。
次に設定例を挙げておきます。
解説① 解説②
解説③