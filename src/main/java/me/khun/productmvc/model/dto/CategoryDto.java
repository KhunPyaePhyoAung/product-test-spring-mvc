package me.khun.productmvc.model.dto;

import java.util.List;

import me.khun.productmvc.model.entity.Category;

public class CategoryDto {

	private Long id;
	private String name;
	private String description;

	public static CategoryDto of(Category category) {
		if (category == null) {
			return null;
		}
		
		var dto = new CategoryDto();
		dto.setId(category.getId());
		dto.setName(category.getName());
		dto.setDescription(category.getDescription());
		return dto;
	}
	
	public static List<CategoryDto> listOf(List<Category> categoryList) {
		return categoryList.stream().map(c -> CategoryDto.of(c)).toList();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
