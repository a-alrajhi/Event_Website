<template>
  <div class="min-h-screen bg-[var(--color-bg)] text-[var(--color-text)] font-[var(--font-poppins)]">
    <!-- Navbar -->
    <Navbar />

    <!-- Hero Section -->
    <section class="bg-gradient-to-br from-[var(--color-primary)]/20 via-[var(--color-secondary)]/10 to-[var(--color-bg)] py-16">
      <div class="container mx-auto px-4 text-center">
        <div class="max-w-3xl mx-auto">
          <h1 class="text-4xl md:text-5xl font-bold text-[var(--color-text)] mb-4">
            Frequently Asked Questions
          </h1>
          <p class="text-lg text-[var(--color-gray)] mb-8">
            Find answers to the most common questions about our events platform
          </p>

          <!-- Search FAQ -->
          <div class="relative max-w-md mx-auto">
            <input
              v-model="searchQuery"
              type="text"
              placeholder="Search FAQs..."
              class="w-full px-4 py-3 pl-12 bg-[var(--color-card)] border border-[var(--color-primary)]/30 rounded-xl text-[var(--color-text)] placeholder-[var(--color-gray)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)] focus:border-transparent"
            />
            <Search class="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-[var(--color-gray)]" />
          </div>
        </div>
      </div>
    </section>

    <!-- FAQ Categories -->
    <section class="container mx-auto px-4 py-12">
      <div class="flex flex-wrap justify-center gap-4 mb-12">
        <button
          v-for="category in categories"
          :key="category.id"
          @click="selectedCategory = category.id"
          :class="[
            'flex items-center gap-2 px-6 py-3 rounded-full font-medium transition-all',
            selectedCategory === category.id
              ? 'bg-[var(--color-primary)] text-white shadow-lg'
              : 'bg-[var(--color-card)] text-[var(--color-text)] hover:bg-[var(--color-primary)]/10 border border-[var(--color-primary)]/20'
          ]"
        >
          <component :is="category.icon" class="w-5 h-5" />
          {{ category.name }}
          <span class="bg-black/20 px-2 py-1 rounded-full text-xs">
            {{ getFAQCountForCategory(category.id) }}
          </span>
        </button>
      </div>

      <!-- FAQ List -->
      <div class="max-w-4xl mx-auto">
        <div v-if="filteredFAQs.length === 0" class="text-center py-12">
          <div class="w-20 h-20 bg-[var(--color-primary)]/10 rounded-full flex items-center justify-center mx-auto mb-4">
            <HelpCircle class="w-8 h-8 text-[var(--color-primary)]" />
          </div>
          <h3 class="text-xl font-semibold text-[var(--color-text)] mb-2">No FAQs found</h3>
          <p class="text-[var(--color-gray)]">Try adjusting your search or category filter</p>
        </div>

        <div v-else class="space-y-4">
          <div
            v-for="faq in filteredFAQs"
            :key="faq.id"
            class="bg-[var(--color-card)]/90 backdrop-blur-sm rounded-2xl shadow-lg border border-[var(--color-primary)]/10 overflow-hidden"
          >
            <button
              @click="toggleFAQ(faq.id)"
              class="w-full px-6 py-4 text-left flex items-center justify-between hover:bg-[var(--color-primary)]/5 transition-colors"
            >
              <div class="flex items-start gap-3 flex-1">
                <div class="w-8 h-8 bg-[var(--color-primary)]/10 rounded-full flex items-center justify-center mt-1 flex-shrink-0">
                  <component :is="getCategoryIcon(faq.category)" class="w-4 h-4 text-[var(--color-primary)]" />
                </div>
                <div class="flex-1">
                  <h3 class="text-lg font-semibold text-[var(--color-text)] mb-1">
                    {{ faq.question }}
                  </h3>
                  <p class="text-sm text-[var(--color-gray)]">
                    {{ getCategoryName(faq.category) }}
                  </p>
                </div>
              </div>
              <ChevronDown
                :class="[
                  'w-5 h-5 text-[var(--color-gray)] transition-transform duration-200 flex-shrink-0',
                  openFAQs.includes(faq.id) ? 'transform rotate-180' : ''
                ]"
              />
            </button>

            <div
              v-if="openFAQs.includes(faq.id)"
              class="px-6 pb-6 border-t border-[var(--color-primary)]/10"
            >
              <div class="pt-4 prose prose-invert max-w-none">
                <div v-html="faq.answer" class="text-[var(--color-text)] leading-relaxed"></div>

                <!-- Tags -->
                <div v-if="faq.tags && faq.tags.length > 0" class="flex flex-wrap gap-2 mt-4">
                  <span
                    v-for="tag in faq.tags"
                    :key="tag"
                    class="px-3 py-1 bg-[var(--color-primary)]/10 text-[var(--color-primary)] rounded-full text-sm font-medium"
                  >
                    {{ tag }}
                  </span>
                </div>

                <!-- Helpful Section -->
                <div class="flex items-center justify-between mt-6 pt-4 border-t border-[var(--color-primary)]/10">
                  <span class="text-sm text-[var(--color-gray)]">Was this helpful?</span>
                  <div class="flex gap-2">
                    <button
                      @click="markHelpful(faq.id, true)"
                      class="flex items-center gap-1 px-3 py-1 rounded-lg hover:bg-[var(--color-primary)]/10 transition-colors text-sm"
                    >
                      <ThumbsUp class="w-4 h-4" />
                      Yes ({{ faq.helpful || 0 }})
                    </button>
                    <button
                      @click="markHelpful(faq.id, false)"
                      class="flex items-center gap-1 px-3 py-1 rounded-lg hover:bg-[var(--color-error)]/10 transition-colors text-sm"
                    >
                      <ThumbsDown class="w-4 h-4" />
                      No ({{ faq.notHelpful || 0 }})
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Contact Support Section -->
    <section class="bg-[var(--color-card)]/50 py-16">
      <div class="container mx-auto px-4 text-center">
        <div class="max-w-2xl mx-auto">
          <h2 class="text-3xl font-bold text-[var(--color-text)] mb-4">
            Still need help?
          </h2>
          <p class="text-[var(--color-gray)] mb-8">
            Can't find the answer you're looking for? Our support team is here to help you.
          </p>

          <div class="flex flex-col sm:flex-row gap-4 justify-center">
            <button class="bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-white px-6 py-3 rounded-xl font-medium transition-colors flex items-center gap-2 justify-center">
              <Mail class="w-5 h-5" />
              Contact Support
            </button>
            <button class="bg-[var(--color-card)] hover:bg-[var(--color-primary)]/10 text-[var(--color-text)] border border-[var(--color-primary)]/30 px-6 py-3 rounded-xl font-medium transition-colors flex items-center gap-2 justify-center">
              <MessageCircle class="w-5 h-5" />
              Live Chat
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- Footer -->
    <AppFooter />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import Navbar from '../components/Navbar/Navbar.vue';
