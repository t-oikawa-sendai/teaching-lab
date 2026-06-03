// コード15-19 ログインに関する処理をするサービス
package com.example.demo.model; 

// Spring関連のインポート文は省略しています

@Service
public class LoginService {
public boolean execute(User user) {
if (user.getPass().equals("1234")) { return true; }
LoginService.java
（com.example.demo.modelパッケージ）