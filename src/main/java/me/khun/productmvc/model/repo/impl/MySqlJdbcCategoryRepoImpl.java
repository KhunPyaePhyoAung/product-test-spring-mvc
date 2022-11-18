package me.khun.productmvc.model.repo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import me.khun.productmvc.model.entity.Category;
import me.khun.productmvc.model.repo.CategoryRepo;

@Repository
public class MySqlJdbcCategoryRepoImpl implements CategoryRepo {
	
	@Autowired
	private SimpleJdbcInsert categoryInsert;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	private RowMapper<Category> categoryRowMapper;
	
	@Value("${db.category.selectById}")
	private String selectCategoryByIdSql;
	
	@Value("${db.category.selectAll}")
	private String selectAllCategoriesSql;
	
	@Value("${db.category.selectByKeyword}")
	private String selectCategoryByKeyword;
	
	@Value("${db.category.update}")
	private String updateCategorySql;
	
	@Value("${db.category.deleteById}")
	private String deleteCategoryByIdSql;

	@Value("${db.category.count}")
	private String countCategorySql;

	@Override
	public Category create(Category entity) {
		var insertParams = new MapSqlParameterSource();
		insertParams.addValue("name", entity.getName());
		insertParams.addValue("description", entity.getDescription());
		var id = categoryInsert.executeAndReturnKey(insertParams).longValue();
		
		return findById(id);
	}

	@Override
	public boolean update(Category entity) {
		var updateParams = new MapSqlParameterSource();
		updateParams.addValue("id", entity.getId());
		updateParams.addValue("name", entity.getName());
		updateParams.addValue("description", entity.getDescription());
		
		var count = jdbc.update(updateCategorySql, updateParams);
		
		return count > 0;
	}

	@Override
	public Category deleteById(Long id) {
		var category = findById(id);
		
		if (category == null) {
			return null;
		}
		
		var deleteParams = new MapSqlParameterSource();
		deleteParams.addValue("id", id);
		
		var count = jdbc.update(deleteCategoryByIdSql, deleteParams);
		
		return count > 0 ? category : null;
	}

	@Override
	public Category findById(Long id) {
		var params = new MapSqlParameterSource();
		params.addValue("id", id);
		return jdbc.query(selectCategoryByIdSql, params, rs -> {
					if (rs.next()) {
						return categoryRowMapper.mapRow(rs, 1);
					}
					return null;
				});
	}

	@Override
	public List<Category> findAll() {
		return jdbc.query(selectAllCategoriesSql, categoryRowMapper);
	}

	@Override
	public long getCount() {
		return jdbc.queryForObject(countCategorySql, new MapSqlParameterSource(), Long.class);
	}

	@Override
	public List<Category> findByKeyword(String keyword) {
		var params = new MapSqlParameterSource();
		params.addValue("keyword", String.format("%%%s%%", keyword));
		return jdbc.query(selectCategoryByKeyword, params, categoryRowMapper);
	}

}
