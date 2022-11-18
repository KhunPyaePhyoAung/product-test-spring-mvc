package me.khun.productmvc.model.repo;

import java.util.List;

public interface BaseRepo<ID, T> {
	
	T create(T entity);
	
	boolean update(T entity);
	
	T deleteById(ID id);
	
	T findById(ID id);

	List<T> findAll();
	
	long getCount();
}
