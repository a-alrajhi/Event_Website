package com.event_website.Dto;

import com.event_website.Entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Data Transfer Object for the {@code Category} entity.
 *
 * <p>Used to expose category data to clients via API responses without exposing the full entity.
 * Typically returned in responses from endpoints like /api/categories.
 *
 * <p>This DTO includes both English and Arabic names and descriptions, along with an optional
 * photo.
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a category of events, including multilingual support and media")
public class CategoryDTO {

  @Schema(description = "Unique identifier of the category", example = "1")
  private Integer id;

  @Schema(description = "Category name in English", example = "Music")
  private String name;

  @Schema(description = "Category name in Arabic", example = "موسيقى")
  private String arName;

  @Schema(description = "URL to category photo (optional)", example = "https://example.com/images/music.jpg")
  private String photo;

  @Schema(description = "Category description in English", example = "Live music events and festivals")
  private String description;

  @Schema(description = "Category description in Arabic", example = "حفلات موسيقية ومهرجانات حية")
  private String arDescription;

  /**
   * Converts this DTO to its corresponding {@link Category} entity.
   * @return a new Category entity populated with DTO data.
   */
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
