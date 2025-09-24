package com.event_website.Dto;

import com.event_website.Entity.Slot;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Data Transfer Object for a time slot of an event")
public class SlotDTO {
    @Schema(description = "Unique identifier of the slot", example = "123")
    private Integer id;

    @Schema(description = "Identifier of the event this slot belongs to", example = "45")
    private Integer eventId;

    @Schema(description = "Date of the slot in ISO format (yyyy-MM-dd)", example = "2025-09-22")
    private LocalDate date;

    @Schema(description = "Start time of the slot in 24-hour format (HH:mm:ss)", example = "14:00:00")
    private LocalTime startTime;

    @Schema(description = "End time of the slot in 24-hour format (HH:mm:ss)", example = "16:00:00")
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
