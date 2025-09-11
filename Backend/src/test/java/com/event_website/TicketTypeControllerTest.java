package com.event_website;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.event_website.Config.JwtFilter;
import com.event_website.Controller.TicketTypeController;
import com.event_website.Entity.TicketType;
import com.event_website.Request.CreateTicketTypeRequest;
import com.event_website.Request.UpdateTicketTypeRequest;
import com.event_website.Service.TicketTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
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
    controllers = TicketTypeController.class,
    excludeFilters = {
      @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)
    })
@AutoConfigureMockMvc(addFilters = false)
class TicketTypeControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private TicketTypeService ticketTypeService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void testGetAll() throws Exception {
    TicketType t1 = new TicketType();
    t1.setId(1);
    t1.setName("General Admission");
    t1.setPrice(BigDecimal.valueOf(50.00));
    t1.setHasAssignedSeating(false);

    TicketType t2 = new TicketType();
    t2.setId(6);
    t2.setName("VVIP");
    t2.setPrice(BigDecimal.valueOf(200.00));
    t2.setHasAssignedSeating(false);

    Mockito.when(ticketTypeService.getAllTicketTypes()).thenReturn(List.of(t1, t2));

    mockMvc
        .perform(get("/ticket-type/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].name").value("General Admission"))
        .andExpect(jsonPath("$[1].price").value(200.00));
  }

  @Test
  void testFindById() throws Exception {
    TicketType t = new TicketType();
    t.setId(3);
    t.setName("Standard");
    t.setPrice(BigDecimal.valueOf(75.00));
    t.setHasAssignedSeating(true);

    Mockito.when(ticketTypeService.getTicketTypeById(3)).thenReturn(t);

    mockMvc
        .perform(get("/ticket-type/3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(3))
        .andExpect(jsonPath("$.name").value("Standard"))
        .andExpect(jsonPath("$.hasAssignedSeating").value(true));
  }

  @Test
  void testFindByTypeFound() throws Exception {
    TicketType t = new TicketType();
    t.setId(4);
    t.setName("Premium");
    t.setPrice(BigDecimal.valueOf(150.00));
    t.setHasAssignedSeating(true);

    Mockito.when(ticketTypeService.findTicketTypeByName("Premium")).thenReturn(Optional.of(t));

    mockMvc
        .perform(get("/ticket-type/search").param("type", "Premium"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(4))
        .andExpect(jsonPath("$.name").value("Premium"));
  }

  @Test
  void testFindByTypeNotFound() throws Exception {
    Mockito.when(ticketTypeService.findTicketTypeByName("NotExist")).thenReturn(Optional.empty());

    mockMvc
        .perform(get("/ticket-type/search").param("type", "NotExist"))
        .andExpect(status().isNotFound());
  }

  @Test
  void testCreateTicketType() throws Exception {
    CreateTicketTypeRequest req = new CreateTicketTypeRequest();
    req.setName("Regular");
    req.setPrice(BigDecimal.valueOf(30.00));
    req.setHasAssignedSeating(false);

    TicketType t = new TicketType();
    t.setId(5);
    t.setName("Regular");
    t.setPrice(BigDecimal.valueOf(30.00));
    t.setHasAssignedSeating(false);

    Mockito.when(ticketTypeService.createTicketType(any(CreateTicketTypeRequest.class)))
        .thenReturn(t);

    mockMvc
        .perform(
            post("/ticket-type/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(5))
        .andExpect(jsonPath("$.name").value("Regular"))
        .andExpect(jsonPath("$.price").value(30.00));
  }

  @Test
  void testUpdateTicketType() throws Exception {
    UpdateTicketTypeRequest req = new UpdateTicketTypeRequest();
    req.setName("VIP");
    req.setPrice(BigDecimal.valueOf(120.00));
    req.setHasAssignedSeating(true);

    TicketType t = new TicketType();
    t.setId(2);
    t.setName("VIP");
    t.setPrice(BigDecimal.valueOf(120.00));
  }
}
