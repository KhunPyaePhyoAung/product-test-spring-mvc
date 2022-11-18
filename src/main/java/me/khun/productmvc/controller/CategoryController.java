package me.khun.productmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import me.khun.productmvc.model.dto.CategoryDto;
import me.khun.productmvc.model.entity.Category;
import me.khun.productmvc.model.service.CategoryService;
import me.khun.productmvc.model.service.exception.ServiceException;
import me.khun.productmvc.ui.Alert;
import me.khun.productmvc.ui.Alert.Status;

@Controller
@RequestMapping
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("categories")
	public String categories(
			@RequestParam(required = false)
			String keyword,
			Model model) {
		var categories = categoryService.findByKeyword(keyword);
		model.addAttribute("categories", CategoryDto.listOf(categories));
		return "categories";
	}
	
	@GetMapping("category/edit")
	public String editCategory(
			@RequestParam(required = false)
			Long id,
			Model model) {
		if (id != null && model.getAttribute("category") == null) {
			var category = categoryService.findById(id);
			model.addAttribute("category", CategoryDto.of(category));
		}
		return "edit-category";
	}
	
	@GetMapping(value = "category/detail", params = "id")
	public String categoryDetail(
			@RequestParam
			Long id,
			Model model
			) {
		var category = categoryService.findById(id);
		model.addAttribute("category", CategoryDto.of(category));
		return "category-detail";
	}
	
	@PostMapping("category/save")
	public String saveCategory(
			@ModelAttribute
			Category category,
			Model model,
			RedirectAttributes redirect) {
		
		try {
			var saved = categoryService.save(category);
			
			if (saved.getId() != category.getId()) {
				var alertMessage = "Successfully created a new category : %s.".formatted(saved.getName());
				var alert = new Alert(alertMessage, Status.SUCCESS);
				redirect.addFlashAttribute("alert", alert);
			}
			
			return "redirect:/categories";
		} catch (ServiceException e) {
			redirect.addFlashAttribute("category", CategoryDto.of(category));
			redirect.addFlashAttribute("exception", e);
			return "redirect:/category/edit%s".formatted(category.getId() == null ? "" : "?id=" + category.getId());
		}
	}
	
	@GetMapping(value = "category/delete", params = "id")
	public String deleteCategory(
			@RequestParam
			Long id,
			Model model,
			RedirectAttributes redirect
			) {
		
		try {
			var deletedCategory = categoryService.deleteById(id);
			var deleted = deletedCategory != null;
			var alertMessage = deleted
								? "Successfully deleted the category : %s.".formatted(deletedCategory.getName())
								: "Could not delete the category : %s.".formatted(deletedCategory.getName());
			var alert = new Alert(alertMessage, deleted ? Status.SUCCESS : Status.ERROR);
			
			redirect.addFlashAttribute("alert", alert);
		} catch (ServiceException e) {
			var alert = new Alert(e.getMessage(), Status.ERROR);
			redirect.addFlashAttribute("alert", alert);
		}
		
		return "redirect:/categories";
	}
	
}
