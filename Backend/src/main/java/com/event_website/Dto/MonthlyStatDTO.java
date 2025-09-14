package com.event_website.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyStatDTO {
    private String month; // e.g., "2025-09"
    private long ticketCount;
    private BigDecimal totalRevenue;
}
