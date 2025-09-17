<template>
  <div class="user-profile-page">
    <!-- Left: Profile Update -->
    <div class="profile-section">
      <h2 class="title">Update Profile</h2>

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
            label="Password"
            type="password"
            placeholder="Enter your password"
            v-model="form.password"
            minlength="6"
        />

        <UserProfileInput
            id="confirmPassword"
            label="Confirm Password"
            type="password"
            placeholder="Re-enter your password"
            v-model="form.confirmPassword"
            minlength="6"
        />

        <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

        <div class="button">
          <button type="submit" :disabled="loading">
            <span v-if="loading">Updating...</span>
            <span v-else>Update Profile</span>
          </button>
        </div>
      </form>
    </div>

    <!-- Right: My Bookmarks -->
    <div class="bookmarks-section">
      <h2 class="title">My Bookmarks</h2>

      <!-- No bookmarks message -->
      <p v-if="bookmarks.length === 0" class="no-bookmarks">
        There are no bookmarks yet.
      </p>

      <div v-else class="bookmarks-grid">
        <div
            v-for="bookmark in bookmarks"
            :key="bookmark.id"
            class="card bg-base-100 w-80 shadow-sm"
        >
          <!-- Image -->
          <figure>
            <img :src="bookmark.image" :alt="bookmark.title" />
          </figure>

          <!-- Body -->
          <div class="card-body">
            <h2 class="card-title">{{ bookmark.title }}</h2>
            <p>{{ bookmark.description }}</p>

            <!-- Actions -->
            <div class="card-actions justify-end">
              <button
                  class="btn btn-error btn-sm"
                  @click="deleteBookmark(bookmark.id)"
              >
                Remove
              </button>
              <button
                  class="btn btn-primary btn-sm"
                  @click="bookNow(bookmark.id)"
              >
                Book Now
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useAuthStore } from "../stores/authStore";
import UserProfileInput from "../components/Auth/UserProfileInput.vue";
import "../styles/userProfile.css";

const authStore = useAuthStore();
const loading = ref(false);
const errorMessage = ref("");

// Example bookmarks (replace with API call later)
const bookmarks = ref([
  {
    id: 101,
    title: "Event #101: Music Festival",
    description: "Join us for a night of live music and fun.",
    image:
        "https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp",
  },
  {
    id: 102,
    title: "Event #102: Tech Conference",
    description: "Discover the latest in technology and innovation.",
    image:
        "https://img.daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.webp",
  },
]);

const form = reactive({
  name: "",
  email: "",
  phoneNumber: "",
  password: "",
  confirmPassword: "",
});

const handleSubmit = async () => {
  errorMessage.value = "";
  if (form.password !== form.confirmPassword) {
    errorMessage.value = "Passwords do not match.";
    return;
  }

  try {
    loading.value = true;
    const payload = { ...form };
    delete payload.confirmPassword;
    if (!payload.password) delete payload.password;

    const updatedUser = await authStore.updateUser(payload);
    form.name = updatedUser.name || "";
    form.email = updatedUser.email || "";
    form.phoneNumber = updatedUser.phoneNumber || "";

    alert("Profile updated successfully!");
  } catch (err) {
    console.error("Update error:", err);
    errorMessage.value = err.response?.data?.message || "Update failed";
  } finally {
    loading.value = false;
  }
};

const deleteBookmark = (id) => {
  bookmarks.value = bookmarks.value.filter((b) => b.id !== id);
  alert(`Bookmark with event ID ${id} removed`);
};

const bookNow = (eventId) => {
  alert(`Redirecting to booking page for event #${eventId}`);
};
</script>
