package me.khun.productmvc.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Configuration
@ComponentScan("me.khun.productmvc.model")
@PropertySource({
	"classpath:/database.properties",
	"classpath:/sql.properties"
})
public class RootConfig {

	@Bean
	public DataSource dataSource(
			@Value("${db.url}")
			String url,
			@Value("${db.username}")
			String username,
			@Value("${db.password}")
			String password) {
		var dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	
	@Bean
	public NamedParameterJdbcTemplate jdbc(DataSource dataSource) {
		var jdbc = new NamedParameterJdbcTemplate(dataSource);
		return jdbc;
	}
	
	@Bean
	public SimpleJdbcInsert categoryInsert(DataSource dataSource) {
		var jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.setTableName("category");
		jdbcInsert.setGeneratedKeyName("id");
		return jdbcInsert;
	}
	
	@Bean
	public SimpleJdbcInsert productInsert(DataSource dataSource) {
		var jdbcInsert = new SimpleJdbcInsert(dataSource);
		jdbcInsert.setTableName("product");
		jdbcInsert.setGeneratedKeyName("id");
		return jdbcInsert;
	}
	
}
