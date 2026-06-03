// コード14-5 ログイン処理を担当するBO
package model; 

import dao.AccountsDAO;

public class LoginLogic {
public boolean execute(Login login) { 
AccountsDAO dao = new AccountsDAO();
Account account = dao.findByLogin(login);
testFindByLoginOK: 成功しました
testFindByLoginNG: 成功しました
LoginLogic.java
（model パッケージ）

428
第
V
部
return account != null; 
} 
}
⑤ BOのテスト
DAO同様、④で作成したBOの動作を確認します。