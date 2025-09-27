<template>
  <div class="bookmark-card bg-[var(--color-card)] rounded-xl shadow-lg overflow-hidden hover:shadow-xl transition-all duration-300">
    <figure class="relative overflow-hidden h-48">
      <img
        :src="bookmark.image || 'https://images.ctfassets.net/vy53kjqs34an/1b6S3ia1nuDcqK7uDfvPGz/c2796f467985e3702c6b54862be767d5/1280%C3%A2__%C3%83_%C3%A2__426-_1.jpg'"
        :alt="bookmark.title"
        class="w-full h-full object-cover"
      />
      <!-- Price Badge -->
      <div class="absolute top-3 left-3 bg-[var(--color-primary)] px-3 py-1 rounded-full text-sm font-bold text-[var(--color-text)] shadow-lg">
        {{ bookmark.price === 0 ? 'FREE' : `SAR ${bookmark.price}` }}
      </div>
      <!-- Category Badge -->
      <div v-if="bookmark.category" class="absolute top-3 right-3 bg-[var(--color-secondary)]/90 px-2 py-1 rounded-full text-xs font-medium text-[var(--color-text)]">
        {{ bookmark.category }}
      </div>
      <!-- Status Badge -->
      <div v-if="bookmark.status && bookmark.status !== 'active'" class="absolute bottom-3 left-3 bg-yellow-500 px-2 py-1 rounded-full text-xs font-medium text-white">
        {{ bookmark.status.toUpperCase() }}
      </div>
    </figure>

    <div class="card-body p-4">
      <h2 class="text-lg font-semibold text-[var(--color-text)] mb-2 line-clamp-1">{{ bookmark.title }}</h2>

      <!-- Event Details -->
      <div class="space-y-2 mb-3 text-sm text-[var(--color-gray)]">
        <div class="flex items-center gap-2">
          <Calendar class="w-4 h-4 text-[var(--color-primary)]" />
          <span>{{ formatDate(bookmark.date) }}</span>
        </div>
        <div class="flex items-center gap-2">
          <MapPin class="w-4 h-4 text-[var(--color-primary)]" />
          <span class="truncate">{{ bookmark.venue }}</span>
        </div>
        <div v-if="bookmark.capacity" class="flex items-center gap-2">
          <svg class="w-4 h-4 text-[var(--color-primary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"></path>
          </svg>
          <span class="text-sm">{{ bookmark.capacity }} capacity</span>
        </div>
        <div v-if="bookmark.organizer" class="flex items-center gap-2">
          <svg class="w-4 h-4 text-[var(--color-primary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
          </svg>
          <span class="truncate text-sm">by {{ bookmark.organizer }}</span>
        </div>
      </div>

      <p class="text-[var(--color-gray)] text-sm mb-4 line-clamp-2">{{ bookmark.description }}</p>

      <div class="card-actions flex gap-2">
        <button @click="removeBookmark" class="btn btn-danger flex-1 py-2 px-4 rounded-lg font-medium transition-colors bg-[var(--color-error)] hover:bg-[var(--color-error)]/80 text-[var(--color-text)]">
          Remove
        </button>
        <button @click="bookNow" class="btn btn-primary flex-1 py-2 px-4 rounded-lg font-medium transition-colors bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-[var(--color-text)]">
          Book Now
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import { Calendar, MapPin } from 'lucide-vue-next';

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

const formatDate = (date) => {
  if (!date) return 'TBD';

  try {
    return new Date(date).toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric',
      weekday: 'short',
    });
  } catch {
    return 'TBD';
  }
};
</script>

<style scoped>
.bookmark-card {
  width: 100%;
  max-width: 350px;
}

.line-clamp-1 {
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
