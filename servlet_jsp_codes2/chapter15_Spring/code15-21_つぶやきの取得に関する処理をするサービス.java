// コード15-21 つぶやきの取得に関する処理をするサービス
package com.example.demo.model; 

// Spring関連のインポート文は省略しています
import java.util.List;
import com.example.demo.dao.MuttersDAO;

PostMutterLogic.java
（com.example.demo.modelパッケージ）
GetMutterLogic.java
（com.example.demo.modelパッケージ）

482
第
V
部
@Service
public class GetMutterListService {
public List<Mutter> execute() {
MuttersDAO dao = new MuttersDAO();
List<Mutter> mutterList = dao.findAll();
return mutterList;
}
}
今回は作成したDAOを利用しているが、Spring Data JPAを使
用すると、DAOを自動的に生成してくれるぞ。
なにそれ！　最高やん！
15.6.4 コントローラクラスの作成
　これまでリクエストを処理していたサーブレットを、Springのコントロー
ラに変更します。
　@Controllerが付いている以外に、次の点が変わっています。
・クラス名の末尾: 「Controller」を追加
・パッケージ: 「com.example.demo.controller」に配置
・ doGet/doPostメソッド: ハンドラメソッドに変更
・リクエストパラメータの取得: ハンドラメソッドの引数で受け取る
・ HttpSessionの取得: ハンドラメソッドの引数で受け取る
・サービスの取得: 依存性注入を使用
・フォワード/リダイレクト先の指定: ハンドラメソッドの戻り値で指定
　次のコード15-22は、コード10-5のLoginをコントローラクラスに変更した
ものです。