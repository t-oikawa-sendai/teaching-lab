package jp.kwebs.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyappController {

	@GetMapping("/myapp")
	public String display() {
		return "comment-entry";
	}
	
	@PostMapping("/myapp")
	public String commentData(@RequestParam String comment, Model model) {
		model.addAttribute("comment", comment);
		return  "comment-result";
	}
}
