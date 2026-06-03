// コード15-6 Lombokを使用したクラスの例
package com.example.demo.model; 

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
private String name;
private int price;
}
　@Getter、@Setterといったアノテーションが記述されていることに注目
してください。これらを付けることで、getterやsetterなどの定型的なメソッ
ドを自動的に生成できます。
 表15-3 Lombokが提供する代表的なアノテーション
アノテーション 自動生成されるメソッド・コンストラクタ
@Getter すべてのフィールドの getter
@Setter すべてのフィールドの setter
@Data すべてのフィールドの getter と setter、および toString、
equals、hashCode
@NoArgsConstructor 引数を持たないコンストラクタ
@AllArgsConstructor すべてのフィールドを引数にもつコンストラクタ
@RequiredArgsConstructor fi nal が付いているフィールドを引数にもつコンストラクタ
GetterとSetterの両方を自動生成する場合は、@Dataを使用してもかまい
Product.java
（com.example.demo.modelパッケージ）