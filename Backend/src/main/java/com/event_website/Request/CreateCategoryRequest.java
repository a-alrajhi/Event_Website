package com.event_website.Request;

import com.event_website.Dto.CategoryDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request body for creating a new category.
 *
 * <p>Contains English and Arabic names and descriptions, along with an optional photo. Used in POST
 * requests to the /category/admin/categories endpoint.
 *
 * <p>Validation is applied to ensure required fields are present.
 *
 * @author Abdulrahman Al Rajhi
 * @since 09-09-2025
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryRequest {
  @NotBlank private String name;
  private String arName;
  private String photo;
  private String description;
  private String arDescription;

  @Override
  public String toString() {
    return "CreateCategoryRequest{" + "name='" + name + '\'' + ", arName='" + arName + '\'' + '}';
  }

  public CreateCategoryRequest(CategoryDTO categoryDTO) {
    this.name = categoryDTO.getName();
    this.arName = categoryDTO.getArName();
    this.photo = categoryDTO.getPhoto();
    this.description = categoryDTO.getDescription();
    this.arDescription = categoryDTO.getArDescription();
  }
}