import AppFooter from '../components/AppFooter/AppFooter.vue';
import {
  Search,
  HelpCircle,
  ChevronDown,
  ThumbsUp,
  ThumbsDown,
  Mail,
  MessageCircle,
  Calendar,
  CreditCard,
  User,
  Settings,
  Ticket,
  MapPin
} from 'lucide-vue-next';

const searchQuery = ref('');
const selectedCategory = ref('all');
const openFAQs = ref([]);

const categories = [
  { id: 'all', name: 'All Categories', icon: HelpCircle },
  { id: 'events', name: 'Events', icon: Calendar },
  { id: 'tickets', name: 'Tickets', icon: Ticket },
  { id: 'payments', name: 'Payments', icon: CreditCard },
  { id: 'account', name: 'Account', icon: User },
  { id: 'technical', name: 'Technical', icon: Settings }
];

const faqs = ref([
  {
    id: 1,
    category: 'events',
    question: 'How do I find events in my area?',
    answer: `
      <p>Finding events in your area is easy! You can:</p>
      <ul>
        <li>Use the search bar and enter your location</li>
        <li>Browse the map view to see events near you</li>
        <li>Filter by location in the events page</li>
        <li>Set your location preferences in your profile</li>
      </ul>
      <p>We'll automatically show you events that are closest to your location first.</p>
    `,
    tags: ['location', 'search', 'browse'],
    helpful: 15,
    notHelpful: 2
  },
  {
    id: 2,
    category: 'tickets',
    question: 'Can I get a refund for my tickets?',
    answer: `
      <p>Our refund policy depends on the event and timing:</p>
      <ul>
        <li><strong>More than 7 days before:</strong> Full refund available</li>
        <li><strong>3-7 days before:</strong> 75% refund</li>
        <li><strong>Less than 3 days:</strong> No refund, but you can transfer to someone else</li>
      </ul>
      <p>Some events may have different policies - always check the event details before purchasing.</p>
    `,
    tags: ['refund', 'policy', 'cancellation'],
    helpful: 23,
    notHelpful: 5
  },
  {
    id: 3,
    category: 'payments',
    question: 'What payment methods do you accept?',
    answer: `
      <p>We accept various payment methods including:</p>
      <ul>
        <li>MADA (Saudi local cards)</li>
        <li>Visa and MasterCard</li>
        <li>STC Pay</li>
        <li>Apple Pay and Google Pay</li>
        <li>Bank transfers for large purchases</li>
      </ul>
      <p>All payments are processed securely through our payment partners.</p>
    `,
    tags: ['payment', 'methods', 'secure'],
    helpful: 18,
    notHelpful: 1
  },
  {
    id: 4,
    category: 'account',
    question: 'How do I update my profile information?',
    answer: `
      <p>To update your profile:</p>
      <ol>
        <li>Click on your profile picture in the top right corner</li>
        <li>Select "Profile Settings" from the dropdown</li>
        <li>Edit any information you want to change</li>
        <li>Click "Save Changes" to update</li>
      </ol>
      <p>You can update your name, email, phone number, and preferences at any time.</p>
    `,
    tags: ['profile', 'settings', 'update'],
    helpful: 12,
    notHelpful: 0
  },
  {
    id: 5,
    category: 'events',
    question: 'How do I create an event?',
    answer: `
      <p>Creating an event is simple:</p>
      <ol>
        <li>Go to the Events page</li>
        <li>Click "Create Event" button</li>
        <li>Fill in event details (name, description, date, location)</li>
        <li>Set ticket types and pricing</li>
        <li>Add photos and additional information</li>
        <li>Submit for review</li>
      </ol>
      <p>Our team will review your event within 24 hours and notify you once it's approved.</p>
    `,
    tags: ['create', 'organizer', 'approval'],
    helpful: 28,
    notHelpful: 3
  },
  {
    id: 6,
    category: 'technical',
    question: 'The app is running slowly. What can I do?',
    answer: `
      <p>If you're experiencing slow performance, try these steps:</p>
      <ul>
        <li>Clear your browser cache and cookies</li>
        <li>Check your internet connection</li>
        <li>Close other browser tabs</li>
        <li>Try using a different browser</li>
        <li>Disable browser extensions temporarily</li>
      </ul>
      <p>If the problem persists, please contact our support team with details about your device and browser.</p>
    `,
    tags: ['performance', 'troubleshooting', 'browser'],
    helpful: 9,
    notHelpful: 2
  },
  {
    id: 7,
    category: 'tickets',
    question: 'How do I transfer my ticket to someone else?',
    answer: `
      <p>To transfer your ticket:</p>
      <ol>
        <li>Go to "My Tickets" in your profile</li>
        <li>Find the ticket you want to transfer</li>
        <li>Click "Transfer Ticket"</li>
        <li>Enter the recipient's email address</li>
        <li>They'll receive an email to claim the ticket</li>
      </ol>
      <p>Note: Some events may not allow ticket transfers. Check the event details for restrictions.</p>
    `,
    tags: ['transfer', 'share', 'tickets'],
    helpful: 14,
    notHelpful: 1
  },
  {
    id: 8,
    category: 'payments',
    question: 'Why was my payment declined?',
    answer: `
      <p>Payment declines can happen for several reasons:</p>
      <ul>
        <li>Insufficient funds in your account</li>
        <li>Incorrect card details entered</li>
        <li>Your bank flagged the transaction as suspicious</li>
        <li>Card expired or blocked</li>
        <li>International transactions not enabled</li>
      </ul>
      <p>Try using a different payment method or contact your bank if the issue persists.</p>
    `,
    tags: ['payment', 'declined', 'troubleshooting'],
    helpful: 11,
    notHelpful: 4
  }
]);

