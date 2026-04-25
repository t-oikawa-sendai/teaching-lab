package jp.kwebs.thymeleaf.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kwebs.thymeleaf.form.Bread;

@Controller
public class ExerciseController {
	
	@GetMapping("/ex261")
	public String exercise_261(Model model) {
		var items = List.of("apple", "banana", "cherry");
		model.addAttribute("items", items);
		model.addAttribute("stock", true);
		return "ex261";
	}
	
	@GetMapping("/ex262")
	public String exercise_262(Model model) {
		var bread = new Bread("フランスパン", 350);
		model.addAttribute("bread", bread);
		return "ex262";
	}
	/* Exercise 26-2 の解答
	@GetMapping("/ex263")
	public String exercise_263() {
		return "ex263";
	}
	*/
	
	@GetMapping("/ex263")
	public String exercise_263(Model model) {
		model.addAttribute("bread", new Bread("フランスパン", 350));
		return "ex263";
	}
	
	
	@PostMapping("/ex263")
	public String exercise_263_result(Bread bread) {
		return "ex262";
	}

}
