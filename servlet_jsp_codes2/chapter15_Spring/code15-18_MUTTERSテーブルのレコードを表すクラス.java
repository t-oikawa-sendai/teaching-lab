// コード15-18 MUTTERSテーブルのレコードを表すクラス
package com.example.demo.model; 

import java.io.Serializable;
// lombok関連(lombok.*)のインポート文を省略しています

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mutter implements Serializable {
private int id; // ID
private String userName; // ユーザー名
private String text; // つぶやき内容

public Mutter(String userName, String text) {
this.userName = userName;
User.java
（com.example.demo.modelパッケージ）
Mutter.java
（com.example.demo.modelパッケージ）
解説①

480
第
V
部
this.text = text;
}
}
解説①
　フィールドの一部だけを受け取るコンストラクタは、Lombokで自動生成
できないため、これまで通り自分で定義しています。
15.6.3 サービスクラスの作成
　これまで処理を担当していたクラスを、Springのサービスクラスに変更し
ます。
　@Serviceが付いている以外に、次の変更があります。
・クラス名の末尾:「Logic」から「Service」に変更
・パッケージ:「com.example.demo.model」に配置
execute( )メソッドの内容は、これまでと基本的に同じです。
　次のコード15-19は、コード10-4のLoginLogicをサービスクラスに変更し
たものです。