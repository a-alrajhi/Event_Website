# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Development
- `npm run dev` - Start development server with Vite
- `npm run build` - Build for production
- `npm run preview` - Preview production build locally

### Note
No explicit lint or test commands are configured in package.json. ESLint and Prettier are listed as dependencies but not configured with npm scripts.

## Architecture Overview

This is a Vue 3 frontend application for an event management platform built with:

### Core Stack
- **Vue 3** with Composition API (`<script setup>`)
- **Vite** as build tool and dev server
- **Vue Router** for client-side routing
- **Pinia** for state management with persistence
- **Vue i18n** for internationalization (Arabic/English)

### UI Framework & Styling
- **PrimeVue** with Aura theme as primary UI library
- **TailwindCSS** for utility-first styling
- **Naive UI** for additional components
- **Flowbite** and **FlyonUI** for extended component sets

### Key Features & Integrations
- **Supabase** for backend services and authentication
- **Leaflet** with marker clustering for interactive maps
- **Chart.js** for data visualization
- **Swiper** for carousels and sliders
- **Axios** for HTTP requests
- **Yup** for form validation

## Project Structure

### State Management (Pinia Stores)
- `authStore.js` - Authentication and user session
- `userStore.js` - User profile management
- `paymentStore.js` - Payment processing state
- `createEventStore.js` - Event creation workflow
- `adminStatsStore.js` - Admin dashboard statistics
- `createTTStore.js` - Ticket type creation

### API Layer
- `supabaseClient.js` - Supabase client configuration
- `axiosClient.js` - HTTP client setup
- `eventApi.js` - Event-related API calls
- `searchapi.js` - Search functionality
- `EventDetalisApi.js` - Event details API
- `slotsAndTicketTypes.js` - Slot and ticket type management

### Core Pages
- `Home.vue` - Landing page
- `EventBrowse.vue` - Event listing and search
- `EventDetailsPage.vue` - Individual event details
- `EventPaymentPage.vue` - Payment processing
- `EventConfirmationPage.vue` - Booking confirmation
- `Auth.vue` - Authentication (login/register)
- `UserProfile.vue` - User account management

### Admin Panel
- `AdminForms.vue` - Event creation forms
- `AdminStats.vue` - Analytics dashboard
- `EventManagement.vue` - Event administration
- `UserManagement.vue` - User administration
- Admin components located in `src/components/Admin/`

### Route Protection
Router includes authentication guards that check token validity and redirect unauthenticated users to login page.

### Internationalization
- Default locale: English with Arabic fallback
- Translation files: `src/locales/en.json` and `src/locales/ar.json`

## Development Notes

### Authentication Flow
Uses JWT tokens managed through Pinia store with persistence. Token validation occurs on protected routes.

### Map Integration
Leaflet maps are used for event location display with custom markers and clustering support.

### Payment Integration
Multi-provider payment system supporting various Saudi payment methods (MADA, STC Pay, etc.).

### Styling Approach
Hybrid approach combining PrimeVue components with TailwindCSS utilities. Multiple UI libraries provide extensive component options.