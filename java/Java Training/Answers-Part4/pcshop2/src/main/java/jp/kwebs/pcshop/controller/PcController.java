package jp.kwebs.pcshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import jp.kwebs.pcshop.form.PcForm;
import jp.kwebs.pcshop.service.PcService;

@Controller
@RequestMapping("/pc")
public class PcController {

	private final PcService ps;

	public PcController(PcService ps) {
		this.ps = ps;
	}
	
	@GetMapping("/list")
	public String listing(Model model) {

		var pcs = ps.readAllPcs();
		model.addAttribute("pcs", pcs);
		return "pc-list";
	}
	
	@GetMapping("/create")
	public String pcForm(Model model) {
		model.addAttribute("pcForm", new PcForm());
		return "pc-create";
	}
	
	@PostMapping("/create")
	public String create(@Valid PcForm pcForm, BindingResult result) {
		if (result.hasErrors()) {
			return "pc-create";
		}
		ps.createPc(pcForm);
		return "redirect:/pc/list";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable Long id, Model model) {
		var pc = ps.readPcById(id);
		var pcForm = ps.toForm(pc);
		model.addAttribute("pcForm", pcForm);
		return "pc-edit";
	}
	
	@PostMapping("/edit")
	public String update(@Valid PcForm pcForm, BindingResult result) {
		if (result.hasErrors()) {
			return "pc-edit";
		}
		ps.updatePc(pcForm);
		return "redirect:/pc/list";
	}
	
	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		ps.deletePc(id);
		return "redirect:/pc/list";
	}
}




















