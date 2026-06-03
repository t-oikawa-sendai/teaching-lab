// コード15-17 ユーザーに関する情報を持つクラス
package com.example.demo.model; 

import java.io.Serializable;
// lombok関連(lombok.*)のインポート文を省略しています

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
private String name; // ユーザー名
private String pass; // パスワード
}