package com.event_website.Dto;

import com.event_website.Entity.Event;
import com.event_website.Entity.Slot;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlotDTO {
    private Integer id;
    private Integer eventId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public static SlotDTO fromEntity(Slot slot) {
        SlotDTO slotDTO = new SlotDTO();
        slotDTO.id = slot.getId();
        slotDTO.eventId = slot.getEvent().getId();
        slotDTO.date = slot.getDate();
        slotDTO.startTime = slot.getStartTime();
        slotDTO.endTime = slot.getEndTime();
        return slotDTO;
    }
}
