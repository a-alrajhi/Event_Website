package com.event_website.Service;

import com.event_website.Dto.CategoryDTO;
import com.event_website.Dto.EventDto;
import com.event_website.Entity.Category;
import com.event_website.Repository.CategoryRepo;
import com.event_website.Repository.EventRepo;
import com.event_website.Request.CreateCategoryRequest;
import com.event_website.Request.UpdateCategoryRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling business logic related to categories.
 *
 * <p>Provides operations for creating, updating, retrieving, and deleting category data. Also
 * performs conversion between {@link Category} entities and {@link CategoryDTO} objects.
 *
 * <p>Used by {@code CategoryController} to interact with the persistence layer via {@code
 * CategoryRepo}.
 *
 * @author Abdulrahman Al Rajhi
 * @since 09-09-2025
 * @version 1.0
 */
@Service
public class CategoryService {

  @Autowired private CategoryRepo categoryRepo;
  @Autowired private EventRepo eventRepo;

  public List<CategoryDTO> findAll() {
    return categoryRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
  }

  public Optional<CategoryDTO> findByName(String categoryName) {
    return categoryRepo.findByName(categoryName).map(this::toDto);
  }

  public boolean existsByName(String categoryName) {
    return categoryRepo.existsByName(categoryName);
  }

  public Optional<CategoryDTO> findByArName(String arName) {
    return categoryRepo.findByArName(arName).map(this::toDto);
  }

  public CategoryDTO createCategory(CreateCategoryRequest request) {
    Category category = new Category();
    category.setName(request.getName());
    category.setArName(request.getArName());
    category.setDescription(request.getDescription());
    category.setArDescription(request.getArDescription());
    category.setPhoto(request.getPhoto());

    Category saved = categoryRepo.save(category);
    return toDto(saved);
  }

  public CategoryDTO update(Integer id, @Valid UpdateCategoryRequest request) {
    Category category =
        categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

    // updating fields only if they are provided
    if (request.getName() != null) {
      category.setName(request.getName());
    }
    if (request.getArName() != null) {
      category.setArName(request.getArName());
    }
    if (request.getDescription() != null) {
      category.setDescription(request.getDescription());
    }
    if (request.getArDescription() != null) {
      category.setArDescription(request.getArDescription());
    }
    if (request.getPhoto() != null) {
      category.setPhoto(request.getPhoto());
    }
    Category updated = categoryRepo.save(category);
    return toDto(updated);
  }

  public void deleteCategory(Integer id) {
    categoryRepo.deleteById(id);
  }

  public CategoryDTO getCategoryById(Integer id) {
    Category category =
        categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    return toDto(category);
  }

  private CategoryDTO toDto(Category category) {
    CategoryDTO dto = new CategoryDTO();
    dto.setId(category.getId());
    dto.setName(category.getName());
    dto.setArName(category.getArName());
    dto.setPhoto(category.getPhoto());
    dto.setDescription(category.getDescription());
    dto.setArDescription(category.getArDescription());
    return dto;
  }


  public List<EventDto> findAllEventsByCategory(Integer categoryId) {
    return eventRepo.findByCategoryId(categoryId).stream().map(EventDto::fromEntity).toList();
  }
}
