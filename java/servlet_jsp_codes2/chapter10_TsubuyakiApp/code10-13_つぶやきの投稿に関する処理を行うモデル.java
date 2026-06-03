// コード10-13 つぶやきの投稿に関する処理を行うモデル
package model; 

PostMutterLogic.java
（model パッケージ）

290
第 III
部
import java.util.List;

public class PostMutterLogic {
public void execute(Mutter mutter, List<Mutter> mutterList){
mutterList.add(0, mutter); // 先頭に追加
}
}
解説①　つぶやきリストにつぶやきを保存
ArrayListのadd( )は、第1引数に格納位置（インデックス）を、第2引数に
格納するインスタンスを指定します。指定した位置にすでにインスタンスが
ある場合は、上書きではなく、指定した位置に新しく挿入し、以降のインス
タンスを1つずつ後ろにずらしてくれます｡
　前項で解説したように、サーブレットクラスMainにはつぶやき投稿をす
るdoPost( )を追加します（コード10-14）。