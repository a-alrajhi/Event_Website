<template>
  <footer class="bg-gradient-to-b from-gray-100 to-gray-50 dark:from-gray-800 dark:to-gray-900 text-gray-900 dark:text-white border-t border-[var(--color-primary)]/20 mt-auto">
    <div class="mx-auto w-full max-w-screen-xl px-4 py-12 lg:py-16">
      <!-- Main Footer Content -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 mb-8">
        <!-- Brand Section -->
        <div class="lg:col-span-2">
      <!-- Logo -->
      <router-link
        to="/"
        class="flex items-center space-x-3 hover:opacity-80 transition-opacity"
      >
        <img
          src="../../assets/logo.svg"
          alt="Eventura Logo"
          class="h-16 w-auto object-contain drop-shadow-sm"
        />
        <span class="text-xl md:text-2xl font-bold text-[var(--color-primary)]">
          Eventura
        </span>
      </router-link>
      <p class="text-gray-600 dark:text-gray-300 mb-6 max-w-md leading-relaxed">
        Discover and experience the best events in Saudi Arabia. From concerts and festivals to workshops and cultural experiences, Eventura connects you with unforgettable moments.
      </p>
          <!-- Newsletter Signup -->
          <div class="space-y-3">
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white">Stay Updated</h3>
            <div class="flex flex-col sm:flex-row gap-2">
              <input
                type="email"
                placeholder="Enter your email"
                class="flex-1 px-4 py-2 event-card border border-[var(--color-primary)]/30 rounded-lg text-gray-900 dark:text-white placeholder-gray-500 dark:placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)] focus:border-transparent"
              />
              <button class="px-6 py-2 bg-[var(--color-primary)] hover:bg-[var(--color-hover)] text-white rounded-lg font-medium transition-colors">
                Subscribe
              </button>
            </div>
          </div>
        </div>

        <!-- Quick Links -->
        <div>
          <h3 class="text-lg font-semibold text-[var(--color-text)] mb-4">Quick Links</h3>
          <ul class="space-y-3">
            <li>
              <router-link to="/" class="text-gray-600 dark:text-gray-300 hover:text-[var(--color-primary)] transition-colors flex items-center gap-2">
                <Home class="w-4 h-4" />
                Home
              </router-link>
            </li>
            <li>
              <router-link to="/events" class="text-gray-600 dark:text-gray-300 hover:text-[var(--color-primary)] transition-colors flex items-center gap-2">
                <Calendar class="w-4 h-4" />
                Events
              </router-link>
            </li>
            <li>
              <router-link to="/faq" class="text-gray-600 dark:text-gray-300 hover:text-[var(--color-primary)] transition-colors flex items-center gap-2">
                <HelpCircle class="w-4 h-4" />
                FAQ
              </router-link>
            </li>
            <li>
              <router-link to="/user-profile" class="text-gray-600 dark:text-gray-300 hover:text-[var(--color-primary)] transition-colors flex items-center gap-2">
                <User class="w-4 h-4" />
                Profile
              </router-link>
            </li>
          </ul>
        </div>

        <!-- Support & Legal -->
        <div>
          <h3 class="text-lg font-semibold text-[var(--color-text)] mb-4">Support & Legal</h3>
          <ul class="space-y-3 mb-6">
            <li>
              <a href="#" class="text-gray-600 dark:text-gray-300 hover:text-[var(--color-primary)] transition-colors">
                Contact Support
              </a>
            </li>
            <li>
              <a href="#" class="text-gray-600 dark:text-gray-300 hover:text-[var(--color-primary)] transition-colors">
                Privacy Policy
              </a>
            </li>
            <li>
              <a href="#" class="text-gray-600 dark:text-gray-300 hover:text-[var(--color-primary)] transition-colors">
                Terms of Service
              </a>
            </li>
            <li>
              <a href="#" class="text-gray-600 dark:text-gray-300 hover:text-[var(--color-primary)] transition-colors">
                Cookie Policy
              </a>
            </li>
          </ul>

          <!-- Social Media -->
          <div>
            <h4 class="text-sm font-semibold text-gray-900 dark:text-white mb-3">Follow Us</h4>
            <div class="flex space-x-3">
              <a
                href="#"
                class="w-8 h-8 event-card rounded-lg flex items-center justify-center text-gray-600 dark:text-gray-300 hover:text-white hover:bg-[var(--color-primary)] transition-all"
                title="Facebook"
              >
                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z"/>
                </svg>
              </a>
              <a
                href="#"
                class="w-8 h-8 event-card rounded-lg flex items-center justify-center text-gray-600 dark:text-gray-300 hover:text-white hover:bg-[var(--color-primary)] transition-all"
                title="Twitter"
              >
                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M23.953 4.57a10 10 0 01-2.825.775 4.958 4.958 0 002.163-2.723c-.951.555-2.005.959-3.127 1.184a4.92 4.92 0 00-8.384 4.482C7.69 8.095 4.067 6.13 1.64 3.162a4.822 4.822 0 00-.666 2.475c0 1.71.87 3.213 2.188 4.096a4.904 4.904 0 01-2.228-.616v.06a4.923 4.923 0 003.946 4.827 4.996 4.996 0 01-2.212.085 4.936 4.936 0 004.604 3.417 9.867 9.867 0 01-6.102 2.105c-.39 0-.779-.023-1.17-.067a13.995 13.995 0 007.557 2.209c9.053 0 13.998-7.496 13.998-13.985 0-.21 0-.42-.015-.63A9.935 9.935 0 0024 4.59z"/>
                </svg>
              </a>
              <a
                href="#"
                class="w-8 h-8 event-card rounded-lg flex items-center justify-center text-gray-600 dark:text-gray-300 hover:text-white hover:bg-[var(--color-primary)] transition-all"
                title="Instagram"
              >
                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M12.017 0C5.396 0 .029 5.367.029 11.987c0 6.62 5.367 11.987 11.988 11.987 6.62 0 11.987-5.367 11.987-11.987C24.014 5.367 18.637.001 12.017.001zM8.449 16.988c-1.297 0-2.448-.73-3.016-1.801L4.27 17.94l-1.5-1.5 2.753-1.163c-.371-.573-.589-1.255-.589-1.988 0-2.084 1.690-3.775 3.775-3.775S12.484 11.205 12.484 13.289c0 2.084-1.690 3.775-3.775 3.775z"/>
                </svg>
              </a>
              <a
                href="#"
                class="w-8 h-8 event-card rounded-lg flex items-center justify-center text-gray-600 dark:text-gray-300 hover:text-white hover:bg-[var(--color-primary)] transition-all"
                title="LinkedIn"
              >
                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 24 24">
                  <path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433c-1.144 0-2.063-.926-2.063-2.065 0-1.138.92-2.063 2.063-2.063 1.14 0 2.064.925 2.064 2.063 0 1.139-.925 2.065-2.064 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z"/>
                </svg>
              </a>
            </div>
          </div>
        </div>
      </div>

      <!-- Divider -->
      <hr class="border-[var(--color-primary)]/20 mb-8" />

      <!-- Bottom Section -->
      <div class="flex flex-col md:flex-row md:items-center md:justify-between space-y-4 md:space-y-0">
        <div class="text-sm text-gray-600 dark:text-gray-300">
          © 2025 <span class="text-[var(--color-primary)] font-semibold">EventVision</span>. All rights reserved.
          <span class="block md:inline md:ml-2">Made with ❤️ in Saudi Arabia</span>
        </div>

        <div class="flex flex-wrap items-center gap-6 text-sm">
          <a href="#" class="text-[var(--color-gray)] hover:text-[var(--color-primary)] transition-colors">
            Accessibility
          </a>
          <a href="#" class="text-[var(--color-gray)] hover:text-[var(--color-primary)] transition-colors">
            Sitemap
          </a>
          <div class="flex items-center gap-2 text-gray-600 dark:text-gray-300">
            <Globe class="w-4 h-4" />
            <select class="bg-transparent text-gray-600 dark:text-gray-300 border-none text-sm focus:outline-none cursor-pointer">
              <option value="en">English</option>
              <option value="ar">العربية</option>
            </select>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup>
import { Calendar, Home, HelpCircle, User, Globe } from 'lucide-vue-next';
</script>
