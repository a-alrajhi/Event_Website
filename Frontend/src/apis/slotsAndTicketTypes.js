/**
Event API 
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.0
 */
import apis from "./axiosClient";

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
