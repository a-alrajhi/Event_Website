package com.event_website.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO response after creating a composite event with slots and ticket capacities")
public class CreateCompositeEventDTO {

    @Schema(description = "Event details")
    private EventDto event;

    @Schema(description = "List of slots for the event")
    private List<SlotDTO> slots;

    @Schema(description = "List of capacities per slot and ticket type")
    private List<SlotTicketTypeCapacityDTO> capacities;
}
