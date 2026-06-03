// コード9-1 サーブレットクラスでアプリケーションスコープを利用する
// アプリケーションスコープに保存するインスタンスの生成
Human human = new Human("湊 雄輔", 23);

// ServletContextインスタンスの取得
ServletContext application = this.getServletContext();

// アプリケーションスコープにインスタンスを保存
application.setAttribute("human", human);

// アプリケーションスコープからインスタンスを取得
Human h = (Human)application.getAttribute("human"); 
12
// アプリケーションコープからインスタンスを削除
application.removeAttribute("human");
解説①
解説②

246
第 III
部
解説①　アプリケーションスコープの取得
ServletContextインスタンスは、サーブレットクラスのスーパークラスで
あるHttpServletから継承したgetServletContext( )で取得できます。
アプリケーションスコープを取得する
ServletContext application = this.getServletContext( );
※ jakarta.servlet.ServletContextをインポートする必要がある。
※「this.」は省略可。
解説②　アプリケーションスコープの基本操作
　アプリケーションスコープにインスタンスを保存する方法と、保存したイ
ンスタンスの取得と削除の操作は、ほかのスコープと同じです。
アプリケーションスコープにインスタンスを保存する
application.setAttribute("属性名", インスタンス);
※ 第1引数には保存するインスタンスの属性名をString型で指定する。
※ 属性名は大文字と小文字を区別する。
※ 第2引数には保存するインスタンスを指定する。Object型のためあらゆるクラスのインスタンスを指定
できる。
※ すでに同じ属性名のインスタンスが保存されている場合、上書きされる。
アプリケーションスコープからインスタンスを取得する
取得するインスタンスの型 変数名 = 
　　(取得するインスタンスの型)application.getAttribute("属性名");
※ 引数には取得するインスタンスの属性名をString型で指定する。
※ 属性名は大文字と小文字を区別する。
※ 戻り値はObject型で返されるため、取得したインスタンスは元の型にキャストする必要がある。
※ 指定した属性名のインスタンスが保存されていない場合、nullを返す。