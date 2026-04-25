package jp.kwebs.pcshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import jp.kwebs.pcshop.form.PcForm;

@Controller
@RequestMapping("/pc")
public class PcController {

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("pcForm", new PcForm());
		return "pc-create";
	}
	
	/*
	@PostMapping("/create")
	public String result(PcForm pc) {
		return "pc-result";
	}
	 */
	
	@PostMapping("/create")
	public String result(@Valid PcForm pc, BindingResult result) {
		if(result.hasErrors()) {
			return "pc-create";
		}
		return "pc-result";
	}
}
