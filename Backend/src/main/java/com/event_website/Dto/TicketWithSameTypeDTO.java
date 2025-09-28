package com.event_website.Dto;

import com.event_website.Entity.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * TESTING URL http://localhost:6060/ticket/user/6/grouped-tickets
 *
 * <pre>
 *   [
 * 	{
 * 		"groupedTicketCode": "GRP-6158E24EEC",
 * 		"ticketType": "Regular",
 * 		"count": 128,
 * 		"ticketCodeList": [
 * 			"TKT-2751B53F",
 * 			"TKT-C0927349",
 * 			"TKT-5EFE0898",
 * 			"TKT-E2BA6153",
 * 				],
 * 		"slotTicketTypeCapacityDTO": {
 * 			"id": 3,
 * 			"slotId": 1,
 * 			"ticketTypeId": 5,
 * 			"capacity": 200,
 * 			"remainingTickets": null
 * 		},
 * 		"userId": 6,
 * 		"userName": "mo",
 * 		"ticketCapacity": 200
 * 	},
 *
 * </pre>
 */

/**
 * DTO class for representing grouped tickets of the same type.
 *
 * <p>Groups multiple tickets of the same type belonging to a user into a single summary object.
 * Contains ticket count, individual ticket codes, user information, and slot capacity details.
 *
 * <p>Used for displaying ticket confirmations and managing grouped ticket operations. Generates a
 * unique grouped ticket code by hashing the individual ticket codes.
 *
 * @author Abdulrahman Al Rajhi
 * @since 10-09-2025
 * @version 1.0
 */
@Getter
@Setter
public class TicketWithSameTypeDTO {
  private String groupedTicketCode;
  private String ticketType;

  private int count; // how many tickets of this type

  private List<String> ticketCodeList = new ArrayList<>();

  private SlotTicketTypeCapacityDTO slotTicketTypeCapacityDTO;
  private Integer userId;
  private String userName;
  private Integer ticketCapacity;

  /**
   * give this method a list of tickets of the same type, and itt builds one summary DTO
   *
   * @param tickets ticket list of a type
   * @return this should return this object
   *     <pre>
   *   {
   *   "ticketType": "VIP",
   *   "count": 3,
   *   "ticketCodes": ["TKT-AAA", "TKT-BBB", "TKT-CCC"],
   *   "userId": 1,
   *   "userName": "John Doe",
   *   "slotTicketTypeCapacityDTO": { ... }
   * }
   * </pre>
   */
  public static TicketWithSameTypeDTO fromEntity(List<Ticket> tickets) {
    if (tickets == null || tickets.isEmpty()) return null;

    Ticket firstTicket = tickets.get(0);
    TicketWithSameTypeDTO dto = new TicketWithSameTypeDTO();

    if (firstTicket.getSlotTicketTypeCapacity() != null
        && firstTicket.getSlotTicketTypeCapacity().getTicketType() != null) {
      dto.setTicketType(firstTicket.getSlotTicketTypeCapacity().getTicketType().getName());
    }

    dto.setCount(tickets.size()); // get total tickets in the list

    // adding all ticket codes
    for (Ticket ticket : tickets)
      dto.getTicketCodeList().add(ticket.getTicketCode()); // ["TKT1", "TKT2", "TKT3"]

    // copying  slot capacity info
    if (firstTicket.getSlotTicketTypeCapacity() != null) {
      SlotTicketTypeCapacityDTO slotTicketTypeCapacityDTO =
          SlotTicketTypeCapacityDTO.fromEntity(firstTicket.getSlotTicketTypeCapacity());
      dto.setSlotTicketTypeCapacityDTO(slotTicketTypeCapacityDTO);
      dto.setTicketCapacity(slotTicketTypeCapacityDTO.getCapacity());
    }

    // copying user info
    if (firstTicket.getUser() != null) {
      dto.setUserId(firstTicket.getUser().getId());
      dto.setUserName(firstTicket.getUser().getName());
    }

    // generating GroupedTicketCode
    String concatenatedCodes = String.join("-", dto.getTicketCodeList());
    String hash = DigestUtils.sha256Hex(concatenatedCodes).substring(0, 10).toUpperCase();
    dto.setGroupedTicketCode("GRP-" + hash); // "GRP-8F3D2C9A12" example output
    return dto;
  }
}
