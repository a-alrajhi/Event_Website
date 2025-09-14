package com.event_website.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventStatDTO {
    EventStatDTO(Integer id, String name, Long count) {
        this.eventId = id;
        this.name = name;
        this.bookmarkCount = count;
    }
    private Integer eventId;
    private String name;
    private Long ticketsSold;
    private BigDecimal revenue;
    private Long bookmarkCount;
    private double checkInRate;
}
