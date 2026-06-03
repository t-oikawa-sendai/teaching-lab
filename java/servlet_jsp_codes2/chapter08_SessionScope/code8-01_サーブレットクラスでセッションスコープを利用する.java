// コード8-1 サーブレットクラスでセッションスコープを利用する
// セッションスコープに保存するインスタンスの生成
Human human = new Human();
human.setName("湊 雄輔");
human.setAge(23);

// HttpSessionインスタンスの取得
HttpSession session = request.getSession();

// セッションスコープにインスタンスを保存
session.setAttribute("human", human);
11
// セッションスコープからインスタンスを取得
Human h = (Human)session.getAttribute("human"); 

// セッションスコープからインスタンスを削除
session.removeAttribute("human"); 
解説①　セッションスコープの取得
HttpSessionインスタンスは、HttpServletRequest インスタンスの
getSession( )で取得します。
解説①
解説②