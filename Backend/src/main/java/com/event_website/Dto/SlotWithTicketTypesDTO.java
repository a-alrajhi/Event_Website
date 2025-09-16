package com.event_website.Dto;

import com.event_website.Entity.Slot;
import com.event_website.Request.CompositeCreateCapacity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlotWithTicketTypesDTO {
    private Integer id;
    private Integer eventId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<CompositeCreateCapacity> ticketTypes;

    public Slot toSlot() {
        Slot slot = new Slot();
        slot.setId(this.id);
        slot.setStartTime(this.startTime);
        slot.setEndTime(this.endTime);
        slot.setDate(this.date);
        return slot;
    }
}
