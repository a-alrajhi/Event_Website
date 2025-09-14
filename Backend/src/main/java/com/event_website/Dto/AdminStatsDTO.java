package com.event_website.Dto;

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
public class AdminStatsDTO {
    private long totalUsers;
    private long totalEvents;
    private long totalTicketsSold;
    private BigDecimal totalRevenue;
    private double overallCheckInRate;

    private List<EventStatDTO> topEventsBySales;
    private List<EventStatDTO> topEventsByBookmarks;
}