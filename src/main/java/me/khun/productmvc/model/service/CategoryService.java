package me.khun.productmvc.model.service;

import java.util.List;

import me.khun.productmvc.model.entity.Category;

public interface CategoryService {
	
	Category save(Category category);
	
	Category deleteById(long id);
	
	boolean delete(Category category);
	
	Category findById(long id);
	
	List<Category> findByKeyword(String keyword);
	
	List<Category> findAll();
	
	long getCount();
}
