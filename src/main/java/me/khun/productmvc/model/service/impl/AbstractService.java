package me.khun.productmvc.model.service.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import me.khun.productmvc.model.service.exception.DataIntegrityViolationExceptionExtractor;
import me.khun.productmvc.model.service.exception.DuplicateKeyExceptionExtractor;
import me.khun.productmvc.model.service.exception.ServiceException;

public abstract class AbstractService {
	protected ServiceException parseServiceException(String entityName, DataAccessException e) {
		var message = "";
		
		if (e instanceof DuplicateKeyException ex) {
			message = new DuplicateKeyExceptionExtractor(entityName, ex).getMessage();
		} else if (e instanceof DataIntegrityViolationException ex) {
			message = new DataIntegrityViolationExceptionExtractor(entityName, ex).getMessage();
		}
		
		return new ServiceException(message);
	}
}
