<template>
  <div
      class="bookmark-card bg-white rounded-xl shadow-md hover:shadow-xl transition-all duration-300 overflow-hidden"
  >
    <!-- Image -->
    <div class="relative overflow-hidden">
      <img
          v-if="bookmark.image"
          :src="bookmark.image"
          :alt="bookmark.title"
          class="w-full h-48 object-cover"
      />
      <img
          v-else
          src="https://via.placeholder.com/400x200.png?text=Event+Image"
          alt="Placeholder image"
          class="w-full h-48 object-cover"
      />
    </div>

    <!-- Card Content -->
    <div class="card-body p-4">
      <h2
          class="card-title text-lg font-semibold text-gray-800 line-clamp-1 hover:text-[var(--color-primary)] transition-colors"
      >
        {{ bookmark.title }}
      </h2>
      <p class="card-description text-sm text-gray-600 line-clamp-2">
        {{ bookmark.description }}
      </p>
    </div>

    <!-- Buttons -->
    <div class="card-actions flex gap-2 p-3 pt-0">
      <button
          @click="bookNow"
          class="btn flex-1 bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-white"
      >
        Book
      </button>
      <button
          @click="removeBookmark"
          class="btn flex-1 bg-[var(--color-error)] hover:bg-red-700 text-white"
      >
        Remove
      </button>
      <RouterLink
          :to="`/event/${bookmark.id}`"
          class="btn flex-1 bg-gray-100 hover:bg-gray-200 text-gray-800 text-center"
      >
        Details
      </RouterLink>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import { RouterLink } from "vue-router";

const props = defineProps({
  bookmark: { type: Object, required: true },
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
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}
.bookmark-card:hover {
  transform: translateY(-3px);
}

.btn {
  padding: 0.45rem 0.6rem;
  border-radius: 0.4rem;
  font-weight: 600;
  font-size: 0.8rem;
  text-align: center;
  transition: all 0.3s ease;
}
</style>
