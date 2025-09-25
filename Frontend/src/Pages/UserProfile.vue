<template>
  <div>
    <!-- Navbar -->
    <Navbar />

    <div class="profile-wrapper">
      <div class="overlay">
        <!-- Main Profile Page -->
        <div class="user-profile-page">
          <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
            <!-- Update Profile -->
            <div class="md:col-span-1">
              <div class="card profile-card">
                <div class="card-header">
                  <h2 class="card-title">Update Profile</h2>
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

                  <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

                  <div class="button-container">
                    <button type="submit" class="submit-btn" :disabled="loading">
                      <span v-if="loading">Updating...</span>
                      <span v-else>Update Profile</span>
                    </button>
                  </div>
                </form>
              </div>
            </div>

            <!-- Bookmarks Section -->
            <div class="md:col-span-2">
              <div class="card bookmarks-card">
                <div class="card-header">
                  <h2 class="card-title">My Bookmarks</h2>
                </div>

                <div class="bookmarks-section">
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
            </div>
          </div>
        </div>

        <!-- Footer -->
        <div class="footer-wrapper">
          <AppFooter />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../stores/authStore";
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
.profile-wrapper {
  background-image: url("https://www.timeoutriyadh.com/cloud/timeoutriyadh/2022/10/23/riyadh-season-opening-ceremony-fireworks.jpg");
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
  min-height: 100vh;
  width: 100%;
  position: relative;
}

.overlay {
  background: rgba(255, 255, 255, 0.9);
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.user-profile-page {
  max-width: 90rem;
  margin: 3rem auto;
  padding: 2rem 1.5rem;
  flex: 1;
}

.grid {
  display: grid;
  gap: 2rem;
}

.grid-cols-1 {
  grid-template-columns: 1fr;
}

@media (min-width: 768px) {
  .md\:grid-cols-3 {
    grid-template-columns: repeat(3, 1fr);
  }
  
  .md\:col-span-1 {
    grid-column: span 1;
  }
  
  .md\:col-span-2 {
    grid-column: span 2;
  }
}

.card {
  background: #fff;
  border-radius: 1rem;
  box-shadow: 0 6px 14px rgba(0, 0, 0, 0.06);
  padding: 1.5rem;
  transition: all 0.3s ease;
}

.card:hover {
  transform: translateY(-3px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.12);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 0.8rem;
  margin-bottom: 1.2rem;
  border-bottom: 2px solid #e5e7eb;
  padding-bottom: 0.6rem;
}

.card-title {
  font-size: 1.6rem;
  font-weight: 800;
  color: #111827;
  letter-spacing: -0.3px;
  margin: 0;
}

.profile-card {
  max-width: 400px;
  margin: 0 auto;
}

.bookmarks-card {
  min-height: 30rem;
}

.auth-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.button-container {
  margin-top: 0.5rem;
}

.submit-btn {
  width: 100%;
  padding: 0.75rem 1rem;
  background: linear-gradient(to right, #2563eb, #1d4ed8);
  color: white;
  border: none;
  border-radius: 0.5rem;
  font-weight: 600;
  font-size: 0.95rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.submit-btn:hover:not(:disabled) {
  background: linear-gradient(to right, #1d4ed8, #1e40af);
  transform: translateY(-1px);
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.error-message {
  color: #dc2626;
  font-size: 0.85rem;
  margin: 0;
}

.bookmarks-section {
  width: 100%;
}

.no-bookmarks {
  color: #6b7280;
  font-size: 0.95rem;
  text-align: center;
  padding: 2rem;
  margin: 0;
}

.bookmarks-grid {
  display: grid;
  gap: 1.25rem;
  grid-template-columns: 1fr;
}

@media (min-width: 768px) {
  .bookmarks-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 1024px) {
  .bookmarks-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 1200px) {
  .bookmarks-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

.footer-wrapper {
  margin-top: 2rem;
  background: #ffffff;
  border-top: 1px solid #e5e7eb;
  border-radius: 0.75rem 0.75rem 0 0;
  box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.05);
  padding: 1.5rem;
}

/* Responsive adjustments */
@media (max-width: 767px) {
  .user-profile-page {
    padding: 1rem;
    margin: 1rem auto;
  }
  
  .profile-card {
    max-width: 100%;
  }
  
  .grid {
    gap: 1.5rem;
  }
}
</style>