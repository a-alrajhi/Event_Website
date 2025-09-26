package com.event_website.Controller;

import com.event_website.Dto.CategoryDTO;
import com.event_website.Dto.EventDto;
import com.event_website.Dto.ErrorDTO;
import com.event_website.Request.CreateCategoryRequest;
import com.event_website.Request.UpdateCategoryRequest;
import com.event_website.Service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "Categories", description = "APIs for managing event categories")
public class CategoryController {

  @Autowired private CategoryService categoryService;

  @Operation(
          summary = "Get all categories",
          description = "Returns a list of all categories.",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Categories retrieved successfully",
                          content = @Content(schema = @Schema(implementation = CategoryDTO.class))
                  ),
                  @ApiResponse(
                          responseCode = "500",
                          description = "Internal server error",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  )
          }
  )
  @GetMapping("/categories")
  public ResponseEntity<List<CategoryDTO>> findAll() {
    return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
  }

  @Operation(
          summary = "Get events by category ID",
          description = "Returns a list of events that belong to a specific category.",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Events retrieved successfully",
                          content = @Content(schema = @Schema(implementation = EventDto.class))
                  ),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Category not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  ),
                  @ApiResponse(
                          responseCode = "500",
                          description = "Internal server error",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  )
          }
  )
  @GetMapping("/events/category/{categoryId}")
  public ResponseEntity<List<EventDto>> getAllEventsByCategory(
          @Parameter(description = "ID of the category") @PathVariable Integer categoryId) {
    return ResponseEntity.ok(categoryService.findAllEventsByCategory(categoryId));
  }

  @Operation(
          summary = "Find category by English name",
          description = "Returns an optional category matching the provided English name.",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Category found",
                          content = @Content(schema = @Schema(implementation = CategoryDTO.class))
                  ),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Category not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  )
          }
  )
  @GetMapping("/categories/by-name")
  public ResponseEntity<Optional<CategoryDTO>> findByName(
          @Parameter(description = "English name of the category") @RequestParam String name) {
    return new ResponseEntity<>(categoryService.findByName(name), HttpStatus.OK);
  }

  @Operation(
          summary = "Find category by Arabic name",
          description = "Returns an optional category matching the provided Arabic name.",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Category found",
                          content = @Content(schema = @Schema(implementation = CategoryDTO.class))
                  ),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Category not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  )
          }
  )
  @GetMapping("/categories/by-ar-name")
  public ResponseEntity<Optional<CategoryDTO>> findByArName(
          @Parameter(description = "Arabic name of the category") @RequestParam String name) {
    return new ResponseEntity<>(categoryService.findByArName(name), HttpStatus.OK);
  }

  @Operation(
          summary = "Create a new category",
          description = "Creates a new category with the provided details.",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Category created successfully",
                          content = @Content(schema = @Schema(implementation = CategoryDTO.class))
                  ),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Category already exists or validation failed",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  ),
                  @ApiResponse(
                          responseCode = "500",
                          description = "Internal server error",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  )
          }
  )
  @PostMapping("/admin/categories")
  public ResponseEntity<CategoryDTO> createCategory(
          @Valid @RequestBody CreateCategoryRequest request) {
    if (categoryService.existsByName(request.getName())) {
      throw new RuntimeException("Category already exists. Cannot add a duplicate category.");
    }
    return ResponseEntity.ok(categoryService.createCategory(request));
  }

  @Operation(
          summary = "Get category by ID",
          description = "Returns the category identified by the provided ID.",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Category retrieved successfully",
                          content = @Content(schema = @Schema(implementation = CategoryDTO.class))
                  ),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Category not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  )
          }
  )
  @GetMapping("/categories/{id}")
  public CategoryDTO getCategoryById(
          @Parameter(description = "ID of the category") @PathVariable Integer id) {
    return categoryService.getCategoryById(id);
  }

  @Operation(
          summary = "Update an existing category",
          description = "Updates the category identified by the provided ID with new details.",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Category updated successfully",
                          content = @Content(schema = @Schema(implementation = CategoryDTO.class))
                  ),
                  @ApiResponse(
                          responseCode = "400",
                          description = "Validation failed or bad request",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  ),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Category not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  )
          }
  )
  @PutMapping("/admin/categories/{id}")
  public CategoryDTO updateCategory(
          @Parameter(description = "ID of the category") @PathVariable Integer id,
          @Valid @RequestBody UpdateCategoryRequest request) {
    return categoryService.update(id, request);
  }

  @Operation(
          summary = "Delete a category",
          description = "Deletes the category identified by the provided ID.",
          responses = {
                  @ApiResponse(
                          responseCode = "200",
                          description = "Category deleted successfully"
                  ),
                  @ApiResponse(
                          responseCode = "404",
                          description = "Category not found",
                          content = @Content(schema = @Schema(implementation = ErrorDTO.class))
                  )
          }
  )
  @DeleteMapping("/admin/categories/{id}")
  public void deleteCategory(
          @Parameter(description = "ID of the category") @PathVariable Integer id) {
    categoryService.deleteCategory(id);
  }
}
