package com.event_website.Dto;

import com.event_website.Entity.Category;
import com.event_website.Entity.Event;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for the Event entity.
 *
 * <p>Used to send and receive simplified event data in API operations.
 * This DTO does not expose internal logic or entity relations directly.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Event data transfer object, representing core event details")
public class EventDto {

    @Schema(description = "Unique identifier of the event", example = "101")
    private Integer id;

    @Schema(description = "Event name in English", example = "Tech Conference 2025")
    private String name;

    @Schema(description = "Event name in Arabic", example = "مؤتمر التقنية 2025")
    private String arName;

    @Schema(description = "Event description in English", example = "A gathering of tech enthusiasts and professionals.")
    private String description;

    @Schema(description = "Event description in Arabic", example = "تجمع لعشاق التقنية والمحترفين.")
    private String arDescription;

    @Schema(description = "URL to the event banner or photo", example = "https://example.com/events/techconf2025.jpg")
    private String photoUrl;

    @Schema(description = "Indicates if the event has assigned seating", example = "true")
    private Boolean hasAssignedSeating;

    @Schema(description = "ID of the category this event belongs to", example = "3")
    private Integer categoryId;

    /**
     * Maps an Event entity to a DTO.
     *
     * @param event the entity to map
     * @return a new EventDto object
     */
    public static EventDto fromEntity(Event event) {
        if (event == null) return null;

        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setArName(event.getArName());
        dto.setDescription(event.getDescription());
        dto.setArDescription(event.getArDescription());
        dto.setPhotoUrl(event.getPhotoUrl());
        dto.setHasAssignedSeating(event.getHasAssignedSeating());

        if (event.getCategory() != null) {
            dto.setCategoryId(event.getCategory().getId());
        }
        return dto;
    }

    /**
     * Converts the DTO into an Event entity using the provided category.
     *
     * @param category the Category entity to associate
     * @return Event entity populated with data from this DTO
     */
    public Event toEntity(Category category) {
        Event event = new Event();
        event.setId(this.id);
        event.setName(this.name);
        event.setArName(this.arName);
        event.setDescription(this.description);
        event.setArDescription(this.arDescription);
        event.setPhotoUrl(this.photoUrl);
        event.setHasAssignedSeating(this.hasAssignedSeating);

        if (category != null) {
            event.setCategory(category);
        }
        return event;
    }
}
