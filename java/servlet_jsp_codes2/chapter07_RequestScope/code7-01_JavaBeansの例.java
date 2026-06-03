// コード7-1 JavaBeansの例
package model;
import java.io.Serializable; 

public class Human implements Serializable {
private String name;
private int age; 

public Human() { }
public Human(String name, int age) {
this.name = name;
this.age = age;
}
public String getName() { return name; }
public void setName(String name) { this.name = name; }
public int getAge() { return age; }
public void setAge(int age) { this.age = age; }
}
JavaBeansのルール①　直列化可能である
　直列化を可能にするためにjava.io.Serializableインタフェースを実装しま
す。直列化とは、インスタンスのフィールドの内容をバイト列に変換して
ファイルなどに保存し、それをまたインスタンスに復元する技術です。
JavaBeansを作ったり利用したりするだけであれば、詳しい内容を理解する
必要はありません。
JavaBeansのルール②　クラスはpublicでパッケージに所属する
package文でパッケージ宣言を行い、クラスをpublicで修飾します。
ルール② Human.java
（modelパッケージ）