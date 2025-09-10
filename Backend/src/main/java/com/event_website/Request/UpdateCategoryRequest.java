package com.event_website.Request;

import lombok.Getter;
import lombok.Setter;

/**
 * Request body for updating an existing category.
 *
 * <p>This DTO allows partial or full updates of a category's name, description, and photo in both
 * English and Arabic.
 *
 * <p>Used in PUT requests to the /category/admin/categories/{id} endpoint.
 *
 * @author Abdulrahman Al Rajhi
 * @since 09-09-2025
 * @version 1.0
 */
@Getter
@Setter
public class UpdateCategoryRequest {
  private String name;
  private String arName;
  private String photo;
  private String description;
  private String arDescription;

  @Override
  public String toString() {
    return "UpdateCategoryRequest{" + "arName='" + arName + '\'' + ", name='" + name + '\'' + '}';
  }
}
