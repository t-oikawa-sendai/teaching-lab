// コード15-1 基本的なコントローラの例
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
@GetMapping("/Hello")
public String handle() {
// リクエストによって実行する処理を記述する
}
}
解説① コントローラクラスの名前
Springでは、コントローラクラスの名前の末尾に「Controller」を付ける
のが一般的です。そのため、本章でもこの慣習に従っています。
解説② リクエストマッピングアノテーション
　サーブレットの場合、どのリクエストでメソッドが実行されるかは、
@WebServletアノテーションとメソッド名（doGet / doPost）で指定してい
ました。
　一方、Springでは、リクエストマッピングアノテーションを使って、どの
リクエストに対応するかを指定します。
リクエストマッピングアノテーション
@GetMapping("/URLパターン") ※1
@PostMapping("/URLパターン") ※2
※1 org.springframework.web.bind.annotation.GetMappingをインポートする必要がある
※2 org.springframework.web.bind.annotation.PostMappingをインポートする必要がある
解説①
解説②
ハンドラメソッド