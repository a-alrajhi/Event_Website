package com.event_website.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompositeEventDTO {
    EventDto event;
    List<SlotDTO> slots;
    List<SlotTicketTypeCapacityDTO> capacities;
}
