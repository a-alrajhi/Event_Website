package com.event_website.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Monthly statistics data transfer object")
public class MonthlyStatDTO {

    @Schema(description = "Month in the format YYYY-MM", example = "2025-09")
    private String month;

    @Schema(description = "Total number of tickets sold in the month", example = "1500")
    private long ticketCount;

    @Schema(description = "Total revenue generated in the month (SAR)", example = "45000.75")
    private BigDecimal totalRevenue;
}
