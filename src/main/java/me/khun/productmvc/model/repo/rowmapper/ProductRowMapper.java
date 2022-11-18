package me.khun.productmvc.model.repo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import me.khun.productmvc.model.entity.Category;
import me.khun.productmvc.model.entity.Product;

@Component
public class ProductRowMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		var category = new Category();
		category.setId(rs.getLong("category_id"));
		category.setName(rs.getString("category_name"));
		category.setDescription(rs.getString("category_description"));
		
		var product = new Product();
		product.setId(rs.getLong("id"));
		product.setName(rs.getString("name"));
		product.setCategory(category);
		product.setUnitPrice(rs.getInt("unit_price"));
		product.setDescription(rs.getString("description"));
		
		return product;
	}

}
