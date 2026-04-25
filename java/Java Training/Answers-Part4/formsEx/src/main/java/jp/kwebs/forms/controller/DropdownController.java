package jp.kwebs.forms.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DropdownController {

	@GetMapping("/transport")
	public String display() {
		return "dropdown-enter";
	}
	
	@PostMapping("/transport")
	public String result(@RequestParam String transport, Model model) {
		model.addAttribute("transport", transport);
		return "dropdown-result";
	}
	
}
