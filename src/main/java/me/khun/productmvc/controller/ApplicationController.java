package me.khun.productmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import me.khun.productmvc.model.service.CategoryService;
import me.khun.productmvc.model.service.ProductService;

@Controller
@RequestMapping
public class ApplicationController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;

	@GetMapping(value = {
			"/", "home"
	})
	public String home(Model model) {
		var categoryCount = categoryService.getCount();
		var productCount = productService.getCount();
		model.addAttribute("categoryCount", categoryCount);
		model.addAttribute("productCount", productCount);
		return "index";
	}
}
