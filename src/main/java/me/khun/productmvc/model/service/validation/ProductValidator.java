package me.khun.productmvc.model.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import me.khun.productmvc.model.entity.Product;
import me.khun.productmvc.model.service.exception.ServiceException;

@Component
public class ProductValidator implements EntityValidator<Product> {

	@Override
	public void validate(Product product) {
		if (!StringUtils.hasLength(product.getName()) || product.getName().isBlank()) {
			throw new ServiceException("Product name cannot be empty.");
		}
		
		if (product.getCategory() == null) {
			throw new ServiceException("Please select a category.");
		}
		
		if (product.getUnitPrice() == null || product.getUnitPrice() < 0) {
			throw new ServiceException("Please enter a valid unit price.");
		}
		
		if (product.getId() != null && product.getId() < 1) {
			product.setId(null);
		}
		
		product.setName(product.getName().trim());
		
		if (StringUtils.hasLength(product.getDescription())) {
			product.setDescription(product.getDescription().trim());
		}
	}

}
