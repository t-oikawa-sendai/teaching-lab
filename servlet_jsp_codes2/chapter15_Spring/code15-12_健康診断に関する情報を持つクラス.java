// コード15-12 健康診断に関する情報を持つクラス
package com.example.demo.model; 

import java.io.Serializable;
import lombok.Data;

@Data
public class Health implements Serializable {
private double height, weight, bmi;
private String bodyType;
}
解説①
　@Dataアノテーションを付けることで、getter/setterをLombokが自動生
成します（@Getterと@Setterをそれぞれ付けてもかまいません）。
15.5.3 サービスクラスの作成
　コード7-5のHealthCheckLogicを、Springのサービスクラスとして作成し
ます。
Springの慣習に従い、クラス名を「HealthCheckLogic」から「HealthCheck
Service」に変更しています（15.3.3項）。
　このクラスは、@Serviceアノテーションを付けている点を除けば、基本的
な処理内容はこれまでと同じです。execute( )メソッドの処理内容は、コー
ド7-5から変わっていません。
Health.java
（com.example.demo.modelパッケージ）
解説①