package me.khun.productmvc.model.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import me.khun.productmvc.model.entity.Category;
import me.khun.productmvc.model.service.exception.ServiceException;

@Component
public class CategoryValidator implements EntityValidator<Category> {

	@Override
	public void validate(Category category) {
		if (!StringUtils.hasLength(category.getName()) || category.getName().isBlank()) {
			throw new ServiceException("Category name cannot be empty.");
		}
		
		if (category.getId() != null && category.getId() < 0) {
			category.setId(null);
		}
		
		category.setName(category.getName().trim());
		
		if (StringUtils.hasLength(category.getDescription())) {
			category.setDescription(category.getDescription().trim());
		}
	}

}
