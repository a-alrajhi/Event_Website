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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Summary statistics for the admin dashboard")
public class AdminStatsDTO {

    @Schema(description = "Total number of users registered in the system", example = "1234")
    private long totalUsers;

    @Schema(description = "Total number of events created", example = "456")
    private long totalEvents;

    @Schema(description = "Total number of tickets sold", example = "7890")
    private long totalTicketsSold;

    @Schema(description = "Total revenue from ticket sales", example = "152340.75")
    private BigDecimal totalRevenue;

    @Schema(description = "Overall check-in rate (between 0.0 and 1.0)", example = "0.87")
    private double overallCheckInRate;

    @Schema(description = "List of top events ranked by ticket sales")
    private List<EventStatDTO> topEventsBySales;

    @Schema(description = "List of top events ranked by user bookmarks")
    private List<EventStatDTO> topEventsByBookmarks;
}
