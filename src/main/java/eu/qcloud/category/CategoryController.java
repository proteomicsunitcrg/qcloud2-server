package eu.qcloud.category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * Category controller
 * @author dmancera
 */
@RestController
@PreAuthorize("hasRole('MANAGER')")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="/api/category", method = RequestMethod.GET)
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/api/category", method = RequestMethod.POST)
	public Category addCategory(@RequestBody Category category) {
		return categoryService.addCategory(category);
	}
	@RequestMapping(value="/api/category/{categoryName}", method = RequestMethod.GET)
	public Category getCategoryByName(@PathVariable String categoryName) {
		return categoryService.getCategoryByName(categoryName);
	}
}
