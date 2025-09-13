<template>
  <h1>Running from Event Details Page!</h1>
  <div class="event-details-page px-4 py-6 max-w-7xl mx-auto">
    <div v-if="eventDetailsFetched">
      <EventBreadcrumb :pagePath="eventDetailsFetched" />
      <EventMedia :photo="eventDetailsFetched.photoUrl" />

      <!-- Main content and sidebar -->
      <div class="flex flex-col lg:flex-row gap-6 mt-8">
        <div class="flex-1">
          <EventMainContent
            :title="eventDetailsFetched.name"
            :subtitle="eventDetailsFetched.description"
          />
          <EventRules :rules="eventDetailsFetched.rules" />
        </div>

        <div class="w-full lg:w-1/3 px-3 py-12">
          <EventSidebar
            :startingPrice="eventDetailsFetched.startingPrice"
            :date="eventDetailsFetched.date"
            :eventStartingTime="eventDetailsFetched.eventStartingTime"
          />
        </div>
      </div>

      <!-- Location Map -->
      <div class="mt-10">
        <EventLocationMap :location="eventDetailsFetched.location" />
      </div>

      <!-- Similar Events -->
      <div class="mt-10">
        <SimilarEvents :categoryId="eventDetailsFetched.categoryId" />
      </div>
    </div>
    <div v-else>
      <p class="text-gray-500">Loading event details...</p>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import { getEventDetailsFromId, getFullEventDetails } from "../apis/eventApi";
import EventBreadcrumb from "../components/Event/EventBreadcrumb.vue";
import EventMedia from "../components/Event/EventMedia.vue";
import EventMainContent from "../components/Event/EventMainContent.vue";
import EventSidebar from "../components/Event/EventSidebar.vue";
import EventLocationMap from "../components/Event/EventLocationMap.vue";
import SimilarEvents from "../components/Event/SimilarEvents.vue";
import EventRules from "../components/Event/EventRules.vue";

const route = useRoute();
const eventId = route.params.id;

const eventDetailsFetched = ref(null);
const location = ref(null);
const ticketTypes = ref(null);
const slots = ref([]);

// getting response from the backend
onMounted(async () => {
  try {
    const data = await getFullEventDetails(eventId);
    eventDetailsFetched.value = data;
    location.value = data.location;
    ticketTypes.value = data.ticketTypes;
    slots.value = data.slots;

    console.log("Fetched Event:", data);
  } catch (err) {
    console.error("Failed to load event:", err);
  }
});
</script>

<style scoped>
h1 {
  color: #42b983;
}
</style>

<!-- 
<template>
  <div v-if="event">
    <EventBreadcrumb :event="event" />
    <EventMedia :media="event.mediaUrls" />
    <div class="grid">
      <EventMainContent :event="event" />
      <EventSidebar :event="event" />
    </div>
    <EventLocationMap :location="event.location" />
    <SimilarEvents :category="event.categoryId" />
  </div>
  <div v-else>
    Loading...
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getEventDetailsFromId } from '@/apis/eventApi';

const route = useRoute();
const eventId = route.params.id;

const event = ref(null);

onMounted(async () => {
  event.value = await getEventDetailsFromId(eventId);
});
</script> -->
