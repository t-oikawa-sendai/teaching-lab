package jp.kwebs.forms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormsController {

	@GetMapping("/forms")
	public String display() {
		return "forms";
	}
}
