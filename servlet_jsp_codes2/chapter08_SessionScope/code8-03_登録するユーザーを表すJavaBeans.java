// コード8-3 登録するユーザーを表すJavaBeans
package model; 

import java.io.Serializable;

public class User implements Serializable {
private String id;
private String name;
private String pass;

public User() { }
public User(String id, String name, String pass) {
this.id = id;
this.name = name;
this.pass = pass;
セッションスコープを使うプログラム実行時の注意点
セッションスコープを使用するプログラムの実行結果がおかしいと
きは、ブラウザをすべて閉じてから実行し直す。
User.java
（modelパッケージ）

226
第 III
部
}
public String getId() { return id; }
public String getPass() { return pass; }
public String getName() { return name; }
}