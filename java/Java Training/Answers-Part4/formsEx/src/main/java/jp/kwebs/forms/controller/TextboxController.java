package jp.kwebs.forms.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TextboxController {
	
	@GetMapping("/address")
	public String display() {
		return "textbox-enter";
	}
	
	@PostMapping("/address")
	public String result(@RequestParam String address, Model model) {
		model.addAttribute("address", address);
		return "textbox-result";
	}
}


