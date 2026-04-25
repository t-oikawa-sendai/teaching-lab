package jp.kwebs.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyappController {

	@GetMapping("/myapp")
	public String display() {
		return "comment";
	}
}
