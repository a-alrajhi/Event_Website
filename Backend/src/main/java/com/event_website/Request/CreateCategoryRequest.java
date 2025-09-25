package com.event_website.Request;

import com.event_website.Dto.CategoryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request body for creating a new category.
 *
 * <p>Contains English and Arabic names and descriptions, along with an optional photo.
 * Used in POST requests to the /category/admin/categories endpoint.
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
@Schema(description = "Request payload to create a new category with multilingual support")
public class CreateCategoryRequest {

  @NotBlank
  @Schema(description = "Category name in English", example = "Music")
  private String name;

  @Schema(description = "Category name in Arabic", example = "موسيقى")
  private String arName;

  @Schema(description = "URL or path to category photo", example = "https://example.com/images/music.jpg", nullable = true)
  private String photo;

  @Schema(description = "Category description in English", example = "Category for music events")
  private String description;

  @Schema(description = "Category description in Arabic", example = "فئة للفعاليات الموسيقية")
  private String arDescription;

  @Override
  public String toString() {
    return "CreateCategoryRequest{" +
            "name='" + name + '\'' +
            ", arName='" + arName + '\'' +
            '}';
  }

  public CreateCategoryRequest(CategoryDTO categoryDTO) {
    this.name = categoryDTO.getName();
    this.arName = categoryDTO.getArName();
    this.photo = categoryDTO.getPhoto();
    this.description = categoryDTO.getDescription();
    this.arDescription = categoryDTO.getArDescription();
  }
}
