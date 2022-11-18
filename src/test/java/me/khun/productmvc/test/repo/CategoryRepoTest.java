package me.khun.productmvc.test.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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
import me.khun.productmvc.model.repo.CategoryRepo;

@SpringJUnitConfig(classes = RootConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class CategoryRepoTest {
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Test
	@Order(1)
	@DisplayName("Find all categories when there is no data and should return empty list.")
	@Sql(scripts = "classpath:/database.sql")
	public void findAllCategories_WhenThereIsNoData_ReturnEmptyList() {
		var categories = categoryRepo.findAll();
		assertEquals(0, categories.size());
	}
	
	@Test
	@Order(2)
	@DisplayName("Get the category count when there is no data and should return 0.")
	public void getCategoryCount_WhenThereIsNoData() {
		var count = categoryRepo.getCount();
		assertEquals(0, count);
	}
	
	@Test
	@Order(3)
	@DisplayName("Create a category.")
	public void createCategory() {
		var category = new Category();
		category.setName("Smart Phone");
		category.setDescription("Smart phone description");
		
		var created = categoryRepo.create(category);
		
		category.setId(created.getId());
		
		assertEquals(category, created);
		assertEquals(created, categoryRepo.findById(created.getId()));
	}
	
	@Test
	@Order(4)
	@DisplayName("Create a category without the non-required fields.")
	public void createCategory_WithoutNonRequiredFields() {
		var category = new Category();
		category.setName("Laptop");
		
		var created = categoryRepo.create(category);
		
		category.setId(created.getId());
		
		assertEquals(category, created);
		assertEquals(created, categoryRepo.findById(created.getId()));
	}
	
	@Test
	@Order(5)
	@DisplayName("Should throw the exception when creating a category with the name that already exists.")
	public void createCategory_NameAlreadyExists_ThrowsException() {
		var category = new Category();
		category.setName("Smart Phone");
		assertThrows(DataAccessException.class, () -> categoryRepo.create(category));
	}
	
	@Test
	@Order(6)
	@DisplayName("Update the category.")
	public void updateCategory() {
		var category = categoryRepo.findById(2L);
		category.setName("Laptop Updated");
		category.setDescription("Laptop description updated");
		
		var updated = categoryRepo.update(category);
		
		assertTrue(updated);
		assertEquals(category, categoryRepo.findById(category.getId()));
	}
	
	@Test
	@Order(7)
	@DisplayName("Should throw the exception when updating the category with the name that already exists.")
	public void updateCategory_NameAlreadyExists_ThrowsException() {
		var category = categoryRepo.findById(2L);
		category.setName("Smart Phone");
		assertThrows(DataAccessException.class, () -> categoryRepo.update(category));
	}
	
	@Test
	@Order(8)
	@DisplayName("Find the category by the id of null value.")
	public void findCategoryById_IdIsNull_ReturnNull() {
		assertNull(categoryRepo.findById(null));
	}
	
	@Test
	@Order(9)
	@DisplayName("Find the category by the id that exists.")
	public void findCategoryById_IdExists() {
		var id = 1L;
		var category = categoryRepo.findById(id);
		assertNotNull(categoryRepo);
		assertEquals("Smart Phone", category.getName());
		assertEquals("Smart phone description", category.getDescription());
	}
	
	@Test
	@Order(10)
	@DisplayName("Find the category by the id that does not exist.")
	public void findCategoryById_IdNotExists_ReturnNull() {
		var id = 3L;
		var category = categoryRepo.findById(id);
		assertNull(category);
	}
	
	@Test
	@Order(11)
	@DisplayName("Find the categories by the keyword.")
	public void findCategoriesByKeyword() {
		var category1 = categoryRepo.findById(1L);
		var category2 = categoryRepo.findById(2L);
		var categoryList = List.of(category1, category2);
		
		var keyword = "mart";
		var resultList = categoryRepo.findByKeyword(keyword);
		assertEquals(1, resultList.size());
		assertTrue(resultList.contains(category1));
		
		keyword = "pto";
		resultList = categoryRepo.findByKeyword(keyword);
		assertEquals(1, resultList.size());
		assertTrue(resultList.contains(category2));
		
		keyword = "descrip";
		resultList = categoryRepo.findByKeyword(keyword);
		assertEquals(2, resultList.size());
		assertTrue(resultList.containsAll(categoryList));
		
		keyword = "keyword";
		resultList = categoryRepo.findByKeyword(keyword);
		assertEquals(0, resultList.size());
	}
	
	@Test
	@Order(12)
	@DisplayName("Find all categories.")
	public void findAllCategories() {
		var category1 = categoryRepo.findById(1L);
		var category2 = categoryRepo.findById(2L);
		var categoryList = List.of(category1, category2);
		
		var categories = categoryRepo.findAll();
		
		assertEquals(2, categories.size());
		assertTrue(categories.containsAll(categoryList));
	}
	
	@Test
	@Order(13)
	@DisplayName("Get the category count.")
	public void getCategoryCount() {
		var count = categoryRepo.getCount();
		assertEquals(2, count);
	}
	
	@Test
	@Order(14)
	@DisplayName("Should throw the exception when deleting the category that is in use.")
	@Sql(statements = "INSERT INTO `product` (`name`, `category_id`, `unit_price`) VALUES ('Product', 2, 10000)")
	public void deleteCategory_ThatIsInUse_ThrowsException() {
		assertThrows(DataAccessException.class, () -> categoryRepo.deleteById(2L));
	}
	
	@Test
	@Order(15)
	@DisplayName("Delete the category by the id that exists.")
	public void deleteCategoryById_IdExists_ReturnTrue() {
		var id = 1L;
		var category = categoryRepo.findById(id);
		var deleted = categoryRepo.deleteById(id);
		assertEquals(category, deleted);
		assertNull(categoryRepo.findById(id));
	}
	
	@Test
	@Order(16)
	@DisplayName("Delete the category by the id of null value.")
	public void delteCategoryById_IdIsNull_ReturnNull() {
		assertNull(categoryRepo.deleteById(null));
	}
	
	@Test
	@Order(17)
	@DisplayName("Delete the category by the id that does not exists.")
	public void deleteCategoryById_IdNotExists_ReturnFalse() {
		var id = 1L;
		var deleted = categoryRepo.deleteById(id);
		assertNull(deleted);
	}
	
}
