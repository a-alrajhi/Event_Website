package com.event_website.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detailed information about an event including category and ticket prices.")
public class EventDtoDetalis {

    @Schema(description = "Unique identifier for the event", example = "101")
    private Integer id;

    @Schema(description = "Name of the event", example = "Tech Conference 2025")
    private String name;

    @Schema(description = "Description of the event", example = "An annual conference for tech enthusiasts.")
    private String description;

    @Schema(description = "URL to the event's main image or banner", example = "https://example.com/images/event.jpg")
    private String photoUrl;

    @Schema(description = "Name of the event's category", example = "Technology")
    private String categoryName;

    @Schema(description = "List of available ticket prices for the event", example = "[100.00, 150.50, 200.00]")
    private List<BigDecimal> prices;  // Add list of ticket prices

    @Schema(description = "Location name of the event", example = "[100.00, 150.50, 200.00]")
    private String locationName;      // Add location name if needed

    // Constructor to ensure compatibility with EventService
    public EventDtoDetalis(Integer id, String name, String description, String photoUrl, String categoryName, List<BigDecimal> prices, String locationName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.categoryName = categoryName;
        this.prices = prices;
        this.locationName = locationName;
    }

    // Default constructor
    public EventDtoDetalis() {}
}
