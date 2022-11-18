package me.khun.productmvc.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import me.khun.productmvc.model.entity.Product;
import me.khun.productmvc.model.repo.ProductRepo;
import me.khun.productmvc.model.service.ProductService;
import me.khun.productmvc.model.service.validation.EntityValidator;

@Service
public class MySqlJdbcProductServiceImpl extends AbstractService implements ProductService {
	
	@Autowired
	private ProductRepo repo;

	@Autowired
	private EntityValidator<Product> validator;
	
	private static final String ENTITY_NAME = "product";
	
	@Override
	public Product save(Product product) {
		validator.validate(product);
		try {
			if (product.getId() == null) {
				return repo.create(product);
			}
			return repo.update(product) ? product : null;
		} catch (DataAccessException e) {
			throw parseServiceException(ENTITY_NAME, e);
		}
	}

	@Override
	public Product deleteById(long id) {
		try {
			return repo.deleteById(id);
		} catch (DataAccessException e) {
			throw parseServiceException(ENTITY_NAME, e);
		}
	}

	@Override
	public boolean delete(Product product) {
		return deleteById(product.getId()) != null;
	}

	@Override
	public Product findById(long id) {
		try {
			return repo.findById(id);
		} catch (DataAccessException e) {
			throw parseServiceException(ENTITY_NAME, e);
		}
	}

	@Override
	public List<Product> findByKeyword(String keyword) {
		keyword = keyword == null ? "" : keyword.trim();
		
		try {
			return repo.findByKeyword(keyword);
		} catch (DataAccessException e) {
			throw parseServiceException(ENTITY_NAME, e);
		}
	}

	@Override
	public List<Product> findAll() {
		try {
			return repo.findAll();
		} catch (DataAccessException e) {
			throw parseServiceException(ENTITY_NAME, e);
		}
	}

	@Override
	public long getCount() {
		try {
			return repo.getCount();
		} catch (DataAccessException e) {
			throw parseServiceException(ENTITY_NAME, e);
		}
	}

}
