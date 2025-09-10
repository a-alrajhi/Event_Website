package com.event_website.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
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
}
