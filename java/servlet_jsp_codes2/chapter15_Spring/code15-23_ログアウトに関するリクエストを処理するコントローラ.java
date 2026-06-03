// コード15-23 ログアウトに関するリクエストを処理するコントローラ
package com.example.demo.controller; 

// Spring関連のインポート文は省略しています

@Controller
public class LogoutController {
@GetMapping("/Logout")
public String logout(HttpSession session) {
// セッションスコープを破棄
session.invalidate();
// ログアウト画面にフォワード 
return "logout";
}
}
　次のコード15-24は、コード13-9のMainをコントローラクラスに変更した
ものです。