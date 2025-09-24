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

                <form class="form" @submit.prevent="handleSubmit">
                  <UserProfileInput id="name" label="Full Name" v-model="form.name" required />
                  <UserProfileInput id="email" label="Email" type="email" v-model="form.email" required />
                  <UserProfileInput id="phoneNumber" label="Phone Number" type="tel" v-model="form.phoneNumber" required />
                  <UserProfileInput id="password" label="New Password" type="password" v-model="form.password" minlength="6" />
                  <UserProfileInput id="confirmPassword" label="Confirm Password" type="password" v-model="form.confirmPassword" minlength="6" />

                  <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

                  <button type="submit" :disabled="loading" class="submit-btn">
                    <span v-if="loading">Updating...</span>
                    <span v-else>Update Profile</span>
                  </button>
                </form>
              </div>
            </div>

            <!-- Bookmarks -->
            <div class="md:col-span-2">
              <div class="card bookmarks-card">
                <div class="card-header">
                  <h2 class="card-title">My Bookmarks</h2>
                </div>

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

        <!--  Footer -->
        <div class="footer-wrapper">
          <AppFooter />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from "vue";
import { useAuthStore } from "../stores/authStore";
import UserProfileInput from "../components/Auth/UserProfileInput.vue";
import BookmarkCard from "../components/Cards/BookmarkCard.vue";
import Navbar from "../components/Navbar/Navbar.vue";
import AppFooter from "../components/AppFooter/AppFooter.vue";

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

const bookmarks = ref([
  {
    id: 101,
    title: "test",
    description: "description.",
    image: "https://via.placeholder.com/400x200.png?text=Tech+Conference",
  },
]);

onMounted(async () => {
  try {
    const user = await authStore.getUser();
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
    await authStore.updateUser(payload);
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
  alert(`Bookmark with ID ${id} removed`);
};

const bookNow = (bookmark) => {
  alert(`Redirecting to booking page for ${bookmark.title}`);
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
  border-bottom: 2px solid #e5e7eb; /* subtle divider */
  padding-bottom: 0.6rem;
}

.card-header .icon {
  font-size: 1.5rem;
}
.card-title {
  font-size: 1.6rem;
  font-weight: 800;
  color: #111827;
  letter-spacing: -0.3px;
}
.profile-card {
  max-width: 340px;
  margin: 0 auto;
}
.bookmarks-card {
  min-height: 30rem;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
.submit-btn {
  width: 100%;
  padding: 0.7rem;
  background: linear-gradient(to right, #2563eb, #1d4ed8);
  color: white;
  border-radius: 0.5rem;
  font-weight: 600;
  font-size: 0.9rem;
  transition: all 0.3s ease;
  margin-top: 0.5rem;
}
.submit-btn:hover {
  background: linear-gradient(to right, #1d4ed8, #1e40af);
  transform: scale(1.02);
}
.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
.error-message {
  color: #dc2626;
  font-size: 0.85rem;
}
.no-bookmarks {
  color: #6b7280;
  font-size: 0.95rem;
  text-align: center;
  padding: 1.5rem;
}
.bookmarks-grid {
  display: grid;
  gap: 1.25rem;
}
@media (min-width: 768px) {
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
</style>
