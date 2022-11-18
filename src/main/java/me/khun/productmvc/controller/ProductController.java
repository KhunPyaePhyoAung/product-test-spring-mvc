package me.khun.productmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import me.khun.productmvc.model.dto.CategoryDto;
import me.khun.productmvc.model.dto.ProductDto;
import me.khun.productmvc.model.entity.Product;
import me.khun.productmvc.model.service.CategoryService;
import me.khun.productmvc.model.service.ProductService;
import me.khun.productmvc.model.service.exception.ServiceException;
import me.khun.productmvc.ui.Alert;
import me.khun.productmvc.ui.Alert.Status;

@Controller
@RequestMapping
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("products")
	public String products(
			@RequestParam(required = false)
			String keyword,
			Model model) {
		var products = productService.findByKeyword(keyword);
		model.addAttribute("products", ProductDto.listOf(products));
		return "products";
	}
	
	@GetMapping("product/edit")
	public String editProduct(
			@RequestParam(required = false)
			Long id,
			Model model) {
		var categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		if (id != null) {
			var product = productService.findById(id);
			model.addAttribute("product", ProductDto.of(product));
		}
		return "edit-product";
	}
	
	@GetMapping(value = "product/detail", params = "id")
	public String productDetail(
			@RequestParam
			Long id,
			Model model
			) {
		var product = productService.findById(id);
		model.addAttribute("product", ProductDto.of(product));
		return "product-detail";
	}
	
	@PostMapping("product/save")
	public String saveProduct(
			@RequestParam(required = false)
			Long id,
			@RequestParam(required = false)
			String name,
			@RequestParam(required = false)
			Integer unitPrice,
			@RequestParam(name = "category", required = false)
			Long categoryId,
			@RequestParam(required = false)
			String description,
			Model model,
			RedirectAttributes redirect) {
		
		var category = categoryService.findById(categoryId);
		
		var product = new Product();
		product.setId(id);
		product.setName(name);
		product.setUnitPrice(unitPrice);
		product.setCategory(category);
		product.setDescription(description);
		
		try {
			var saved = productService.save(product);
			
			if (saved.getId() != id) {
				var alertMessage = "Successfully created a new product : %s.".formatted(saved.getName());
				var alert = new Alert(alertMessage, Status.SUCCESS);
				redirect.addFlashAttribute("alert", alert);
			}
			
			return "redirect:/products";
		} catch (ServiceException e) {
			model.addAttribute("categories", CategoryDto.listOf(categoryService.findAll()));
			model.addAttribute("product", ProductDto.of(product));
			model.addAttribute("exception", e);
			return "edit-product";
		}
		
	}
	
	@GetMapping("product/delete")
	public String deleteProduct(
			@RequestParam
			Long id,
			Model model,
			RedirectAttributes redirect
			) {
		
		try {
			var deletedProduct = productService.deleteById(id);
			var deleted = deletedProduct != null;
			var alertMessage = deleted
								? "Successfully deleted the product : %s.".formatted(deletedProduct.getName())
								: "Could not delete the product : %s.".formatted(deletedProduct.getName());
			var alert = new Alert(alertMessage, deleted ? Status.SUCCESS : Status.ERROR);
			redirect.addFlashAttribute("alert", alert);
		} catch (ServiceException e) {
			var alert = new Alert(e.getMessage(), Status.ERROR);
			redirect.addFlashAttribute("exception", alert);
		}
		
		return "redirect:/products";
	}
	
}
