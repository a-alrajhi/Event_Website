package com.event_website.Dto;

import com.event_website.Entity.Slot;
import com.event_website.Request.CompositeCreateCapacity;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO representing a Slot along with its associated ticket types and capacities")
public class SlotWithTicketTypesDTO {

    @Schema(description = "Unique identifier of the slot", example = "1")
    private Integer id;

    @Schema(description = "ID of the event this slot belongs to", example = "10")
    private Integer eventId;

    @Schema(description = "Date of the slot", example = "2025-09-22")
    private LocalDate date;

    @Schema(description = "Start time of the slot", example = "18:00")
    private LocalTime startTime;

    @Schema(description = "End time of the slot", example = "20:00")
    private LocalTime endTime;

    @Schema(description = "List of ticket types and their capacities for this slot")
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
