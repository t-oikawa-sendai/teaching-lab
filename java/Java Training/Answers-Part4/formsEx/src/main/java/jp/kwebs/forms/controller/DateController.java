package jp.kwebs.forms.controller;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DateController {

	@GetMapping("/holiday")
	public String display() {
		return "date-enter";
	}
	
	@PostMapping("/holiday")
	public String result(@RequestParam LocalDate holiday, Model model) {
		model.addAttribute("holiday", holiday);
		return "date-result";
	}
}
