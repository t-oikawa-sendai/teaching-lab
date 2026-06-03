// コード8-4 ユーザー登録を行うモデル
package model; 

public class RegisterUserLogic {
public boolean execute(User user) {
// 登録処理（実際の登録処理は行わない）
return true;
}
}
　次のコード8-5では、リクエストパラメータ「action」の値によって、処理
や遷移先を振り分ける点に注目してください。