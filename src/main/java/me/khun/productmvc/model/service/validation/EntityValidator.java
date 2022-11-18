package me.khun.productmvc.model.service.validation;

public interface EntityValidator<T> {
	void validate(T entity);
}
