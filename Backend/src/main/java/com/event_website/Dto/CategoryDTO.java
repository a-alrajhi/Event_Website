package com.event_website.Dto;

import com.event_website.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for the {@code Category} entity.
 *
 * <p>Used to expose category data to clients via API responses without exposing the full entity.
 * Typically returned in responses from endpoints like /api/categories.
 *
 * <p>This DTO includes both English and Arabic names and descriptions, along with an optional
 * photo.
 *
 * @author Abdulrahman Al Rajhi
 * @since 09-09-2025
 * @version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
  private Integer id;
  private String name;
  private String arName;

  private String photo;
  private String description;
  private String arDescription;

  public Category toEntity() {
    Category category = new Category();
    category.setId(id);
    category.setName(name);
    category.setArName(arName);
    category.setPhoto(photo);
    category.setDescription(description);
    category.setArDescription(arDescription);
    return category;
  }
}
