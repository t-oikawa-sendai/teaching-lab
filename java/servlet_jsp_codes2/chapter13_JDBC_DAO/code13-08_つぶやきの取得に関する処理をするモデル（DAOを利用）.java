// コード13-8 つぶやきの取得に関する処理をするモデル（DAOを利用）
package model; 

import java.util.List;
import dao.MuttersDAO;

public class GetMutterListLogic {
public List<Mutter> execute() {
MuttersDAO dao = new MuttersDAO();
List<Mutter> mutterList = dao.findAll();
return mutterList;
}
}
PostMutterLogic.java
（model パッケージ）
引数でつぶやきリストを
受け取らない
DAOを利用してつぶやき
を投稿
GetMutterListLogic.java
（model パッケージ）