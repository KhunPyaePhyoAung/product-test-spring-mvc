package me.khun.productmvc.model.repo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import me.khun.productmvc.model.entity.Product;
import me.khun.productmvc.model.repo.ProductRepo;

@Repository
public class MySqlJdbcProductRepoImpl implements ProductRepo {
	
	@Autowired
	private SimpleJdbcInsert productInsert;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private RowMapper<Product> productRowMapper;

	@Value("${db.product.selectById}")
	private String selectProductByIdSql;

	@Value("${db.product.selectAll}")
	private String selectAllProductsSql;

	@Value("${db.product.selectByKeyword}")
	private String selectProductByKeywordSql;
	
	@Value("${db.product.update}")
	private String updateProductSql;

	@Value("${db.product.deleteById}")
	private String deleteProductByIdSql;
	
	@Value("${db.product.count}")
	private String countProductSql;


	@Override
	public Product create(Product entity) {
		var params = new MapSqlParameterSource();
		params.addValue("name", entity.getName());
		params.addValue("category_id", entity.getCategory().getId());
		params.addValue("unit_price", entity.getUnitPrice());
		params.addValue("description", entity.getDescription());
		params.addValue("id", entity.getId());
		
		var id = productInsert.executeAndReturnKey(params).longValue();
		var product = findById(id);
		
		return product;
	}

	@Override
	public boolean update(Product entity) {
		var params = new MapSqlParameterSource();
		params.addValue("name", entity.getName());
		params.addValue("category_id", entity.getCategory().getId());
		params.addValue("unit_price", entity.getUnitPrice());
		params.addValue("description", entity.getDescription());
		params.addValue("id", entity.getId());
		
		var count = jdbc.update(updateProductSql, params);
		
		return count > 0;
	}

	@Override
	public Product deleteById(Long id) {
		var params = new MapSqlParameterSource();
		params.addValue("id", id);
		
		var product = findById(id);
		
		if (product == null) {
			return null;
		}
		
		var count = jdbc.update(deleteProductByIdSql, params);
		
		return count > 0 ? product : null;
	}

	@Override
	public Product findById(Long id) {
		var params = new MapSqlParameterSource();
		params.addValue("id", id);
		
		return jdbc.query(selectProductByIdSql, params, rs -> {
					if (rs.next()) {
						return productRowMapper.mapRow(rs, 1);
					}
					return null;
				});
	}

	@Override
	public List<Product> findAll() {
		return jdbc.query(selectAllProductsSql, productRowMapper);
	}

	@Override
	public long getCount() {
		return jdbc.queryForObject(countProductSql, new MapSqlParameterSource(), Long.class);
	}

	@Override
	public List<Product> findByKeyword(String keyword) {
		var params = new MapSqlParameterSource();
		params.addValue("keyword", String.format("%%%s%%", keyword));
		return jdbc.query(selectProductByKeywordSql, params, productRowMapper);
	}

}
