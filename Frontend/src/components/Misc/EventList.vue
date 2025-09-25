<template>
  <ul class="event-list">
    <li v-for="event in eventsList" :key="event.id" class="event-item">
      <img v-if="event.photoUrl" :src="event.photoUrl" :alt="event.name" class="event-img" />
      <div class="event-info">
        <strong class="event-name">{{ event.name }}</strong>
        <p class="event-description">{{ event.description }}</p>
        <button @click="() => browseEvent(event.id)" class="browse-btn">Browse</button>
      </div>
    </li>
  </ul>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const props = defineProps({
  events: {
    type: Array,
    required: true
  }
});

const eventsList = ref(props.events || []);

watch(() => props.events, (newEvents) => {
  eventsList.value = newEvents || [];
});

const browseEvent = (eventId) => {
  console.log("Navigating to event", eventId);
  router.push({ name: 'EventDetails', params: { id: eventId } });
};
</script>

<style scoped>
.event-list {
  list-style-type: none;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.event-item {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease;
}

.event-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.15);
}

.event-img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 15px;
  border-radius: 10px;
}

.event-info {
  flex: 1;
}

.event-name {
  font-size: 18px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.event-description {
  font-size: 14px;
  color: #555;
  margin-bottom: 10px;
}

.browse-btn {
  padding: 10px 18px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s ease;
}

.browse-btn:hover {
  background-color: #0056b3;
}
</style>

