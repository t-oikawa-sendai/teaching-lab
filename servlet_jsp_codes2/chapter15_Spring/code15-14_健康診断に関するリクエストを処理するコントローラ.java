// コード15-14 健康診断に関するリクエストを処理するコントローラ
package com.example.demo.controller; 

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.model.Health;
import com.example.demo.model.HealthCheckService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HealthCheckController {
HealthCheckController.java
（com.example.demo.controllerパッケージ）