package me.khun.productmvc.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import me.khun.productmvc.config.RootConfig;
import me.khun.productmvc.model.entity.Category;
import me.khun.productmvc.model.service.CategoryService;
import me.khun.productmvc.model.service.exception.ServiceException;

@SpringJUnitConfig(classes = RootConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class CategoryServiceTest {
	
	@Autowired
	private CategoryService categoryService;

	@Test
	@Order(1)
	@DisplayName("Find all categories when there is no data and should return empty list.")
	@Sql(scripts = "classpath:/database.sql")
	public void findAllCategories_WhenThereIsNoData_ReturnEmptyList() {
		var categories = categoryService.findAll();
		assertEquals(0, categories.size());
	}
	
	@Test
	@Order(2)
	@DisplayName("Get the category count when there is no data and should return 0.")
	public void getCategoryCount_WhenThereIsNoData() {
		var count = categoryService.getCount();
		assertEquals(0, count);
	}
	
	@Test
	@Order(3)
	@DisplayName("Create a category.")
	public void createCategory() {
		var category = new Category();
		category.setName("   Smart Phone   ");
		category.setDescription("   Smart phone description   ");
		
		var created = categoryService.save(category);
		
		category.setId(created.getId());
		category.setName("Smart Phone");
		category.setDescription("Smart phone description");
		
		assertEquals(category, created);
		assertEquals(created, categoryService.findById(created.getId()));
	}
	
	@Test
	@Order(4)
	@DisplayName("Create a category without the non-required fields.")
	public void createCategory_WithoutNonRequiredFields() {
		var category = new Category();
		category.setName("Laptop");
		
		var created = categoryService.save(category);
		
		category.setId(created.getId());
		
		assertEquals(category, created);
		assertEquals(created, categoryService.findById(created.getId()));
	}
	
	@Test
	@Order(5)
	@DisplayName("Should throw the exception when creating a category with the name that already exists.")
	public void createCategory_NameAlreadyExists_ThrowsException() {
		var category = new Category();
		category.setName("Smart Phone");
		assertThrows(ServiceException.class, () -> categoryService.save(category));
	}
	
	@Test
	@Order(6)
	@DisplayName("Should throw the exception when creating a category with the name of null value.")
	public void createCategory_NameIsNull_ThrowsException() {
		var category = new Category();
		category.setName(null);
		assertThrows(ServiceException.class, () -> categoryService.save(category));
	}
	
	@Test
	@Order(7)
	@DisplayName("Should throw the exception when creating a category with the empty name.")
	public void createCategory_NameIsEmpty_ThrowsException() {
		var category = new Category();
		category.setName("");
		assertThrows(ServiceException.class, () -> categoryService.save(category));
	}
	
	@Test
	@Order(8)
	@DisplayName("Should throw the exception when creating a category with the blank name.")
	public void createCategory_NameIsBlank_ThrowsException() {
		var category = new Category();
		category.setName("   ");
		assertThrows(ServiceException.class, () -> categoryService.save(category));
	}
	
	@Test
	@Order(9)
	@DisplayName("Update the category.")
	public void updateCategory() {
		var category = categoryService.findById(2L);
		category.setName("   Laptop Updated   ");
		category.setDescription("   Laptop description updated   ");
		
		var updated = categoryService.save(category);
		
		category.setName("Laptop Updated");
		category.setDescription("Laptop description updated");
		
		assertEquals(category, updated);
		assertEquals(updated, categoryService.findById(updated.getId()));
	}
	
	@Test
	@Order(10)
	@DisplayName("Should throw the exception when updating the category with the name that already exists.")
	public void updateCategory_NameAlreadyExists_ThrowsException() {
		var category = categoryService.findById(2L);
		category.setName("Smart Phone");
		assertThrows(ServiceException.class, () -> categoryService.save(category));
	}
	
	@Test
	@Order(11)
	@DisplayName("Should throw the exception when updating a category with the name of null value.")
	public void updateCategory_NameIsNull_ThrowsException() {
		var category = categoryService.findById(2L);
		category.setName(null);
		assertThrows(ServiceException.class, () -> categoryService.save(category));
	}
	
	@Test
	@Order(12)
	@DisplayName("Should throw the exception when updating a category with the empty name.")
	public void updateCategory_NameIsEmpty_ThrowsException() {
		var category = categoryService.findById(2L);
		category.setName("");
		assertThrows(ServiceException.class, () -> categoryService.save(category));
	}
	
	@Test
	@Order(13)
	@DisplayName("Should throw the exception when updating a category with the blank name.")
	public void updateCategory_NameIsBlank_ThrowsException() {
		var category = categoryService.findById(2L);
		category.setName("   ");
		assertThrows(ServiceException.class, () -> categoryService.save(category));
	}
	
	@Test
	@Order(14)
	@DisplayName("Find the category by the id that exists.")
	public void findCategoryById_IdExists() {
		var id = 1L;
		var category = categoryService.findById(id);
		assertNotNull(categoryService);
		assertEquals("Smart Phone", category.getName());
		assertEquals("Smart phone description", category.getDescription());
	}
	
	@Test
	@Order(15)
	@DisplayName("Find the category by the id that does not exist.")
	public void findCategoryById_IdNotExists_ReturnNull() {
		var id = 3L;
		var category = categoryService.findById(id);
		assertNull(category);
	}
	
	@Test
	@Order(16)
	@DisplayName("Find the categories by the keyword.")
	public void findCategoriesByKeyword() {
		var category1 = categoryService.findById(1L);
		var category2 = categoryService.findById(2L);
		var categoryList = List.of(category1, category2);
		
		var keyword = "smart";
		var resultList = categoryService.findByKeyword(keyword);
		assertEquals(1, resultList.size());
		assertTrue(resultList.contains(category1));
		
		keyword = "lap";
		resultList = categoryService.findByKeyword(keyword);
		assertEquals(1, resultList.size());
		assertTrue(resultList.contains(category2));
		
		keyword = "description";
		resultList = categoryService.findByKeyword(keyword);
		assertEquals(2, resultList.size());
		assertTrue(resultList.containsAll(categoryList));
		
		keyword = "keyword";
		resultList = categoryService.findByKeyword(keyword);
		assertEquals(0, resultList.size());
	}
	
	@Test
	@Order(17)
	@DisplayName("Find all categories.")
	public void findAllCategories() {
		var category1 = categoryService.findById(1L);
		var category2 = categoryService.findById(2L);
		var categoryList = List.of(category1, category2);
		
		var categories = categoryService.findAll();
		
		assertEquals(2, categories.size());
		assertTrue(categories.containsAll(categoryList));
	}
	
	@Test
	@Order(18)
	@DisplayName("Get the category count.")
	public void getCategoryCount() {
		var count = categoryService.getCount();
		assertEquals(2, count);
	}
	
	@Test
	@Order(19)
	@DisplayName("Delete the category by the id that exists.")
	public void deleteCategoryById_IdExists_ReturnTrue() {
		var id = 1L;
		var category = categoryService.findById(id);
		var deleted = categoryService.deleteById(id);
		assertEquals(category, deleted);
		assertNull(categoryService.findById(id));
	}
	
	@Test
	@Order(20)
	@DisplayName("Delete the category by the id that does not exists.")
	public void deleteCategoryById_IdNotExists_ReturnFalse() {
		var id = 1L;
		var deleted = categoryService.deleteById(id);
		assertNull(deleted);
	}
	
	@Test
	@Order(21)
	@DisplayName("Delete the category which id exists.")
	public void deleteCategory_IdExists_ReturnTrue() {
		var id = 2L;
		var category = categoryService.findById(id);
		var deleted = categoryService.delete(category);
		assertTrue(deleted);
		assertNull(categoryService.findById(id));
	}
	
	@Test
	@Order(22)
	@DisplayName("Delete the category which id does not exist.")
	public void deleteCategory_IdNotExists_ReturnFalse() {
		var category = new Category();
		category.setId(4L);
		category.setName("Tablet");
		
		var deleted = categoryService.delete(category);
		assertFalse(deleted);
	}
	
	@Test
	@Order(23)
	@DisplayName("Should throw the exception when deleting the category that is in use.")
	@Sql(statements = {
		"INSERT INTO `category` (`id`, `name`) VALUES (3, 'Category')",
		"INSERT INTO `product` (`name`, `category_id`, `unit_price`) VALUES ('Product', 3, 10000)"
	})
	public void deleteCategory_ThatIsInUse_ThrowsException() {
		assertThrows(ServiceException.class, () -> categoryService.deleteById(3L));
	}
	
}
