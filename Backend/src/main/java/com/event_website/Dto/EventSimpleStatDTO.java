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
public class EventSimpleStatDTO {
    private Integer eventId;
    private String eventName;
    private long ticketsSold;
    private BigDecimal revenue;
}
