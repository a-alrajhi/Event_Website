package com.event_website.Request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        name = "UpdateCategoryRequest",
        description = "Request payload for updating an existing category with optional fields"
)
public class UpdateCategoryRequest {

  @Schema(description = "Category name in English", example = "Music")
  private String name;

  @Schema(description = "Category name in Arabic", example = "موسيقى")
  private String arName;

  @Schema(description = "URL or path of the category photo", example = "https://example.com/photos/music.jpg")
  private String photo;

  @Schema(description = "Description in English", example = "All music related events")
  private String description;

  @Schema(description = "Description in Arabic", example = "كل الفعاليات المتعلقة بالموسيقى")
  private String arDescription;

  @Override
  public String toString() {
    return "UpdateCategoryRequest{" + "arName='" + arName + '\'' + ", name='" + name + '\'' + '}';
  }
}
