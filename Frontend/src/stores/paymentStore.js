/**
 * Payment Store - Manages payment and checkout state
 * @author: Abdulrahman Al Rajhi
 * @since: 9/18/2025
 * @version: 1.0
 */
import { defineStore } from "pinia";

export const usePaymentStore = defineStore("payment", {
  state: () => ({
    // data related to payment
    event: null, // full event object
    eventId: null,
    slotId: null,
    tickets: [], // [{ id, name, price, quantity, subtotal }]
    totalTickets: 0,
    totalAmount: 0,
    location: null,
  }),

  actions: {
    /**
     * Set checkout data and calculate totals
     */
    setCheckoutData({ event, eventId, slotId, tickets, location }) {
      this.event = event;
      this.eventId = eventId;
      this.slotId = slotId;
      this.tickets = tickets;
      this.location = location;
      // recompute totals
      this.totalTickets = tickets.reduce((sum, t) => sum + t.quantity, 0);
      this.totalAmount = tickets.reduce((sum, t) => sum + t.subtotal, 0);
      this.selectedPaymentMethod = "credit-card";
    },

    /**
     * Clear all checkout data
     */
    clearCheckoutData() {
      this.$reset();
    },
  },
  persist: true, // save to localstorage
});
