package com.event_website.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detailed view of an event including slots, category, location, and pricing. Admin only")
public class DetailedEventDto {

    @Schema(description = "Basic event information")
    private EventDto event;

    @Schema(description = "List of available time slots for the event")
    private List<SlotDTO> slots;

    @Schema(description = "Category or type of the event", example = "Music Concert")
    private String category;

    @Schema(description = "Location where the event takes place", example = "Riyadh, Saudi Arabia")
    private String Location;

    @Schema(description = "Minimum price of tickets available for this event", example = "49.99")
    private BigDecimal price;
}
