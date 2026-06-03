// コード14-4 AccountsDAOをテストするクラス
package test; 

import model.Account;
import model.Login;
import dao.AccountsDAO;

public class AccountsDAOTest {
public static void main(String[] args) {
testFindByLoginOK(); // ユーザーが見つかる場合のテスト
testFindByLoginNG(); // ユーザーが見つからない場合のテスト
}
public static void testFindByLoginOK() {
AccountsDAOTest.java
（test パッケージ）

426
第
V
部
Login login = new Login("minato", "1234");
AccountsDAO dao = new AccountsDAO();
Account result = dao.findByLogin(login);
if (result != null &&
 result.getUserId().equals("minato") &&
 result.getPass().equals("1234") &&
 result.getMail().equals("yusuke.minato@miyabilink.jp") &&
 result.getName().equals("湊 雄輔") &&
 result.getAge() == 23) {
System.out.println("testFindByLoginOK:成功しました");
} else {
System.out.println("testFindByLoginOK:失敗しました");
}
}
public static void testFindByLoginNG() {
Login login = new Login("minato", "12345");
AccountsDAO dao = new AccountsDAO();
Account result = dao.findByLogin(login);
if (result == null) {
System.out.println("testFindByLoginNG:成功しました");
} else {
System.out.println("testFindByLoginNG:失敗しました");
}
}
}
　このAccountsDAOTestは、mainメソッドを持つクラスなので一般的なJava
のクラスとして実行可能です。Eclipseの場合、ファイルを選択して右クリッ
ク→「実行」→「Javaアプリケーション」を選択して実行します。
　次のような結果が表示されたらテストは成功です。