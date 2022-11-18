package me.khun.productmvc.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import me.khun.productmvc.model.entity.Category;
import me.khun.productmvc.model.repo.CategoryRepo;
import me.khun.productmvc.model.service.CategoryService;
import me.khun.productmvc.model.service.validation.EntityValidator;

@Service
public class MySqlJdbcCategoryServiceImpl extends AbstractService implements CategoryService {

	@Autowired
	private CategoryRepo repo;
	
	@Autowired
	private EntityValidator<Category> validator;
	
	private static final String ENTITY_NAME = "category";
	
	@Override
	public Category save(Category category) {
		validator.validate(category);
		
		try {
			if (category.getId() == null) {
				return repo.create(category);
			}
			
			return repo.update(category) ? category : null;
		} catch (DataAccessException e) {
			throw parseServiceException(ENTITY_NAME, e);
		}
	}

	@Override
	public Category deleteById(long id) {
		try {
			return repo.deleteById(id);
		} catch (DataAccessException e) {
			throw parseServiceException(ENTITY_NAME, e);
		}
	}

	@Override
	public boolean delete(Category category) {
		return deleteById(category.getId()) != null;
	}

	@Override
	public Category findById(long id) {
		try {
			return repo.findById(id);
		} catch (DataAccessException e) {
			throw parseServiceException(ENTITY_NAME, e);
		}
	}

	@Override
	public List<Category> findByKeyword(String keyword) {
		keyword = keyword == null ? "" : keyword.trim();
		try {
			return repo.findByKeyword(keyword);
		} catch (DataAccessException e) {
			throw parseServiceException(ENTITY_NAME, e);
		}
	}

	@Override
	public List<Category> findAll() {
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
