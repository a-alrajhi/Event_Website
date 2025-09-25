package com.event_website.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Statistical summary for a specific event")
public class EventStatDTO {

    @Schema(description = "Unique ID of the event", example = "101")
    private Integer eventId;

    @Schema(description = "Name of the event", example = "Spring Music Festival")
    private String name;

    @Schema(description = "Total number of tickets sold for the event", example = "320")
    private Long ticketsSold;

    @Schema(description = "Total revenue generated from ticket sales for the event", example = "15600.50")
    private BigDecimal revenue;

    @Schema(description = "Number of users who bookmarked this event", example = "120")
    private Long bookmarkCount;

    @Schema(description = "Check-in rate for the event (0.0 to 1.0)", example = "0.85")
    private double checkInRate;

    public EventStatDTO(Integer id, String name, Long count) {
        this.eventId = id;
        this.name = name;
        this.bookmarkCount = count;
    }
}
