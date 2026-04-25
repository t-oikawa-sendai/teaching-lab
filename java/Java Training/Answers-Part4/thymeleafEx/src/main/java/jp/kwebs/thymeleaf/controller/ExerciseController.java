package jp.kwebs.thymeleaf.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.kwebs.thymeleaf.form.Bread;

@Controller
public class ExerciseController {
	
	@GetMapping("/ex371")
	public String exercise_371(Model model) {
		var items = List.of("apple", "banana", "cherry");
		model.addAttribute("items", items);
		model.addAttribute("stock", true);
		return "ex371";
	}
	
	@GetMapping("/ex372")
	public String exercise_372(Model model) {
		var bread = new Bread("フランスパン", 350);
		model.addAttribute("bread", bread);
		return "ex372";
	}
	
	@GetMapping("/ex373")
	public String exercise_373(Model model) {
		return "ex373";
	}
	
	@PostMapping("/ex373")
	public String exercise_373_result(Bread bread) {
		return "ex372"; // 前回作ったものをそのまま利用
	}
	
	@GetMapping("/ex374")
	public String exercise_374(Model model) {
		model.addAttribute("bread", new Bread("フランスパン", 350));
		return "ex374";
	}
	
	@PostMapping("/ex374")
	public String exercise_374_result(Bread bread) {
		return "ex372";// 前回作ったものをそのまま利用
	}

}
