// コード15-20 つぶやきの投稿に関する処理をするサービス
package com.example.demo.model; 

// Spring関連(org.springframework.*)のインポート文は省略しています
import com.example.demo.dao.MuttersDAO;

@Service
public class PostMutterService {
public void execute(Mutter mutter) { 
MuttersDAO dao = new MuttersDAO();
dao.create(mutter);
}
}
　次のコード15-21は、コード13-8のGetMutterListLogicをサービスクラス
に変更したものです。