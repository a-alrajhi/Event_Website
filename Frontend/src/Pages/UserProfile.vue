<template>
  <div class="user-profile-page">
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

        <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

        <div class="button">
          <button type="submit" :disabled="loading">
            <span v-if="loading">Updating...</span>
            <span v-else>Update Profile</span>
          </button>
        </div>
      </form>
    </div>

    <div class="bookmarks-section">
      <h2 class="title">My Bookmarks</h2>

      <p v-if="bookmarks.length === 0" class="no-bookmarks">
        There are no bookmarks yet.
      </p>

      <div v-else class="bookmarks-grid">
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
</template>

<script setup>
import { reactive, ref, onMounted } from "vue";
import { useAuthStore } from "../stores/authStore";
import UserProfileInput from "../components/Auth/UserProfileInput.vue";
import BookmarkCard from "../components/Cards/BookmarkCard.vue";
import "../styles/userProfile.css";

const authStore = useAuthStore();
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

const bookmarks = ref([
  {
    id: 101,
    title: "Here is the title",
    description: "Here is the description",
    image: "",
  },
]);

onMounted(async () => {
  try {
    const user = await authStore.getUser();
    userData.value = user;
    form.name = user.name || "";
    form.email = user.email || "";
    form.phoneNumber = user.phoneNumber || "";
  } catch (err) {
    console.error("Failed to fetch user data:", err);
  }
});

const handleSubmit = async () => {
  errorMessage.value = "";

  if (form.password !== form.confirmPassword) {
    errorMessage.value = "New passwords do not match.";
    return;
  }

  try {
    loading.value = true;

    const payload = {
      name: form.name,
      email: form.email,
      phoneNumber: form.phoneNumber,
      password: form.password || undefined,
    };

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

const removeBookmark = (id) => {
  bookmarks.value = bookmarks.value.filter((b) => b.id !== id);
  alert(`Bookmark with event ID ${id} removed`);
};

const bookNow = (eventId) => {
  alert(`Redirecting to booking page for event #${eventId}`);
};
</script>
