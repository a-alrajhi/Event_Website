# 🎫 Event Management Platform Frontend

A modern, sophisticated event management and ticket booking platform built with Vue.js 3, featuring a sleek dark mode with glassmorphism design, comprehensive payment processing, and digital ticket generation with QR codes.

![Vue.js](https://img.shields.io/badge/Vue.js-3.4-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-5.4-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.4-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)
![PrimeVue](https://img.shields.io/badge/PrimeVue-4.1-FF6D00?style=for-the-badge&logo=vue.js&logoColor=white)

## ✨ Features

### 🎪 Event Management

- **Event Discovery**: Browse and search events with advanced filtering
- **Event Details**: Comprehensive event pages with media, location maps, and similar events
- **Interactive Maps**: Leaflet-powered location mapping with venue details
- **Category-based Browsing**: Organized event categorization system

### 🎟️ Ticket Booking System

- **Smart Ticket Selection**: Dynamic ticket types with real-time capacity checking
- **Multi-step Checkout**: Intuitive 3-step booking process (Selection → Payment → Confirmation)
- **Real-time Availability**: Live ticket capacity updates via backend API integration
- **Grouped Ticket Management**: Efficient handling of multiple ticket purchases

### 💳 Payment Processing

- **Multiple Payment Methods**: Support for Credit Cards, Apple Pay, Visa, Mastercard, Mada, and STC Pay
- **Dynamic Payment UI**: Contextual button styling based on selected payment method
- **Secure Checkout**: JWT-authenticated payment processing
- **Order Summary**: Detailed breakdown of ticket selections and pricing

### 📱 Digital Tickets

- **QR Code Generation**: Instant digital tickets with scannable QR codes
- **Glassmorphism Design**: Premium ticket design with frosted glass effects
- **Download & Share**: Native sharing capabilities and ticket downloads
- **Responsive Layout**: Optimized for mobile and desktop viewing

### 🌙 Modern UI/UX

- **Dark Mode Support**: Comprehensive dark/light theme switching
- **Glassmorphism Effects**: Modern frosted glass design throughout
- **Responsive Design**: Mobile-first approach with Tailwind CSS
- **Smooth Animations**: CSS transitions and hover effects
- **Accessibility**: WCAG-compliant design patterns

### 🔐 Authentication & User Management

- **JWT Authentication**: Secure user sessions with token management
- **User Profiles**: Personal account management and settings
- **Admin Dashboard**: Comprehensive admin panel for event and user management
- **Role-based Access**: Different access levels for users and administrators

### 🌍 Internationalization

- **Multi-language Support**: Arabic and English language support
- **RTL Support**: Right-to-left language compatibility
- **Localized Content**: Dynamic content translation

## 🛠️ Technology Stack

### Frontend Framework

- **Vue.js 3** - Progressive JavaScript framework with Composition API
- **Vite** - Next-generation build tool for faster development
- **Vue Router** - Official routing solution for Vue.js applications

### UI & Styling

- **Tailwind CSS** - Utility-first CSS framework for rapid UI development
- **PrimeVue** - Rich UI component library with Aura theme
- **CSS Glassmorphism** - Custom frosted glass effects and animations
- **Responsive Design** - Mobile-first approach with flexible layouts

### State Management

- **Pinia** - Intuitive Vue.js state management with persistence
- **Persistent Stores** - Local storage integration for cart and auth state

### Utilities & Libraries

- **Axios** - Promise-based HTTP client for API communication
- **Day.js** - Modern date manipulation library
- **Vue I18n** - Internationalization plugin for Vue.js
- **Leaflet** - Interactive map integration for venue locations
- **QR Code Generation** - Dynamic QR code creation for digital tickets

## 📁 Project Structure

```
src/
├── apis/                    # API integration layer
│   ├── axiosClient.js      # HTTP client configuration
│   ├── eventApi.js         # Event-related API endpoints
│   ├── searchapi.js        # Search functionality
│   └── slotsAndTicketTypes.js # Ticket management APIs
├── components/             # Reusable Vue components
│   ├── Admin/             # Admin panel components
│   ├── Auth/              # Authentication components
│   ├── Cards/             # Event card components
│   ├── Event/             # Event-specific components
│   ├── Map/               # Map integration components
│   ├── Navbar/            # Navigation components
│   └── SearchBar/         # Search functionality
├── Pages/                 # Main application pages
│   ├── Home.vue           # Landing page
│   ├── EventDetailsPage.vue # Event information
│   ├── EventPaymentPage.vue # Payment processing
│   └── EventConfirmationPage.vue # Booking confirmation
├── stores/                # Pinia state management
│   ├── authStore.js       # Authentication state
│   ├── paymentStore.js    # Payment and checkout state
│   └── userStore.js       # User profile management
├── Router/                # Vue Router configuration
├── styles/                # Global styling
├── Utils/                 # Utility components
└── locales/               # Internationalization files
```

## 🛠️ Backend Technology Stack

The backend is built with **Spring Boot** to provide a robust, secure, and scalable API layer for the frontend.

### Core Framework

- **Spring Boot 3.x** – Main framework for RESTful API development
- **Spring Security** – Authentication & authorization with JWT tokens
- **Spring Data JPA (Hibernate)** – ORM for database interactions
- **Maven** – Build automation and dependency management

### Database

- **PostgreSQL** – Relational database for events, tickets, and user data
- **JPA Entities & Repositories** – Structured domain model with repository pattern
- **Custom Queries** – Optimized queries via custom repositories

### API Structure

- **Controllers** – Handle HTTP requests (`EventController`, `TicketController`, `AuthController`, etc.)
- **DTOs (Data Transfer Objects)** – Decouple API layer from entities (`EventDto`, `TicketDTO`, `UserDTO`, etc.)
- **Services** – Business logic layer (`EventService`, `TicketService`, etc.)
- **Repositories** – Data access layer with JPA (`EventRepo`, `TicketRepo`, etc.)

### Security & Auth

- **JWT Authentication** – Stateless authentication with custom `JwtFilter`
- **Role-based Access** – User/Admin separation
- **Global Exception Handling** – Custom exceptions and `GlobalExceptionHandler`

### Utilities

- **Logging (AOP)** – Request/response logging with `RequestLoggingAspect`
- **Ticket Code Generator** – Unique ticket code creation
- **Query Utilities** – Helper methods for dynamic queries

## 🔧 Key Components

### EventTicketTypes.vue

- Dynamic ticket selection interface
- Real-time capacity checking via backend API
- Responsive quantity controls with validation
- Integration with payment store for checkout flow

### TicketComponent.vue

- Premium digital ticket design with glassmorphism
- QR code generation and display
- Download and sharing capabilities
- Responsive layout for all device sizes

### EventPaymentPage.vue

- Multi-method payment processing
- Dynamic payment method selection
- Order summary with ticket breakdown
- Secure checkout flow integration

### EventConfirmationPage.vue

- Booking confirmation display
- Generated ticket presentation
- Event details summary
- Success state with next steps

### AnalyticsDashboard.vue

- Centralized admin analytics dashboard
- Displays key metrics: revenue, users, events, and tickets sold
- Interactive charts for monthly revenue and ticket sales (line + bar charts)
- Top-performing events table with live data
- Quick actions for managing events, users, and refreshing stats
- Powered by **PrimeVue Charts** and **Pinia** state management

### EventLocationForm.vue

- Handles event location selection during event creation/editing
- Supports two modes:
  - **Predefined Location Selector** – choose from stored venue list
  - **Custom Map Input** – add a new location using an interactive map
- Integrates with **Leaflet-powered map** via `EventLocationMap.vue`
- Real-time coordinate binding with validation
- Allows rollback between predefined and custom location modes
- Persists valid location data to the **Pinia createEventStore**

### Home.vue

- Landing page showcasing featured events and platform highlights
- **Hero Section**: Engaging header with Riyadh Season banner and CTA
- **Info Cards**: Highlights upcoming events, venues, and categories
- **Slider Section**: Featured events carousel for quick discovery
- **Best Events Section**: Curated top events with enhanced card layout
- **Why Choose EventVision**: Trust, instant booking, premium experience highlights
- **Stats Section**: Dynamic numbers for customer base, events, cities, and rating
- **Footer & Navbar** integration for full page structure

## 🚀 Getting Started

### Prerequisites

- Node.js (v16 or higher)
- npm or yarn package manager
- Modern web browser with ES6+ support

### Installation

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd Event_Website/Frontend
   ```

2. **Install dependencies**

   ```bash
   npm install
   # or
   yarn install
   ```

3. **Environment Configuration**
   Create a `.env` file in the root directory:

   ```env
   VITE_API_BASE_URL=http://localhost:8080/api
   VITE_APP_TITLE=Event Management Platform
   ```

4. **Start development server**

   ```bash
   npm run dev
   # or
   yarn dev
   ```

5. **Build for production**
   ```bash
   npm run build
   # or
   yarn build
   ```

## 🎯 Usage

### Event Discovery

1. Browse events on the home page
2. Use search and filters to find specific events
3. View detailed event information including location and rules

### Ticket Booking

1. Select an event and click "Book Tickets"
2. Choose ticket types and quantities
3. Proceed through the 3-step checkout process:
   - **Step 1**: Ticket Selection
   - **Step 2**: Payment Processing
   - **Step 3**: Confirmation & Digital Tickets

### Digital Tickets

- Access tickets from confirmation page
- Download or share tickets directly
- Present QR codes at venue entrance

## 🔒 Security Features

- **JWT Authentication**: Secure token-based authentication
- **API Interceptors**: Automatic token refresh and error handling
- **Input Validation**: Client-side validation for all forms
- **HTTPS Ready**: Production-ready security configurations

## 🌐 API Integration

The frontend communicates with a Spring Boot backend through RESTful APIs:

- **Event Management**: CRUD operations for events and categories
- **Ticket System**: Real-time capacity checking and booking
- **Payment Processing**: Secure payment method handling
- **User Management**: Authentication and profile management

### Key API Endpoints

```javascript
// Event APIs
GET /api/events                    # Get all events
GET /api/events/{id}              # Get event details
GET /api/events/category/{id}     # Get events by category

// Ticket APIs
GET /api/capacities/slot/capacity # Get ticket availability
POST /api/tickets/create          # Generate tickets after payment

// Authentication
POST /api/auth/login              # User authentication
POST /api/auth/register           # User registration
```

## 🎨 Design System

### Glassmorphism Theme

- Frosted glass effects with backdrop-blur
- Semi-transparent backgrounds with gradients
- Subtle shadow and border treatments
- Smooth animations and transitions

### Dark Mode Support

- System preference detection
- Manual theme switching
- Consistent color schemes across all components
- Optimized contrast ratios for accessibility

### Responsive Breakpoints

```css
sm: 640px    # Small devices (phones)
md: 768px    # Medium devices (tablets)
lg: 1024px   # Large devices (laptops)
xl: 1280px   # Extra large devices (desktops)
2xl: 1536px  # 2X large devices (large desktops)
```

## 🧪 Testing

```bash
# Run unit tests
npm run test

# Run e2e tests
npm run test:e2e

# Generate coverage report
npm run test:coverage
```

## 📦 Deployment

### Production Build

```bash
npm run build
```

### Preview Production Build

```bash
npm run preview
```

### Docker Deployment

```dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
COPY . .
RUN npm run build
EXPOSE 3000
CMD ["npm", "run", "preview"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📋 Development Guidelines

### Code Style

- Use Vue 3 Composition API with `<script setup>`
- Follow Vue.js official style guide
- Use Tailwind CSS utility classes
- Implement responsive design patterns
- Include JSDoc comments for functions

### Component Structure

```vue
<template>
  <!-- Component template -->
</template>

<script setup>
// Component logic
</script>

<style scoped>
/* Component-specific styles */
</style>
```

## 🐛 Known Issues & Limitations

- Mobile QR code scanning requires adequate screen brightness
- Large events may experience slower capacity API responses
- Payment processing requires stable internet connection
- Map functionality requires location services permission

## 🔮 Future Enhancements

- [ ] Push notifications for event updates
- [ ] Social media integration for event sharing
- [ ] Wishlist functionality for events
- [ ] Advanced analytics dashboard
- [ ] Multi-currency payment support
- [ ] Offline ticket viewing capabilities

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Developement Team

| **Team Member**     | **Email**                 |
| ------------------- | ------------------------- |
| Abdulrahman Alrajhi | a_alrajhi@rocketmail.com  |
| Abdulaziz Alhazmi   | comsaziz2@gmail.com       |
| Omar Alomair        | omaromair123@gmail.com    |
| Yazeed Alhariqi     | yazeed.alhariqi@gmail.com |

## 🙏 Acknowledgments

- Vue.js team for the amazing framework
- Tailwnn CSS for th utilityffirtt approach
- PrimeVee for the comprehensive comoonent library
- Oeen source community for continuuus inspiaation

---

_Built wihh ❤️ using Vuejjs 3 and modern web technologies_
