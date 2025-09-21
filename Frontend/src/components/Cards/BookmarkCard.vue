<template>
  <div class="bookmark-card">
    <figure>
      <img v-if="bookmark.image" :src="bookmark.image" :alt="bookmark.title" />
      <img v-else src="https://via.placeholder.com/350x200?text=Here+is+the+picture" alt="Placeholder image" />
    </figure>
    <div class="card-body">
      <h2>{{ bookmark.title }}</h2>
      <p>{{ bookmark.description }}</p>
      <div class="card-actions">
        <button @click="removeBookmark" class="btn btn-danger">Remove</button>
        <button @click="bookNow" class="btn btn-primary">Book Now</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  bookmark: Object,
});

const emit = defineEmits(["remove-bookmark", "book-now"]);

const removeBookmark = () => {
  emit("remove-bookmark", props.bookmark.id);
};

const bookNow = () => {
  emit("book-now", props.bookmark.id);
};
</script>

<style scoped>
.bookmark-card {
  width: 100%;
  max-width: 350px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.card-body {
  padding: 15px;
}

.card-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.btn {
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
}

.btn-danger {
  background-color: #e11d48;
  color: white;
}

.btn-primary {
  background-color: #4f46e5;
  color: white;
}
</style>
