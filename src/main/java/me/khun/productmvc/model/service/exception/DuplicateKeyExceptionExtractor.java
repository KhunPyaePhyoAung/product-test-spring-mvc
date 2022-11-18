package me.khun.productmvc.model.service.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateKeyExceptionExtractor {

	private DuplicateKeyException e;
	private String entityName;
	
	public DuplicateKeyExceptionExtractor(String entityName, DuplicateKeyException e) {
		this.e = e;
		this.entityName = entityName;
	}
	
	public String getEntry() {
		var message = e.getMessage();
		var start = message.indexOf("'") + 1;
		var end = message.indexOf("'", start);
		return message.substring(start, end);
	}
	
	public String getKey() {
		var message = e.getMessage();
		var end = message.lastIndexOf("'");
		var start = message.lastIndexOf("'", end - 1) + 1;
		return message.substring(start, end);
	}
	
	public String getMessage() {
		return String.format("The %s with this %s already exists.", entityName.toLowerCase(), getKey().toLowerCase());
	}
	
}
