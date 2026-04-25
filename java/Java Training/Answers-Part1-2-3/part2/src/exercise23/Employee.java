package exercise23;

//スーパークラス
public abstract class Employee {
 private String name;       // 従業員名
 private String department; // 部署
 private double salary; 	// 給与

 // コンストラクタ
 public Employee(String name, String department, double salary) {
     this.name = name;
     this.department = department;
     this.salary = salary;
 }

 // 業務を行うメソッド（サブクラスでオーバーライド）
 public abstract void work();

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

