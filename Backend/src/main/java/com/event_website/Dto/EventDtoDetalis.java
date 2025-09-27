package com.event_website.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Schema(description = "Location name of the event", example = "KAFD Opera House")
    private String locationName; // Add location name if needed

    @Schema(description = "Starting date of the event", example = "2025-10-9")
    private LocalDate date;

    @Schema(description = "all dates of the event", example = "[2025-10-9,2025-10-10]")
    private List<LocalDate> dates;

    @Schema(description = "Starting time of the event", example = "12:30")
    private String time;

    @Schema(description = "Remaining seats of the first slot the event", example = "20")
    private Integer remaining;

    @Schema(description = "Overall attendees", example = "200")
    private Integer attendees;
}
