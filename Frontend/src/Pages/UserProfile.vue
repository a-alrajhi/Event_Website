<template>
  <div class="min-h-screen bg-gray-50 dark:bg-gray-900">
    <!-- Navbar -->
    <Navbar />

    <!-- Hero Section -->
    <div class="relative via-[var(--color-secondary)] to-[var(--color-hover)] py-16">
      <div class="absolute inset-0 bg-black/10"></div>
      <div class="relative container mx-auto px-6">
        <div class="text-center text-white">
          <div class="w-24 h-24 bg-white/20 rounded-full mx-auto mb-4 flex items-center justify-center border-2 border-white/30">
            <User class="w-12 h-12 text-white" />
          </div>
          <h1 class="text-4xl font-bold mb-2">{{ userData?.name || 'User Profile' }}</h1>
          
        </div>
      </div>
    </div>

    <!-- Main Content -->
    <div class="container mx-auto px-6 py-12">
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Update Profile -->
        <div class="lg:col-span-1">
          <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-lg border border-gray-200 dark:border-gray-700 p-6 h-fit">
            <div class="flex items-center gap-3 mb-6 pb-4 border-b border-gray-200 dark:border-gray-600">
              <div class="w-10 h-10 bg-[var(--color-primary)]/10 rounded-full flex items-center justify-center">
                <Settings class="w-5 h-5 text-[var(--color-primary)]" />
              </div>
              <h2 class="text-xl font-semibold text-gray-900 dark:text-white">Update Profile</h2>
            </div>

                <form class="auth-form" @submit.prevent="handleSubmit">
                  <UserProfileInput
                    id="name"
                    label="Full Name"
                    placeholder="Enter your name"
                    v-model="form.name"
                    required
                  />

                  <UserProfileInput
                    id="email"
                    label="Email Address"
                    type="email"
                    placeholder="Enter your email"
                    v-model="form.email"
                    required
                  />

                  <UserProfileInput
                    id="phoneNumber"
                    label="Phone Number"
                    type="tel"
                    placeholder="Enter your phone number"
                    v-model="form.phoneNumber"
                    required
                  />

                  <UserProfileInput
                    id="password"
                    label="New Password"
                    type="password"
                    placeholder="Enter your new password"
                    v-model="form.password"
                    minlength="6"
                  />

                  <UserProfileInput
                    id="confirmPassword"
                    label="Confirm New Password"
                    type="password"
                    placeholder="Re-enter your new password"
                    v-model="form.confirmPassword"
                    minlength="6"
                  />

            <p v-if="errorMessage" class="text-red-500 text-sm mb-4">{{ errorMessage }}</p>

            <button type="submit" class="w-full bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-white font-medium py-3 px-4 rounded-lg transition-colors disabled:opacity-50 disabled:cursor-not-allowed" :disabled="loading">
              <span v-if="loading" class="flex items-center justify-center gap-2">
                <div class="w-4 h-4 border-2 border-white/30 border-t-white rounded-full animate-spin"></div>
                Updating...
              </span>
              <span v-else>Update Profile</span>
            </button>
          </form>
          </div>
        </div>

        <!-- Bookmarks Section -->
        <div class="lg:col-span-2">
          <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-lg border border-gray-200 dark:border-gray-700 p-6">
            <div class="flex items-center gap-3 mb-6 pb-4 border-b border-gray-200 dark:border-gray-600">
              <div class="w-10 h-10 bg-[var(--color-error)]/10 rounded-full flex items-center justify-center">
                <Heart class="w-5 h-5 text-[var(--color-error)]" />
              </div>
              <div class="flex-1">
                <h2 class="text-xl font-semibold text-gray-900 dark:text-white">My Bookmarks</h2>
                <p class="text-gray-600 dark:text-gray-400 text-sm">{{ bookmarks.length }} saved events</p>
              </div>
            </div>

            <!-- Empty State -->
            <div v-if="bookmarks.length === 0" class="text-center py-12">
              <div class="w-20 h-20 bg-gray-100 dark:bg-gray-700 rounded-full mx-auto mb-4 flex items-center justify-center">
                <Heart class="w-8 h-8 text-gray-400" />
              </div>
              <h3 class="text-lg font-medium text-gray-900 dark:text-white mb-2">No bookmarks yet</h3>
              <p class="text-gray-600 dark:text-gray-400 mb-4">Start exploring events and save your favorites!</p>
              <button @click="$router.push('/events')" class="bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-white px-6 py-2 rounded-lg transition-colors">
                Browse Events
              </button>
            </div>

            <!-- Bookmarks Grid -->
            <div v-else class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
              <BookmarkCard
                v-for="bookmark in bookmarks"
                :key="bookmark.id"
                :bookmark="bookmark"
                @remove-bookmark="removeBookmark"
                @book-now="bookNow"
              />
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Footer -->
    <AppFooter />
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../stores/authStore";
import { User, Settings, Heart } from "lucide-vue-next";
import Navbar from "../components/Navbar/Navbar.vue";
import AppFooter from "../components/AppFooter/AppFooter.vue";
import UserProfileInput from "../components/Auth/UserProfileInput.vue";
import BookmarkCard from "../components/Cards/BookmarkCard.vue";
import { getEvents } from "../apis/EventDetalisApi";

