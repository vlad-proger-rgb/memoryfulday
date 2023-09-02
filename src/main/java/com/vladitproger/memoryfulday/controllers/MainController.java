package com.vladitproger.memoryfulday.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Year;

@Controller
public class MainController {

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("title", "Main Page");
		model.addAttribute("year", Year.now().getValue());
		return "index";
	}

	@GetMapping("/gallery")
	public String gallery(Model model) {
		model.addAttribute("asdasd", "asd");
		return "gallery";
	}

	@GetMapping("/search")
	public String search(Model model) {
		model.addAttribute("asd", "asd");
		return "search";
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("asd", "asd");
		return "about";
	}



}
