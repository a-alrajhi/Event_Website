import apis from "./axiosClient";

// THIS METHOD SHOULD BE IN THE SERVICE FOLDER
export async function getFullEventDetails(eventId) {
  // fetching all data in parallel
  const [eventDetails, ticketTypes, slots, location] = await Promise.all([
    getEventDetailsFromId(eventId),
    getEventTicketTypesFromId(eventId),
    getEventSlotsFromId(eventId),
    getEventLocationFromId(eventId),
  ]);

  return {
    ...eventDetails,
    ticketInfo: ticketTypes,
    slots,
    location,
    startingPrice: ticketTypes.length
      ? Math.min(...ticketTypes.map((ticket) => ticket.price))
      : null, // could be null if no ticket types
  };
}

/**
 * Getting event details from event id
 * @param {*} eventId event id
 */
export async function getEventDetailsFromId(eventId) {
  const response = await apis.get(`/Event/${eventId}`);
  return response.data;
}

/**
 * Getting event tickets from event id
 * @param {*} eventId event id
 */
export async function getEventTicketTypesFromId(eventId) {
  const resp = await apis.get(`/ticket-type/event/${eventId}`);
  return resp.data;
}

/**
 * Getting event slots from event id
 * @param {*} eventId event id
 */
export async function getEventSlotsFromId(eventId) {
  const resp = await apis.get(`/slots/event/${eventId}`);
  return resp.data;
}

/**
 * Getting event location from event id
 * @param {*} eventId event id
 */
export async function getEventLocationFromId(eventId) {
  const resp = await apis.get(`/locations/${eventId}`);
  return resp.data;
}