const filteredFAQs = computed(() => {
  let filtered = faqs.value;

  // Filter by category
  if (selectedCategory.value !== 'all') {
    filtered = filtered.filter(faq => faq.category === selectedCategory.value);
  }

  // Filter by search query
  if (searchQuery.value.trim()) {
    const query = searchQuery.value.toLowerCase();
    filtered = filtered.filter(faq =>
      faq.question.toLowerCase().includes(query) ||
      faq.answer.toLowerCase().includes(query) ||
      faq.tags.some(tag => tag.toLowerCase().includes(query))
    );
  }

  return filtered;
});

const getFAQCountForCategory = (categoryId) => {
  if (categoryId === 'all') {
    return faqs.value.length;
  }
  return faqs.value.filter(faq => faq.category === categoryId).length;
};

const getCategoryIcon = (categoryId) => {
  const category = categories.find(cat => cat.id === categoryId);
  return category ? category.icon : HelpCircle;
};

const getCategoryName = (categoryId) => {
  const category = categories.find(cat => cat.id === categoryId);
  return category ? category.name : 'General';
};

const toggleFAQ = (faqId) => {
  const index = openFAQs.value.indexOf(faqId);
  if (index > -1) {
    openFAQs.value.splice(index, 1);
  } else {
    openFAQs.value.push(faqId);
  }
};

const markHelpful = (faqId, isHelpful) => {
  const faq = faqs.value.find(f => f.id === faqId);
  if (faq) {
    if (isHelpful) {
      faq.helpful = (faq.helpful || 0) + 1;
    } else {
      faq.notHelpful = (faq.notHelpful || 0) + 1;
    }
  }
};
</script>

<style scoped>
.prose ul {
  list-style-type: disc;
  margin-left: 1.5rem;
  margin-bottom: 1rem;
}

.prose ol {
  list-style-type: decimal;
  margin-left: 1.5rem;
  margin-bottom: 1rem;
}

.prose li {
  margin-bottom: 0.5rem;
}

.prose p {
  margin-bottom: 1rem;
}

.prose strong {
  font-weight: 600;
  color: var(--color-primary);
}
</style>