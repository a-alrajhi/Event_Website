package com.event_website.Controller;

import com.event_website.Request.BatchTicketRequest;
import com.event_website.Service.BatchTicketService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate-tickets")
public class BatchTicketController {
  @Autowired private BatchTicketService batchTicketService;

  @PostMapping()
  public ResponseEntity<?> generateTicketsFromList(@RequestBody List<BatchTicketRequest> btr) {
    return ResponseEntity.ok(batchTicketService.generateTickets(btr));
  }
}
