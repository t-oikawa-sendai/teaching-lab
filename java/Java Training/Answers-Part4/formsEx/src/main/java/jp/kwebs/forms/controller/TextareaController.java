package jp.kwebs.forms.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TextareaController {
	
	@GetMapping("/notice")
	public String display() {
		return "textarea-enter";
	}
	
	@PostMapping("/notice")
	public String textareaResult(@RequestParam String notice, Model model) {
		model.addAttribute("notice", notice);
		return "textarea-result";
	}
}





















