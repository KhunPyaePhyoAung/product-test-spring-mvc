package me.khun.productmvc.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import me.khun.productmvc.config.RootConfig;
import me.khun.productmvc.model.entity.Category;
import me.khun.productmvc.model.entity.Product;
import me.khun.productmvc.model.repo.CategoryRepo;
import me.khun.productmvc.model.service.ProductService;
import me.khun.productmvc.model.service.exception.ServiceException;

@SpringJUnitConfig(classes = RootConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceTest {
	
	@Autowired
	private ProductService productService;
	
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
		var products = productService.findAll();
		assertEquals(0, products.size());
	}
	
	@Test
	@Order(2)
	@DisplayName("Get the product count when there is no data and should return 0.")
	public void getProductCount_WhenThereIsNoData() {
		var count = productService.getCount();
		assertEquals(0, count);
	}
	
	@Test
	@Order(3)
	@DisplayName("Create a product.")
	public void createProduct() {
		var product = new Product();
		product.setName("   Samsung Note 13   ");
		product.setCategory(category1);
		product.setUnitPrice(1000000);
		product.setDescription("   Samsung Note 13 Description   ");
		
		var created = productService.save(product);
		
		product.setId(created.getId());
		product.setName("Samsung Note 13");
		product.setDescription("Samsung Note 13 Description");
		
		assertEquals(product, created);
		assertEquals(created, productService.findById(created.getId()));
	}
	
	@Test
	@Order(4)
	@DisplayName("Create a product without the non-required fields.")
	public void createProduct_WithoutNonRequiredFields() {
		var product = new Product();
		product.setName("Dell XPS 13");
		product.setCategory(category1);
		product.setUnitPrice(1400000);
		
		var created = productService.save(product);
		
		product.setId(created.getId());
		
		assertEquals(product, created);
		assertEquals(created, productService.findById(created.getId()));
	}
	
	@Test
	@Order(5)
	@DisplayName("Should thorw the exceptio when creating a product with the name that already exists.")
	public void createProduct_NameAlreadyExists_ThrowsException() {
		var product = new Product();
		product.setName("Samsung Note 13");
		product.setCategory(category2);
		product.setUnitPrice(900000);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(6)
	@DisplayName("Should throw the exception when creating a product with the name of null value.")
	public void createProduct_NameIsNull_ThrowsException() {
		var product = new Product();
		product.setName(null);
		product.setCategory(category1);
		product.setUnitPrice(10000);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(7)
	@DisplayName("Should throw the exception when creating a product with the empty name.")
	public void createProduct_NameIsEmpty_ThrowsException() {
		var product = new Product();
		product.setName("");
		product.setCategory(category1);
		product.setUnitPrice(10000);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(8)
	@DisplayName("Should throw the exception when creating a product with the blank name.")
	public void createProduct_NameIsBlank_ThrowsException() {
		var product = new Product();
		product.setName("   ");
		product.setCategory(category1);
		product.setUnitPrice(10000);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(9)
	@DisplayName("Should throw the exception when creating a product with the category of null value.")
	public void createProduct_CategoryIsNull_ThrowsException() {
		var product = new Product();
		product.setName("New Product");
		product.setCategory(null);
		product.setUnitPrice(10000);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(11)
	@DisplayName("Should throw the exception when creating a product with the unit price less than 0.")
	public void createProduct_UnitPirceLessThanZero_ThrowsException() {
		var product = new Product();
		product.setName("New Product");
		product.setCategory(category1);
		product.setUnitPrice(-1);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(12)
	@DisplayName("Update the product.")
	public void updateProduct() {
		var product = productService.findById(2L);
		product.setName("   Dell XPS 13 Developer Edition   ");
		product.setCategory(category2);
		product.setUnitPrice(0);
		product.setDescription("   Dell XPS 13 Developer Edition Description   ");
		
		var updated = productService.save(product);
		
		product.setName("Dell XPS 13 Developer Edition");
		product.setDescription("Dell XPS 13 Developer Edition Description");
		
		assertEquals(product, updated);
		assertEquals(updated, productService.findById(updated.getId()));
	}
	
	@Test
	@Order(13)
	@DisplayName("Should throw the exception when updating the product with the name that already exists.")
	public void updateProduct_NameAlredyExists_ThrowsException() {
		var product = productService.findById(2L);
		product.setName("Samsung Note 13");
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(14)
	@DisplayName("Should throw the exception when updating a product with the name of null value.")
	public void updateProduct_NameIsNull_ThrowsException() {
		var product = productService.findById(2L);
		product.setName(null);
		product.setCategory(category1);
		product.setUnitPrice(10000);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(15)
	@DisplayName("Should throw the exception when updating a product with the empty name.")
	public void updateProduct_NameIsEmpty_ThrowsException() {
		var product = productService.findById(2L);
		product.setName("");
		product.setCategory(category1);
		product.setUnitPrice(10000);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(16)
	@DisplayName("Should throw the exception when updating a product with the blank name.")
	public void updateProduct_NameIsBlank_ThrowsException() {
		var product = productService.findById(2L);
		product.setName("   ");
		product.setCategory(category1);
		product.setUnitPrice(10000);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(17)
	@DisplayName("Should throw the exception when updating a product with the category of null value.")
	public void updateProduct_CategoryIsNull_ThrowsException() {
		var product = productService.findById(2L);
		product.setName("New Product");
		product.setCategory(null);
		product.setUnitPrice(10000);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(19)
	@DisplayName("Should throw the exception when updating a product with the unit price less than 0.")
	public void updateProduct_UnitPirceLessThanZero_ThrowsException() {
		var product = productService.findById(2L);
		product.setName("New Product");
		product.setCategory(category1);
		product.setUnitPrice(-1);
		assertThrows(ServiceException.class, () -> productService.save(product));
	}
	
	@Test
	@Order(20)
	@DisplayName("Find the product by the id that exists.")
	public void findProductById_IdExists() {
		var id = 1L;
		var product = productService.findById(id);
		assertNotNull(product);
		assertEquals("Samsung Note 13", product.getName());
		assertEquals(category1, product.getCategory());
		assertEquals(1000000, product.getUnitPrice());
		assertEquals("Samsung Note 13 Description", product.getDescription());
	}
	
	@Test
	@Order(21)
	@DisplayName("Find the product by the id that does not exist.")
	public void findProductById_IdNotExists_ReturnNull() {
		var id = 3L;
		var product = productService.findById(id);
		assertNull(product);
	}
	
	@Test
	@Order(22)
	@DisplayName("Find the products by the keyword.")
	public void findProductsByKeyword() {
		var product1 = productService.findById(1L);
		var product2 = productService.findById(2L);
		var productList = List.of(product1, product2);
		
		var keyword = "samsung";
		var resultList = productService.findByKeyword(keyword);
		assertEquals(1, resultList.size());
		assertTrue(resultList.contains(product1));
		
		keyword = "xps";
		resultList = productService.findByKeyword(keyword);
		assertEquals(1, resultList.size());
		assertTrue(resultList.contains(product2));
		
		keyword = "13";
		resultList = productService.findByKeyword(keyword);
		assertEquals(2, resultList.size());
		assertTrue(resultList.containsAll(productList));
		
		keyword = "lenovo";
		resultList = productService.findByKeyword(keyword);
		assertEquals(0, resultList.size());
		
		keyword = "descrip";
		resultList = productService.findByKeyword(keyword);
		assertEquals(2, resultList.size());
		assertTrue(resultList.containsAll(productList));
	}
	
	@Test
	@Order(23)
	@DisplayName("Find all products.")
	public void findAllProducts() {
		var products = productService.findAll();
		assertEquals(2, products.size());
		assertTrue(products.contains(productService.findById(1L)));
		assertTrue(products.contains(productService.findById(2L)));
	}
	
	@Test
	@Order(24)
	@DisplayName("Get the product count.")
	public void getProductCount() {
		var count = productService.getCount();
		assertEquals(2, count);
	}
	
	@Test
	@Order(25)
	@DisplayName("Delete the product by the id that exists.")
	public void deleteProductById_IdExists_ReturnTrue() {
		var id = 1L;
		var product = productService.findById(id);
		var deleted = productService.deleteById(id);
		assertEquals(product, deleted);
		assertNull(productService.findById(id));
	}
	
	@Test
	@Order(26)
	@DisplayName("Delete the product by the id that does not exist.")
	public void deleteProductById_IdNotExists_ReturnFalse() {
		var id = 3L;
		var deleted = productService.deleteById(id);
		assertNull(deleted);
	}
	
	@Test
	@Order(27)
	@DisplayName("Delete the product which id exists.")
	public void deleteProduct_IdExists_ReturnTrue() {
		var id = 2L;
		var product = productService.findById(id);
		var deleted = productService.delete(product);
		assertTrue(deleted);
		assertNull(productService.findById(id));
	}
	
	@Test
	@Order(28)
	@DisplayName("Delete the product which id does not exist.")
	public void deleteProduct_IdNotExists_ReturnFalse() {
		var product = new Product();
		product.setId(1L);
		product.setName("Xiaomi Note 10");
		product.setCategory(category1);
		product.setUnitPrice(1200000);
		
		var deleted = productService.delete(product);
		assertFalse(deleted);
	}
}
