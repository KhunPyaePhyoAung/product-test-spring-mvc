package me.khun.productmvc.model.dto;

import java.util.List;

import me.khun.productmvc.model.entity.Product;

public class ProductDto {

	private Long id;
	private String name;
	private Integer unitPrice;
	private String description;
	private Long categoryId;
	private String categoryName;
	private String categoryDescription;
	
	public static ProductDto of(Product product) {
		if (product == null) {
			return null;
		}
		
		var dto = new ProductDto();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setUnitPrice(product.getUnitPrice());
		dto.setDescription(product.getDescription());
		dto.setCategoryId(product.getCategory().getId());
		dto.setCategoryName(product.getCategory().getName());
		dto.setCategoryDescription(product.getCategory().getDescription());
		return dto;
	}
	
	public static List<ProductDto> listOf(List<Product> productList) {
		return productList.stream().map(p -> ProductDto.of(p)).toList();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

}