const authStore = useAuthStore();
const router = useRouter();
const loading = ref(false);
const errorMessage = ref("");

const form = reactive({
  name: "",
  email: "",
  phoneNumber: "",
  password: "",
  confirmPassword: "",
});

const userData = ref(null);
const bookmarks = ref([]);

onMounted(async () => {
  try {
    const user = await authStore.getUser();
    userData.value = user;
    form.name = user.name || "";
    form.email = user.email || "";
    form.phoneNumber = user.phoneNumber || "";

    // Load bookmarked events
    await loadBookmarks();
  } catch (err) {
    console.error("Failed to fetch user data:", err);
    errorMessage.value = "Failed to load user data";
  }
});

const loadBookmarks = async () => {
  try {
    const savedEventIds = JSON.parse(localStorage.getItem('savedEvents') || '[]');
    if (savedEventIds.length === 0) {
      bookmarks.value = [];
      return;
    }

    // Fetch all events and filter by saved IDs
    const allEvents = await getEvents();
    bookmarks.value = allEvents.filter(event => savedEventIds.includes(event.id));
  } catch (error) {
    console.error('Failed to load bookmarks:', error);
    bookmarks.value = [];
  }
};

const handleSubmit = async () => {
  errorMessage.value = "";

  // Validate password confirmation only if password is provided
  if (form.password && form.password !== form.confirmPassword) {
    errorMessage.value = "New passwords do not match.";
    return;
  }

  try {
    loading.value = true;

    const payload = {
      name: form.name,
      email: form.email,
      phoneNumber: form.phoneNumber,
    };

    // Only include password if it's provided
    if (form.password) {
      payload.password = form.password;
    }

    const updatedUser = await authStore.updateUser(payload);

    // Update form with response data
    form.name = updatedUser.name || "";
    form.email = updatedUser.email || "";
    form.phoneNumber = updatedUser.phoneNumber || "";
    
    // Clear password fields
    form.password = "";
    form.confirmPassword = "";

    alert("Profile updated successfully!");
  } catch (err) {
    console.error("Update error:", err);
    errorMessage.value = err.response?.data?.message || "Update failed";
  } finally {
    loading.value = false;
  }
};

const removeBookmark = (id) => {
  // Remove from local state
  bookmarks.value = bookmarks.value.filter((b) => b.id !== id);

  // Remove from localStorage
  const savedEventIds = JSON.parse(localStorage.getItem('savedEvents') || '[]');
  const updatedIds = savedEventIds.filter(eventId => eventId !== id);
  localStorage.setItem('savedEvents', JSON.stringify(updatedIds));
};

const bookNow = (eventId) => {
  router.push(`/events/ticket-types/${eventId}`);
};
</script>

<style scoped>
.auth-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

/* Animation for loading spinner */
@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.animate-spin {
  animation: spin 1s linear infinite;
}
</style>