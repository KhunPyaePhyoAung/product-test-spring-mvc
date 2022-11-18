package me.khun.productmvc.model.service;

import java.util.List;

import me.khun.productmvc.model.entity.Product;

public interface ProductService {
	
	Product save(Product product);
	
	Product deleteById(long id);
	
	boolean delete(Product product);
	
	Product findById(long id);
	
	List<Product> findByKeyword(String keyword);
	
	List<Product> findAll();
	
	long getCount();
}
