package com.event_website;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.event_website.Config.JwtFilter;
import com.event_website.Controller.TicketController;
import com.event_website.Entity.Ticket;
import com.event_website.Request.CreateTicketRequest;
import com.event_website.Request.UpdateTicketRequest;
import com.event_website.Service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
    controllers = TicketController.class,
    excludeFilters = {
      @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)
    })
@AutoConfigureMockMvc(addFilters = false)
class TicketControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private TicketService ticketService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void testGetAllTickets() throws Exception {
    Ticket ticket = new Ticket();
    ticket.setId(5);
    ticket.setTicketCode("TKT-A3E9832F");
    ticket.setCheckedIn(false);

    Mockito.when(ticketService.getAllTickets()).thenReturn(List.of(ticket));

    mockMvc
        .perform(get("/ticket/all"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(5))
        .andExpect(jsonPath("$[0].ticketCode").value("TKT-A3E9832F"))
        .andExpect(jsonPath("$[0].checkedIn").value(false));
  }

  @Test
  void testGetTicketById() throws Exception {
    Ticket ticket = new Ticket();
    ticket.setId(11);
    ticket.setTicketCode("TKT-670FBD8B");
    ticket.setCheckedIn(true);

    Mockito.when(ticketService.getTicketById(11)).thenReturn(ticket);

    mockMvc
        .perform(get("/ticket/11"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(11))
        .andExpect(jsonPath("$.ticketCode").value("TKT-670FBD8B"))
        .andExpect(jsonPath("$.checkedIn").value(true));
  }

  @Test
  void testCreateTicket() throws Exception {
    CreateTicketRequest req = new CreateTicketRequest();
    // populate request if needed

    Ticket ticket = new Ticket();
    ticket.setId(12);
    ticket.setTicketCode("TKT-NEW123");
    ticket.setCheckedIn(false);

    Mockito.when(ticketService.createTicket(any(CreateTicketRequest.class))).thenReturn(ticket);

    mockMvc
        .perform(
            post("/ticket/create-ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(12))
        .andExpect(jsonPath("$.ticketCode").value("TKT-NEW123"))
        .andExpect(jsonPath("$.checkedIn").value(false));
  }

  @Test
  void testUpdateTicket() throws Exception {
    UpdateTicketRequest req = new UpdateTicketRequest();
    // populate request if needed

    Ticket ticket = new Ticket();
    ticket.setId(5);
    ticket.setTicketCode("TKT-UPDATED123");
    ticket.setCheckedIn(true);

    Mockito.when(ticketService.updateTicket(eq(5), any(UpdateTicketRequest.class)))
        .thenReturn(ticket);

    mockMvc
        .perform(
            put("/ticket/update-ticket/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(5))
        .andExpect(jsonPath("$.ticketCode").value("TKT-UPDATED123"))
        .andExpect(jsonPath("$.checkedIn").value(true));
  }

  @Test
  void testDeleteTicket() throws Exception {
    Mockito.doNothing().when(ticketService).deleteTicket(5);

    mockMvc.perform(delete("/ticket/5")).andExpect(status().isNoContent());
  }

  @Test
  void testCheckInTicket() throws Exception {
    Ticket ticket = new Ticket();
    ticket.setId(7);
    ticket.setTicketCode("TKT-58E9C6C8");
    ticket.setCheckedIn(true);

    Mockito.when(ticketService.checkInTicketByCode("TKT-58E9C6C8")).thenReturn(ticket);

    mockMvc
        .perform(post("/ticket/check-in/TKT-58E9C6C8"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(7))
        .andExpect(jsonPath("$.ticketCode").value("TKT-58E9C6C8"))
        .andExpect(jsonPath("$.checkedIn").value(true));
  }
}
