<!-- Event Details Page 
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.0
-->
<template>
  <Navbar />
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
            :longDescription="eventDetailsFetched.longDescription"
          />
          <EventRules :rules="eventDetailsFetched.rules" />
        </div>

        <div class="lg:w-[320px] xl:w-[400px] shrink-0 px-3 py-12">
          <EventSidebar
            :startingPrice="eventDetailsFetched.startingPrice"
            :slots="eventDetailsFetched.slots"
            :ticketTypes="eventDetailsFetched.ticketInfo"
            :eventId="eventDetailsFetched.id"
          />
        </div>
      </div>

      <!-- Location Map -->
      <div class="mt-10">
        <EventLocationMap :location="eventDetailsFetched.location" />
      </div>

      <!-- Similar Events -->
      <div class="mt-10">
        <SimilarEvents
          v-if="eventDetailsFetched && eventDetailsFetched.categoryId"
          :categoryId="eventDetailsFetched.categoryId"
          :curEventId="eventDetailsFetched.id"
        />
      </div>
    </div>
    <div v-else>
      <p class="text-gray-500">Loading event details...</p>
    </div>
  </div>
  <AppFooter />
</template>

<script setup>
import { useRoute } from "vue-router";
import { ref, onMounted, watch } from "vue";
import { getFullEventDetails } from "../apis/eventApi";
import EventBreadcrumb from "../components/Event/EventBreadcrumb.vue";
import EventMedia from "../components/Event/EventMedia.vue";
import EventMainContent from "../components/Event/EventMainContent.vue";
import EventSidebar from "../components/Event/EventSidebar.vue";
import EventLocationMap from "../components/Event/EventLocationMap.vue";
import SimilarEvents from "../components/Event/SimilarEvents.vue";
import EventRules from "../components/Event/EventRules.vue";
import AppFooter from "../components/AppFooter/AppFooter.vue";
import Navbar from "../components/Navbar/Navbar.vue";

const route = useRoute();
const eventDetailsFetched = ref(null);

const loadEventDetails = async (eventId) => {
  try {
    const data = await getFullEventDetails(eventId);
    eventDetailsFetched.value = data;
    console.log("Fetched Event:", data);
  } catch (err) {
    console.error("Failed to load event:", err);
  }
};

onMounted(() => {
  loadEventDetails(route.params.id);
});
watch(
  () => route.params.id,
  (newId, oldId) => {
    if (newId !== oldId) {
      loadEventDetails(newId);
    }
  }
);
</script>
