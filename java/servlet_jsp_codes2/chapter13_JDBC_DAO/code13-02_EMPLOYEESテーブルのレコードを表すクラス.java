// コード13-2 EMPLOYEESテーブルのレコードを表すクラス
package model; 

public class Employee {
private String id;
private String name;
private int age;

public Employee(String id, String name, int age) {
this.id = id;
this.name = name;
this.age = age;
}
Employee.java
（model パッケージ）

382
第 IV
部
public String getId() { return id; }
public String getName() { return name; }
public int getAge() { return age; } 
}
　次のコード13-3が、EMPLOYEESテーブルを担当するDAOクラスです｡