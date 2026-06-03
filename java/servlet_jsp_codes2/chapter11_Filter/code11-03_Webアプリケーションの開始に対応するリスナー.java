// コード11-3 Webアプリケーションの開始に対応するリスナー
package listener; 

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CounterListener implements ServletContextListener {
public void contextInitialized(ServletContextEvent sce) {
ServletContext context = sce.getServletContext();
Integer count = 0;
context.setAttribute("count", count);
}
public void contextDestroyed(ServletContextEvent sce) {
}
}
解説①　@WebListenerアノテーションを付与
　リスナーには@WebListenerアノテーションを付与する必要があります。
このアノテーションを付けられたリスナーが、Webアプリケーション開始時
にインスタンス化されます。
@WebListenerアノテーションの付与
@WebListener
※ jakarta.servlet.annotation.WebListenerをインポートする必要がある｡
CounterListener.java
（listener パッケージ）
解説① 解説②