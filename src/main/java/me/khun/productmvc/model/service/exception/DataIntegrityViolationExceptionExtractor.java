package me.khun.productmvc.model.service.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DataIntegrityViolationExceptionExtractor {

	private String entityName;
	private DataIntegrityViolationException e;
	
	public DataIntegrityViolationExceptionExtractor(String entityName, DataIntegrityViolationException e) {
		this.entityName = entityName;
		this.e = e;
	}
	
	public String getMessage() {
		var message = e.getMessage();
		if (message.contains("Cannot add or update")) {
			return String.format("Could not save this %s.", entityName);
		} else if (message.contains("Cannot delete or update")) {
			return String.format("Could not delete this %s.", entityName);
		}
		return null;
	}
}
