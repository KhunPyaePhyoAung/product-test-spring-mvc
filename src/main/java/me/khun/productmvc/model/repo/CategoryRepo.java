package me.khun.productmvc.model.repo;

import java.util.List;

import me.khun.productmvc.model.entity.Category;

public interface CategoryRepo extends BaseRepo<Long, Category> {
	
	List<Category> findByKeyword(String keyword);
	
}
