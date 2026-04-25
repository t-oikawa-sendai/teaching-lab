package exercise21;

//スーパークラス
public class Employee {
 private String name;       // 従業員名
 private String department; // 部署
 private double salary; 	// 給与

 // コンストラクタ
 public Employee(String name, String department, double salary) {
     this.name = name;
     this.department = department;
     this.salary = salary;
 }

 public void work() {
     System.out.println(name + "さんは" + department + "に所属しています");
 }

 // ゲッター
 public String getName() {
     return name;
 }

 public String getDepartment() {
     return department;
 }

 public double getSalary() {
     return salary;
 }
}

