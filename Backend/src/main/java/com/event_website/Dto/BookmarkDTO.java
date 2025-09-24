package com.event_website.Dto;

/**
 * @author Yazeed
 * DTO class to transfer bookmark data between layers,
 * mapping entity data to a simpler structure.
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Represents a user bookmark for a specific event")
public class BookmarkDTO {

    @Schema(description = "Unique identifier of the bookmark", example = "1")
    private Integer id;

    @Schema(description = "ID of the event that was bookmarked", example = "101")
    private Integer eventId;

    @Schema(description = "ID of the user who bookmarked the event", example = "202")
    private Integer userId;
}
