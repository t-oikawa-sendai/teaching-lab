// コード10-2 つぶやきに関する情報を持つJavaBeans
package model; 
import java.io.Serializable;

public class Mutter implements Serializable {
private String userName; // ユーザー名
private String text; // つぶやき内容
public Mutter() {}
public Mutter(String userName, String text) {
this.userName = userName;
this.text = text;
}
public String getUserName() { return userName; }
public String getText() { return text; }
}
Mutter.java
（model パッケージ）