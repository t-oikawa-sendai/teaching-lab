// コード15-22 ログインに関するリクエストを処理するコントローラ
package com.example.demo.controller; 

// SpringとLombok関連のインポート文は省略しています
import com.example.demo.model.LoginService;
import com.example.demo.model.User;

@Controller
@RequiredArgsConstructor
public class LoginController {
private final LoginService loginService;
@PostMapping("/Login")
public String login(@RequestParam String name, 
 @RequestParam String pass, HttpSession session) {
// Userインスタンス（ユーザー情報）の生成
User user = new User(name, pass);
// ログイン処理
boolean isLogin = loginService.execute(user);

// ログイン成功時の処理
if (isLogin) {
// ユーザー情報をセッションスコープに保存
session.setAttribute("loginUser", user);
}
// ログイン結果画面にフォワード
return "loginResult"; 
}
}
　次のコード15-23は、コード10-10のLogoutをコントローラクラスに変更
LoginController.java
（com.example.demo.controllerパッケージ）

484
第
V
部
したものです。