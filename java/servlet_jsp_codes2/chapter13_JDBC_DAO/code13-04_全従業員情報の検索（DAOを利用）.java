// コード13-4 全従業員情報の検索（DAOを利用）
import java.util.List; 
import model.Employee;
import dao.EmployeesDAO;

public class SelectEmployees {
public static void main(String[] args) {
// EMPLOYEESテーブルの全レコードを取得
EmployeesDAO empDAO = new EmployeesDAO();
List<Employee> empList = empDAO.findAll();

// 取得したレコードの内容を出力
for (Employee emp : empList){
System.out.println("ID:" + emp.getId());
System.out.println("名前:" + emp.getName());
System.out.println("年齢:" + emp.getAge() + "¥n");
} 
}
}
JDBCプログラム特有のコードがないやん。これやったら私で
も書けるわ！
DAOパターンのメリットを再度確認しておきましょう。データベースを利
用するクラス（コード13-4）には、ConnectionやResultSetの利用、SQL文、
SelectEmployees.java
（デフォルトパッケージ）