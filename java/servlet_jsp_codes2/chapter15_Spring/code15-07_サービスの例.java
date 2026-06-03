// コード15-7 サービスの例
package com.example.demo.model; 

import org.springframework.stereotype.Service;

@Service
public class AddService {
public int execute(int num1, int num2) {
return num1 + num2;
}
}
解説① サービスクラスの名前
Springでは、サービスクラスの名前の末尾に「Service」を付けるのが一
般的です。そのため、本章でもこの慣習に従っています。
15.3.4 コントローラからサービスを利用する
　コントローラがサービスに処理を依頼するには、サービスが持つメソッド
を呼び出します。通常、メソッドを呼び出すには、その前にnewキーワード
でインスタンスを生成しますが、Spring Frameworkを使用した開発では、そ
れは一般的ではありません。
AddService.java
（com.example.demo.modelパッケージ）
解説①