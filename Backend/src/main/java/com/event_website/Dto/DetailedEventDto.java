package com.event_website.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailedEventDto {
    EventDto event;
    List<SlotDTO> slots;
    String category;
    String Location;
    BigDecimal price;
}
