package com.event_website.Dto;

import com.event_website.Entity.SlotTicketTypeCapacity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO representing capacity information for a specific ticket type in a slot")
public class SlotTicketTypeCapacityDTO {

    @Schema(description = "Unique identifier of the capacity record", example = "101")
    private Integer id;

    @Schema(description = "ID of the slot", example = "12")
    private Integer slotId;

    @Schema(description = "ID of the ticket type", example = "3")
    private Integer ticketTypeId;

    @Schema(description = "Capacity allocated for the ticket type in the slot", example = "150")
    private Integer capacity;
    
    @Schema(description = "Remaining tickets that can be bought for this slot ticket capacity", example = "30")
    private Integer remainingTickets;

  public static SlotTicketTypeCapacityDTO fromEntity(SlotTicketTypeCapacity entity) {
    SlotTicketTypeCapacityDTO dto = new SlotTicketTypeCapacityDTO();
    dto.setId(entity.getId());
    dto.setSlotId(entity.getSlot().getId());
    dto.setTicketTypeId(entity.getTicketType().getId());
    dto.setCapacity(entity.getCapacity());
    return dto;
  }
}
