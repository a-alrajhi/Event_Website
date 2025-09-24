package com.event_website.Request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchTicketRequest {
  private Integer userId;
  private Integer slotId;
  private Integer ticketTypeId;
  private Integer quantity;
}