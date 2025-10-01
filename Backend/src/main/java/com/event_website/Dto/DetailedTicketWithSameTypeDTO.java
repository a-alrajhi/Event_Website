package com.event_website.Dto;

import com.event_website.Entity.Event;
import com.event_website.Entity.Slot;
import com.event_website.Entity.Ticket;
import com.event_website.Entity.TicketType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class DetailedTicketWithSameTypeDTO extends TicketWithSameTypeDTO {
    private BigDecimal price;
    private String eventName;
    private String photoUrl;
    private LocalDate date;
    private LocalTime startTime;


    public static DetailedTicketWithSameTypeDTO fromEntity(List<Ticket> tickets, Event event, Slot slot, TicketType ticketType) {
        if (tickets == null || tickets.isEmpty()) return null;

        Ticket firstTicket = tickets.get(0);
        DetailedTicketWithSameTypeDTO dto = new DetailedTicketWithSameTypeDTO();
        dto.setPrice(ticketType.getPrice());
        dto.setEventName(event.getName());
        dto.setDate(slot.getDate());
        dto.setPhotoUrl(event.getPhotoUrl());
        dto.setTicketType(ticketType.getName());
        dto.setStartTime(slot.getStartTime());
        dto.setCount(tickets.size()); // get total tickets in the list

        // adding all ticket codes
        for (Ticket ticket : tickets)
            dto.getTicketCodeList().add(ticket.getTicketCode()); // ["TKT1", "TKT2", "TKT3"]

        // generating GroupedTicketCode
        String concatenatedCodes = String.join("-", dto.getTicketCodeList());
        String hash = DigestUtils.sha256Hex(concatenatedCodes).substring(0, 10).toUpperCase();
        dto.setGroupedTicketCode("GRP-" + hash); // "GRP-8F3D2C9A12" example output
        return dto;
    }
}
