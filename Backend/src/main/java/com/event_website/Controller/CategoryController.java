package com.event_website.Controller;

import com.event_website.Dto.CategoryDTO;
import com.event_website.Request.CreateCategoryRequest;
import com.event_website.Request.UpdateCategoryRequest;
import com.event_website.Service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing categories.
 *
 * <p>Handles CRUD operations for category resources, including multilingual names and descriptions.
 *
 * <p>Base path: /category
 *
 * <p>Endpoints: - GET /categories - GET /categories/by-name - GET /categories/by-ar-name - GET
 * /categories/{id} - POST /admin/categories - PUT /admin/categories/{id} - DELETE
 * /admin/categories/{id}
 *
 * @author Abdulrahman Al Rajhi
 * @since 09-09-2025
 * @version 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

  @Autowired private CategoryService categoryService;

  // @LogRequest(description = "ADD THIS LATER")
  @GetMapping("/categories")
  public ResponseEntity<List<CategoryDTO>> findAll() {
    return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/categories/by-name")
  public ResponseEntity<Optional<CategoryDTO>> findByName(@RequestParam String name) {
    return new ResponseEntity<>(categoryService.findByName(name), HttpStatus.OK);
  }

  @GetMapping("/categories/by-ar-name")
  public ResponseEntity<Optional<CategoryDTO>> findByArName(@RequestParam String name) {
    return new ResponseEntity<>(categoryService.findByArName(name), HttpStatus.OK);
  }

  @PostMapping("/admin/categories")
  public ResponseEntity<CategoryDTO> createCategory(
      @Valid @RequestBody CreateCategoryRequest request) {
    if (categoryService.existsByName(request.getName())) {
      throw new RuntimeException("Category already exists. Cannot add a duplicate category.");
    }

    return ResponseEntity.ok(categoryService.createCategory(request));
    //    return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.OK);
  }

  @GetMapping("/categories/{id}")
  public CategoryDTO getCategoryById(@PathVariable Integer id) {
    return categoryService.getCategoryById(id);
  }

  @PutMapping("/admin/categories/{id}")
  public CategoryDTO updateCategory(
      @PathVariable Integer id, @Valid @RequestBody UpdateCategoryRequest request) {
    return categoryService.update(id, request);
  }

  @DeleteMapping("/admin/categories/{id}")
  public void deleteCategory(@PathVariable Integer id) {
    categoryService.deleteCategory(id);
  }
}
