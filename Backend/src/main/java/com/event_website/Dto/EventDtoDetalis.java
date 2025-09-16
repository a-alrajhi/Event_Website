package com.event_website.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDtoDetalis {
    private Integer eventId;
    private String eventName;
    private String eventDescription;
    private String photoUrl;


    private String categoryName;

    private List<BigDecimal> ticketPrices;

}
