package me.khun.productmvc.model.service.exception;

import me.khun.productmvc.ApplicationException;

public class ServiceException extends ApplicationException {

	private static final long serialVersionUID = 1L;
	
	public ServiceException() {}
	
	public ServiceException(String message) {
		super(message);
	}

}
