/**
 * Slots and Ticket Types API Module
 * @author: Abdulrahman Al Rajhi
 * @since: 9/15/2025
 * @version: 1.0
 */
import apis from "./axiosClient";
import { useAuthStore } from "../stores/authStore";
import { usePaymentStore } from "../stores/paymentStore";

/**
 * Getting event details from event id
 * @param {*} eventId event id
 */
export async function getEventSlots(eventId) {
  const response = await apis.get(`/slots/event/${eventId}`);
  return response.data;
}

/**
 * Getting event details from event id
 * @param {*} eventId event id
 */
export async function getEventTicketTypes(eventId) {
  const response = await apis.get(`/ticket-type/event/${eventId}`);
  return response.data;
}

// @PostMapping("/create-ticket")

/**
 * Create tickets after payment
 * @returns List<TicketDTO>
 */
export async function generateTickets() {
  try {
    const authStore = useAuthStore();
    const paymentStore = usePaymentStore();

    const user = await authStore.getUser();
    if (!user) {
      throw new Error("User is not logged in. Cannot generate tickets.");
    }

    const body = paymentStore.tickets.map((t) => ({
      slotId: paymentStore.slotId,
      ticketTypeId: t.id,
      userId: user.id,
      quantity: t.quantity,
    }));

    console.log("Sending generateTickets payload:", body);

    const response = await apis.post(`/generate-tickets`, body);
    return response.data; // List<TicketDTO>
  } catch (error) {
    console.error("Error creating ticket:", error);
    throw error;
  }
}

/**
 * Get grouped tickets for a user
 * @param {number} userId
 * @returns List<TicketWithSameTypeDTO>
 */
export async function getUserGroupedTickets(userId) {
  const response = await apis.get(`/ticket/user/${userId}/grouped-tickets`);
  return response.data;
}

/**
 * Get capacity + remaining tickets for a given slot and ticket type
 * @param {number} slotId
 * @param {number} ticketTypeId
 * @returns {Promise<Object>} SlotTicketTypeCapacityDTO
 */
export async function getSlotTicketCapacity(slotId, ticketTypeId) {
  const response = await apis.get(`/capacities/slot/capacity`, {
    params: { slotId, ticketTypeId },
  });
  return response.data; // { id, slotId, ticketTypeId, capacity, remainingTickets }
}
