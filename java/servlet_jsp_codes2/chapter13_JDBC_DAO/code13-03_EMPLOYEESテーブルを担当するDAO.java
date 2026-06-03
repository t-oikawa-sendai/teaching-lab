// コード13-3 EMPLOYEESテーブルを担当するDAO
package dao; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Employee;
 11
public class EmployeesDAO {
// データベース接続に使用する情報
private final String JDBC_URL =
 "jdbc:h2:tcp://localhost/~/example";
private final String DB_USER = "sa";
private final String DB_PASS = "";
 17
public List<Employee> findAll() {
List<Employee> empList = new ArrayList<>();
// JDBCドライバを読み込む
try {
EmployeesDAO.java
（dao パッケージ）
Employeeクラスをインポート