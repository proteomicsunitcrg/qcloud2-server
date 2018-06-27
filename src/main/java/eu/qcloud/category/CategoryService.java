package eu.qcloud.category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<>();
		categoryRepository.findAll().forEach(categories::add);		
		return categories;
	}
	
	public Category addCategory(Category category) {
		category.setApiKey(UUID.randomUUID());
		return categoryRepository.save(category);
	}

	public Category getCategoryByName(String categoryName) {
		return categoryRepository.findByName(categoryName);
	}
	
	public void makeCategoryMain(UUID categoryApiKey) {
		List<Category> categories = new ArrayList<>();
		categoryRepository.findAll().forEach(categories::add);
		for(Category c: categories) {
			if(c.getApiKey().equals(categoryApiKey)) {
				c.setMainDataSource(true);
			}else {
				c.setMainDataSource(false);
			}
			categoryRepository.save(c);
		}
		
	}

	public Category getMainCategory() {
		return categoryRepository.findTop1ByIsMainDataSourceTrue();
	}
}