package jp.kwebs.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jp.kwebs.bookstore.form.BookForm;

@Controller
@RequestMapping("/book")
public class BookstoreController {

	@GetMapping("/create")
	public String bookForm(Model model) {
		model.addAttribute("bookForm", new BookForm());
		return "book-create";
	}
	
	@PostMapping("/create")
	public String display(@Valid BookForm bookForm, BindingResult result) {
		if(result.hasErrors()) {
			return "book-create";
		}
		return "book-result";
	}
	
}
