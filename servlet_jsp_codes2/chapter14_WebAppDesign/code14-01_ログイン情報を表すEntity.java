// コード14-1 ログイン情報を表すEntity
package model; 

public class Login {
private String userId;
private String pass;
public Login(String userId, String pass) {
Login.java
（model パッケージ）

422
第
V
部
this.userId = userId;
this.pass = pass;
}
public String getUserId() { return userId; }
public String getPass() { return pass; }
}