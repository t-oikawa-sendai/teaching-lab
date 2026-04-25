package jp.kwebs.forms.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PictureController {

	@GetMapping("/picture")
	public String display() {
		return "picture-enter";
	}
	
	@PostMapping("/picture")
	public String result(@RequestParam String picture, Model model) {
		model.addAttribute("picture", picture);
		return "picture-result";
	}
}
