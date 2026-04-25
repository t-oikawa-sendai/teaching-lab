package jp.kwebs.thymeleaf.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kwebs.thymeleaf.form.User;

@Controller
public class ThymeleafController {

	@GetMapping("/text")
	public String example_text(Model model) {
		model.addAttribute("message", "テキストの表示");
		return "example_text";
	}
	
	@GetMapping("/if")
	public String example_if(Model model) {
		model.addAttribute("stock", true);
		return "example_if";
	}
	
	@GetMapping("/each")
	public String example_each(Model model) {
		var items = List.of("apple", "banana", "cherry");
		model.addAttribute("items", items);
		return "example_each";
	}
	
	@GetMapping("/obj")
	public String example_obj(Model model) {
		var user = new User(100, "田中宏");
		model.addAttribute("user", user);
		return "example_obj";
	}
	@GetMapping("/enter")
	public String example_enter() {
		return "example_enter";
	}
	/* バインドの例ではこちらを使う
	@GetMapping("/enter")
	public String example_enter(Model model) {
		var user = new User(100, "田中宏");
		model.addAttribute("user", user);
		return "example_enter";
	}
	*/

		
	@PostMapping("/enter")
	public String example_result(User user){
		return "example_obj";
	}

}












