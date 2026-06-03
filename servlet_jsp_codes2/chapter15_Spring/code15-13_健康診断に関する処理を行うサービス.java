// コード15-13 健康診断に関する処理を行うサービス
package com.example.demo.model; 

import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {
public void execute(Health health) {
double weight = health.getWeight();
double height = health.getHeight();
double bmi = weight / ((height / 100) * (height / 100));
health.setBmi(bmi);

String bodyType;
if (bmi < 18.5) {
bodyType = "痩せ型";
} else if (bmi < 25) {
bodyType = "普通体型";
} else {
bodyType = "肥満体型";
}
health.setBodyType(bodyType);
}
}
HealthCheckService.java
（com.example.demo.modelパッケージ）

472
第
V
部
15.5.4 コントローラクラスの作成
　コード7-6のHealthCheckを、Springのコントローラとして作成します。
Springの慣習に従い、クラス名を「HealthCheck」から「HealthCheck
Controller」に変更しています（15.2.2項）。
　このクラスは、@Controllerが付いている以外に、次の点がサーブレット
から変わっています。
・ doGet/doPostメソッド→ ハンドラメソッド（@GetMapping/@PostMapping）
・リクエストパラメータの取得 → ハンドラメソッドの引数
・ HttpServletRequestの取得 → ハンドラメソッドの引数
・サービスの取得 → 依存性注入を使用
・フォワード先の指定 → 戻り値で指定