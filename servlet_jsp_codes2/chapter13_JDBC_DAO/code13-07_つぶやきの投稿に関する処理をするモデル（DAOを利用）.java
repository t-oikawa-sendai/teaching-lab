// コード13-7 つぶやきの投稿に関する処理をするモデル（DAOを利用）
package model; 

import dao.MuttersDAO;

public class PostMutterLogic {
public void execute(Mutter mutter) { 
MuttersDAO dao = new MuttersDAO();
dao.create(mutter); 
}
}
MuttersDAOを使用してMUTTERSテーブルの全レコードを取得するGet
MutterListLogic.javaを新しく作成します（コード13-8）。