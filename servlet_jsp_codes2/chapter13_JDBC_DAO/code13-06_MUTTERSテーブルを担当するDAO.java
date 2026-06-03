// コード13-6 MUTTERSテーブルを担当するDAO
package dao; 
 02
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Mutter;
 11
public class MuttersDAO {
// データベース接続に使用する情報
private final String JDBC_URL =
 "jdbc:h2:tcp://localhost/~/dokoTsubu";
private final String DB_USER = "sa";
private final String DB_PASS = "";
 17
public List<Mutter> findAll() {
List<Mutter> mutterList = new ArrayList<>();
// JDBCドライバを読み込む
try {
Class.forName("org.h2.Driver");
} catch (ClassNotFoundException e) {
throw new IllegalStateException(
 "JDBCドライバを読み込めませんでした");
MuttersDAO.java
（dao パッケージ）

392
第 IV
部
}
// データベース接続
try (Connection conn = DriverManager.getConnection(
 JDBC_URL, DB_USER, DB_PASS)) {
 28
// SELECT文の準備
String sql =
 "SELECT ID,NAME,TEXT FROM MUTTERS ORDER BY ID DESC";
PreparedStatement pStmt = conn.prepareStatement(sql);
 32
// SELECT文を実行
ResultSet rs = pStmt.executeQuery();
 35
// SELECT文の結果をArrayListに格納
while (rs.next()) {
int id = rs.getInt("ID");
String userName = rs.getString("NAME");
String text = rs.getString("TEXT");
Mutter mutter = new Mutter(id, userName, text);
mutterList.add(mutter);
}
} catch (SQLException e) {
e.printStackTrace();
return null;
}
return mutterList;
}
public boolean create(Mutter mutter) {
// JDBCドライバを読み込む
try {
IDの大きい順に
検索結果を並べる