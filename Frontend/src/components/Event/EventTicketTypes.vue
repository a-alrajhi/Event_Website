<template>

    <!-- Main Container -->
    <div class="min-h-screen bg-gradient-to-br from-blue-50 via-white to-purple-50">
        <div class="container mx-auto px-4 py-8">

            <!-- Header Section -->
            <div class="text-center mb-8">
                <h1 class="text-3xl font-bold text-gray-900 mb-2">Select Your Tickets</h1>
                <p class="text-gray-600">Choose the number of tickets you'd like to purchase</p>
            </div>

            <!-- Breadcrumb Stepper -->
            <!-- Ready to use component -->
            <!-- Breadcrumb Stepper -->
            <div class="max-w-4xl mx-auto mb-12">
                <ol
                    class="flex items-center justify-between w-full p-6 text-sm font-medium text-gray-600 bg-white border border-gray-200 rounded-lg shadow-lg dark:text-gray-400 dark:bg-gray-800 dark:border-gray-700">

                    <!-- Step 1: Ticket (current step) -->
                    <li class="flex items-center text-blue-600 dark:text-blue-500 font-semibold">
                        <span
                            class="flex items-center justify-center w-8 h-8 me-3 text-sm bg-blue-600 text-white rounded-full shrink-0 shadow-md">
                            1
                        </span>
                        <!-- Step details  -->
                        <div class="text-left hidden sm:block">
                            <div class="text-blue-600 font-semibold">Ticket Selection</div>
                            <div class="text-xs text-gray-500">Choose your tickets</div>
                        </div>

                        <!-- Arrow separator -->
                        <span class="mx-3 text-gray-400">›</span>
                    </li>

                    <!-- Step 2: Payment -->
                    <li class="flex items-center text-gray-500">
                        <span
                            class="flex items-center justify-center w-8 h-8 me-3 text-sm border-2 border-gray-300 rounded-full shrink-0">
                            2
                        </span>
                        <!-- Step details -->
                        <div class="text-left hidden sm:block">
                            <div class="font-semibold">Payment</div>
                            <div class="text-xs">Secure checkout</div>
                        </div>

                        <!-- Arrow separator -->
                        <span class="mx-3 text-gray-400">›</span>
                    </li>

                    <!-- Step 3: Confirmation -->
                    <li class="flex items-center text-gray-500">
                        <span
                            class="flex items-center justify-center w-8 h-8 me-3 text-sm border-2 border-gray-300 rounded-full shrink-0">
                            3
                        </span>

                        <!-- Step details (hidden on mobile) -->
                        <div class="text-left hidden sm:block">
                            <div class="font-semibold">Confirmation</div>
                            <div class="text-xs">Get your tickets</div>
                        </div>
                    </li>
                </ol>
            </div>





            <!-- Ticket Selection Section -->
            <section class="max-w-4xl mx-auto">

                <!-- Loading State -->
                <div v-if="loading" class="flex justify-center items-center py-16">
                    <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
                </div>

                <!-- Error State -->
                <div v-else-if="error" class="bg-red-50 border border-red-200 rounded-xl p-6 text-center">
                    <div class="text-red-600 font-semibold mb-2">Oops! Something went wrong</div>
                    <p class="text-red-500">{{ error }}</p>
                </div>

                <!-- Ticket List -->
                <!-- If all states passed -->
                <div v-else class="space-y-6">
                    <div v-for="ticketInfo in ticketTypes" :key="ticketInfo.id"
                        class="bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1">

                        <!-- Current Ticket Info Container -->
                        <div class="p-6">
                            <!-- Responsive layout: stacked on mobile, side-by-side on large screens -->
                            <div
                                class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">

                                <!-- ==== LEFT SIDE: TICKET INFORMATION ==== -->
                                <div class="flex-1">
                                    <div class="flex items-start space-x-4">
                                        <!-- Ticket Icon -->
                                        <div class="flex-shrink-0">
                                            <div
                                                class="w-12 h-12 bg-gradient-to-r from-neutral-900 to-sky-900 rounded-xl flex items-center justify-center">
                                                <!-- SVG ticket icon -->
                                                <svg class="w-6 h-6 text-white" fill="none" stroke="currentColor"
                                                    viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="2"
                                                        d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a1 1 0 001 1h1a1 1 0 011-1V9a1 1 0 00-1-1H4V7a2 2 0 012-2h10a2 2 0 012 2v1h-1a1 1 0 00-1 1v1a1 1 0 001 1h1v3a2 2 0 01-2 2H5a2 2 0 01-2-2v-1h1a1 1 0 001-1v-1a1 1 0 00-1-1H3V7z" />
                                                </svg>
                                            </div>
                                        </div>


                                        <div class="flex-1 min-w-0">
                                            <h3 class="text-lg font-bold text-gray-900 mb-1">{{ ticketInfo.name }}</h3>

                                            <!-- Ticket Description -->
                                            <p class="text-sm text-gray-600 mb-2">{{ ticketInfo.name + ' event ticket'
                                            }}</p>

                                            <!-- Ticket Price -->
                                            <div class="flex items-center space-x-4">
                                                <span class="text-2xl font-bold text-blue-600">{{
                                                    formatCurrency(ticketInfo.price) }}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Quantity Selector -->
                                <div class="lg:flex-shrink-0">
                                    <!-- Gray Container -->
                                    <div class="bg-gray-50 rounded-xl p-4">
                                        <label class="block text-sm font-medium text-gray-700 mb-3 text-center">
                                            Quantity
                                        </label>

                                        <!-- Quantity Controls Container -->
                                        <div class="flex items-center space-x-3">

                                            <!-- Decrease Button -->
                                            <button @click="decrease(ticketInfo)"
                                                :disabled="(ticketQuantities[ticketInfo.id] || 0) <= 0"
                                                class="w-10 h-10 rounded-full bg-white border-2 border-gray-200 flex items-center justify-center hover:border-blue-500 hover:bg-blue-50 transition-colors duration-200 disabled:opacity-50 disabled:cursor-not-allowed">
                                                <span class="text-gray-900">-</span>
                                            </button>

                                            <!-- Quantity Display -->
                                            <div class="w-16 text-center">
                                                <span class="text-xl font-bold text-gray-900">{{
                                                    ticketQuantities[ticketInfo.id] || 0 }}</span>
                                            </div>
                                            <!-- Increase Button -->
                                            <button @click="increase(ticketInfo)"
                                                :disabled="(ticketQuantities[ticketInfo.id] || 0) >= 50"
                                                class="w-10 h-10 rounded-full bg-white border-2 border-gray-200 flex items-center justify-center hover:border-blue-500 hover:bg-blue-50 transition-colors duration-200">
                                                <span class="text-gray-900">+</span></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>




                <!-- Order Summary -->
                <!-- only show when tickets > 1 -->
                <div v-if="totalTickets > 0"
                    class="mt-8 bg-gradient-to-r from-blue-50 to-purple-50 rounded-2xl p-6 border border-blue-200">

                    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between space-y-4 sm:space-y-0">

                        <!-- Left side summary info -->
                        <div>
                            <h3 class="text-lg font-semibold text-gray-900">Order Summary</h3>

                            <p class="text-sm text-gray-600">{{ totalTickets }} ticket{{ totalTickets > 1 ? 's' : '' }}
                                selected</p>
                        </div>

                        <!-- Right side total price -->
                        <div class="text-right">
                            <div class="text-2xl font-bold text-gray-900">{{ formatCurrency(totalAmount) }}</div>
                            <div class="text-sm text-gray-600">Total Amount</div>
                        </div>
                    </div>
                </div>


                <!-- Page Buttons -->
                <div class="mt-8 flex flex-col sm:flex-row gap-4 justify-center">
                    <!-- Back To Event Button -->
                    <button type="button"
                        class="text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700">
                        Back To Event
                    </button>



                    <!-- Confirm Button -->
                    <button type="button" :disabled="totalTickets === 0"
                        class="px-8 py-3 bg-gradient-to-r from-neutral-900 to-sky-900 text-white font-semibold rounded-xl shadow-lg hover:shadow-xl hover:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none">

                        <div class="flex items-center justify-center space-x-2">
                            <span v-if="totalTickets === 0">Select Tickets</span>
                            <span v-else> Confirm Selection
                            </span>
                        </div>
                    </button>

                </div>
            </section>
        </div>
    </div>
