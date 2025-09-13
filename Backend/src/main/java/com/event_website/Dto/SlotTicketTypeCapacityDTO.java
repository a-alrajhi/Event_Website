package com.event_website.Dto;

import com.event_website.Entity.SlotTicketTypeCapacity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlotTicketTypeCapacityDTO {
    private Integer id;
    private Integer slotId;
    private Integer ticketTypeId;
    private Integer capacity;

    public static SlotTicketTypeCapacityDTO fromEntity(SlotTicketTypeCapacity entity) {
        SlotTicketTypeCapacityDTO dto = new SlotTicketTypeCapacityDTO();
        dto.setId(entity.getId());
        dto.setSlotId(entity.getSlot().getId());
        dto.setTicketTypeId(entity.getTicketType().getId());
        dto.setCapacity(entity.getCapacity());
        return dto;
    }
}

