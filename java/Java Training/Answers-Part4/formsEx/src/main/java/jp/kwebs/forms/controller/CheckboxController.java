package jp.kwebs.forms.controller;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckboxController {

	@GetMapping("/fruit")
	public String display() {
		return "checkbox-enter";
	}
	
	@PostMapping("/fruit")
	public String result(@RequestParam List<String> fruit, Model model) {
		model.addAttribute("fruit", fruit);
		return "checkbox-result";
	}
}
