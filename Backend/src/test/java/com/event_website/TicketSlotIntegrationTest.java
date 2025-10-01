//package com.event_website;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.event_website.Request.CreateTicketRequest;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
///**
// * Integration test flow for tickets + slots + capacities.
// *
// * Step 1 – Create a capacity
// *   - Call: POST /capacities
// *   - Input: slotId, ticketTypeId, capacity
// *   - Output: SlotTicketTypeCapacityDTO (with generated id, slot, ticketType, and capacity)
// *
// * Step 2 – Create a ticket for that capacity
// *   - Call: POST /ticket/create-ticket
// *   - Input: slotTicketTypeCapacityId, ticketCode (custom or generated), optional seatId
// *   - Output: TicketDTO (id, ticketCode, checkedIn=false, slotTicketTypeCapacityDTO, seat=null)
// *
// * Step 3 – Check in the ticket
// *   - Call: POST /ticket/check-in/{ticketCode}
// *   - Input: ticketCode from Step 2
// *   - Output: TicketDTO (with checkedIn=true)
// */
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//class TicketSlotIntegrationTest {
//
//  @Autowired private MockMvc mockMvc;
//
//  @Autowired private ObjectMapper objectMapper;
//
//  @Test
//  void testTicketFlowWithSlotAndCapacity() throws Exception {
//    // 1. Create capacity
//    MvcResult capacityResult =
//        mockMvc
//            .perform(
//                post("/capacities")
//                    .param("slotId", "1")
//                    .param("ticketTypeId", "2")
//                    .param("capacity", "5"))
//            .andExpect(status().isOk())
//            .andDo(print())
//            .andReturn();
//
//    String capacityJson = capacityResult.getResponse().getContentAsString();
//    JsonNode capacityNode = objectMapper.readTree(capacityJson);
//    int capacityId = capacityNode.get("id").asInt();
//
//    // 2. Create a ticket for that capacity
//    CreateTicketRequest ticketReq = new CreateTicketRequest();
//    ticketReq.setSlotTicketTypeCapacityId(capacityId);
//    ticketReq.setTicketCode("TKT-123456");
//
//    MvcResult ticketResult =
//        mockMvc
//            .perform(
//                post("/ticket/create-ticket")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(ticketReq)))
//            .andExpect(status().isOk())
//            .andDo(print())
//            .andReturn();
//
//    String ticketJson = ticketResult.getResponse().getContentAsString();
//    JsonNode ticketNode = objectMapper.readTree(ticketJson);
//    String ticketCode = ticketNode.get("ticketCode").asText();
//
//    // 3. Check-in the ticket
//    mockMvc
//        .perform(post("/ticket/check-in/" + ticketCode))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.checkedIn").value(true))
//        .andDo(print());
//  }
//}
