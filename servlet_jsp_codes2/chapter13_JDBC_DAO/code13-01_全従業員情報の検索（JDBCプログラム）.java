// コード13-1 全従業員情報の検索（JDBCプログラム）
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 06
public class SelectEmployees {
public static void main(String[] args) {
// JDBCドライバを読み込む
try {
Class.forName("org.h2.Driver");
} catch (ClassNotFoundException e) {
throw new IllegalStateException(
 "JDBCドライバを読み込めませんでした");
} 
// データベースに接続
try (Connection conn = DriverManager.getConnection(
 "jdbc:h2:tcp://localhost/~/example", "sa", "")) {
 17
// SELECT文を準備
String sql = "SELECT ID,NAME,AGE FROM EMPLOYEES";
PreparedStatement pStmt = conn.prepareStatement(sql);
 21
SelectEmployees.java
（デフォルトパッケージ）
接続先DB、ユーザ名、パスワード
SQLをDBに届けるPreparedStatementインスタンスを取得する