</template>



<script setup>
import { computed, onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { getAllTicketTypesForEvent } from "../../apis/eventApi";


const route = useRoute();
console.log("params:", route.params);
console.log("query:", route.query);

const slotId = route.query?.slotId || null;
const eventId = route.params.eventId || null;

// list of all ticket types
const ticketTypes = ref([]);
const ticketQuantities = ref({});
// show loading spinner (state)
const loading = ref(true);
// show error message when fetching fails(state)
const error = ref(null);

onMounted(async () => {
    try {
        console.log("fetching ticket types for event:", eventId, "slot:", slotId);
        ticketTypes.value = await getAllTicketTypesForEvent(eventId);
        console.log("fetched ticket types:", ticketTypes.value);
    } catch (err) {
        error.value = "Failed to load ticket types.";
        console.error(err);
    } finally { loading.value = false }
});


function increase(ticket) {
    ticketQuantities.value[ticket.id] = (ticketQuantities.value[ticket.id] || 0) + 1;
}
function decrease(ticket) {
    if ((ticketQuantities.value[ticket.id] || 0) > 0) {
        ticketQuantities.value[ticket.id]--;
    }
}
function formatCurrency(amount) {
    return new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "SAR",
    }).format(amount);
}



const totalTickets = computed(() => {
    return Object.values(ticketQuantities.value).reduce((sum, qty) => sum + (qty || 0), 0);
})


const totalAmount = computed(() => {
    return ticketTypes.value.reduce(
        (sum, ticket) => {
            const qty = ticketQuantities.value[ticket.id] || 0;
            return sum + (ticket.price * qty);
        }, 0);
});
</script>
