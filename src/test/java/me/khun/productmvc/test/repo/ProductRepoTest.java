package me.khun.productmvc.test.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import me.khun.productmvc.config.RootConfig;
import me.khun.productmvc.model.entity.Category;
import me.khun.productmvc.model.entity.Product;
import me.khun.productmvc.model.repo.CategoryRepo;
import me.khun.productmvc.model.repo.ProductRepo;

@SpringJUnitConfig(classes = RootConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class ProductRepoTest {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	private Category category1;
	
	private Category category2;
	
	@BeforeEach
	public void beforeEach() {
		category1 = categoryRepo.findById(1L);
		category2 = categoryRepo.findById(2L);
	}
	
	@Test
	@Order(1)
	@DisplayName("Find all products when there is no data and should return empty list.")
	@Sql(scripts = {
		"classpath:/database.sql",
		"classpath:/preinsert-categories.sql"
	})
	public void findAllProducts_WhenThereIsNoData_ReturnEmptyList() {
		var products = productRepo.findAll();
		assertEquals(0, products.size());
	}
	
	@Test
	@Order(2)
	@DisplayName("Get the product count when there is no data and should return 0.")
	public void getProductCount_WhenThereIsNoData() {
		var count = productRepo.getCount();
		assertEquals(0, count);
	}
	
	@Test
	@Order(3)
	@DisplayName("Create a product.")
	public void createProduct() {
		var product = new Product();
		product.setName("Samsung Note 13");
		product.setCategory(category1);
		product.setUnitPrice(1000000);
		product.setDescription("Samsung Note 13 Description");
		
		var created = productRepo.create(product);
		product.setId(created.getId());
		
		assertEquals(product, created);
		assertEquals(created, productRepo.findById(created.getId()));
	}
	
	@Test
	@Order(4)
	@DisplayName("Create a product without the non-required fields.")
	public void createProduct_WithoutNonRequiredFields() {
		var product = new Product();
		product.setName("Dell XPS 13");
		product.setCategory(category1);
		product.setUnitPrice(1400000);
		
		var created = productRepo.create(product);
		
		product.setId(created.getId());
		
		assertEquals(product, created);
		assertEquals(created, productRepo.findById(created.getId()));
	}
	
	@Test
	@Order(5)
	@DisplayName("Should thorw the exceptio when creating a product with the name that already exists.")
	public void createProduct_NameAlreadyExists_ThrowsException() {
		var product = new Product();
		product.setName("Samsung Note 13");
		product.setCategory(category2);
		product.setUnitPrice(900000);
		assertThrows(DataAccessException.class, () -> productRepo.create(product));
	}
	
	@Test
	@Order(5)
	@DisplayName("Should throw the exception when creating a product with the category that does not exist.")
	public void createProduct_CategoryNotExists_ThrowsException() {
		var category = new Category();
		category.setId(3L);
		category.setName("New Category");
		
		var product = new Product();
		product.setName("New Product");
		product.setCategory(category);
		product.setUnitPrice(10000);
		assertThrows(DataAccessException.class, () -> productRepo.create(product));
	}
	
	@Test
	@Order(6)
	@DisplayName("Update the product.")
	public void updateProduct() {
		var product = productRepo.findById(2L);
		product.setName("Dell XPS 13 Developer Edition");
		product.setCategory(category2);
		product.setUnitPrice(0);
		product.setDescription("Dell XPS 13 Developer Edition Description");
		
		var updated = productRepo.update(product);
		
		assertTrue(updated);
		assertEquals(product, productRepo.findById(product.getId()));
	}
	
	@Test
	@Order(7)
	@DisplayName("Should throw the exception when updating the product with the name that already exists.")
	public void updateProduct_NameAlredyExists_ThrowsException() {
		var product = productRepo.findById(2L);
		product.setName("Samsung Note 13");
		assertThrows(DataAccessException.class, () -> productRepo.update(product));
	}
	
	@Test
	@Order(7)
	@DisplayName("Should throw the exception when updating a product with the category that does not exist.")
	public void updateProduct_CategoryNotExists_ThrowsException() {
		var category = new Category();
		category.setId(3L);
		category.setName("New Category");
		
		var product = productRepo.findById(2L);
		product.setName("New Product");
		product.setCategory(category);
		product.setUnitPrice(10000);
		assertThrows(DataAccessException.class, () -> productRepo.update(product));
	}
	
	@Test
	@Order(8)
	@DisplayName("Find the product by the id that exists.")
	public void findProductById_IdExists() {
		var id = 1L;
		var product = productRepo.findById(id);
		assertNotNull(product);
		assertEquals("Samsung Note 13", product.getName());
		assertEquals(category1, product.getCategory());
		assertEquals(1000000, product.getUnitPrice());
		assertEquals("Samsung Note 13 Description", product.getDescription());
	}
	
	@Test
	@Order(9)
	@DisplayName("Find the product by the id of null value.")
	public void findProductById_IdIsNull_ReturnNull() {
		assertNull(productRepo.findById(null));
	}
	
	@Test
	@Order(10)
	@DisplayName("Find the product by the id that does not exist.")
	public void findProductById_IdNotExists_ReturnNull() {
		var id = 3L;
		var product = productRepo.findById(id);
		assertNull(product);
	}
	
	@Test
	@Order(11)
	@DisplayName("Find the products by the keyword.")
	public void findProductsByKeyword() {
		var product1 = productRepo.findById(1L);
		var product2 = productRepo.findById(2L);
		var productList = List.of(product1, product2);
		
		var keyword = "samsung";
		var resultList = productRepo.findByKeyword(keyword);
		assertEquals(1, resultList.size());
		assertTrue(resultList.contains(product1));
		
		keyword = "xps";
		resultList = productRepo.findByKeyword(keyword);
		assertEquals(1, resultList.size());
		assertTrue(resultList.contains(product2));
		
		keyword = "13";
		resultList = productRepo.findByKeyword(keyword);
		assertEquals(2, resultList.size());
		assertTrue(resultList.containsAll(productList));
		
		keyword = "lenovo";
		resultList = productRepo.findByKeyword(keyword);
		assertEquals(0, resultList.size());
		
		keyword = "descrip";
		resultList = productRepo.findByKeyword(keyword);
		assertEquals(2, resultList.size());
		assertTrue(resultList.containsAll(productList));
	}
	
	@Test
	@Order(12)
	@DisplayName("Find all products.")
	public void findAllProducts() {
		var products = productRepo.findAll();
		assertEquals(2, products.size());
		assertTrue(products.contains(productRepo.findById(1L)));
		assertTrue(products.contains(productRepo.findById(2L)));
	}
	
	@Test
	@Order(13)
	@DisplayName("Get the product count.")
	public void getProductCount() {
		var count = productRepo.getCount();
		assertEquals(2, count);
	}
	
	@Test
	@Order(14)
	@DisplayName("Delete the product by the id that exists.")
	public void deleteProductById_IdExists_ReturnTrue() {
		var id = 1L;
		var product = productRepo.findById(id);
		var deleted = productRepo.deleteById(id);
		assertEquals(product, deleted);
		assertNull(productRepo.findById(id));
	}
	
	@Test
	@Order(15)
	@DisplayName("Delete the product with the id of null value.")
	public void deleteProductById_IdIsNull_ReturnNull() {
		assertNull(productRepo.deleteById(null));
	}
	
	@Test
	@Order(16)
	@DisplayName("Delete the product by the id that does not exist.")
	public void deleteProductById_IdNotExists_ReturnFalse() {
		var id = 3L;
		var deleted = productRepo.deleteById(id);
		assertNull(deleted);
	}
	
}
