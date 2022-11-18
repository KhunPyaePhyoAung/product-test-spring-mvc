package me.khun.productmvc.model.repo;

import java.util.List;

import me.khun.productmvc.model.entity.Product;

public interface ProductRepo extends BaseRepo<Long, Product> {

	List<Product> findByKeyword(String keyword);
}
