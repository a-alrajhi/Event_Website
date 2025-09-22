<!--
Event Rules Component
@author: Abdulrahman Al Rajhi
@since: 9/15/2025
@version: 1.1 (simplified)
-->
<template>
  <div class="bg-white rounded-xl shadow p-8 mt-6 space-y-8">
    <!-- Terms & Conditions -->
    <section>
      <h2 class="text-xl font-semibold text-gray-800 mb-2">
        Terms & Conditions
      </h2>
      <p class="text-gray-600">
        Entry requires a valid ticket. Organizer may refuse admission. All
        attendees must follow venue rules. Photography/recording may be
        restricted at times.
      </p>
    </section>

    <!-- Refund Policy -->
    <section>
      <h2 class="text-xl font-semibold text-gray-800 mb-2">
        Refund & Cancellation
      </h2>
      <p class="text-gray-600">
        Tickets are non-refundable unless the event is cancelled. In case of
        cancellation, refunds will be issued within 5–7 business days.
      </p>
    </section>

    <!-- Extra Info -->
    <section>
      <h2 class="text-xl font-semibold text-gray-800 mb-2">Good to Know</h2>
      <ul class="list-disc list-inside text-gray-600 space-y-1">
        <li>Gates open 30 minutes before start time</li>
        <li>Free on-site parking available</li>
        <li>Valid photo ID required at entry</li>
        <li>No outside food or drinks allowed</li>
        <li>Lost tickets cannot be replaced</li>
        <li>Event may be photographed for promotions</li>
      </ul>
    </section>

    <!-- Contact -->
    <section class="pt-4 border-t border-gray-200">
      <h2 class="text-xl font-semibold text-gray-800 mb-2">Need Help?</h2>
      <p class="text-gray-600">
        Contact us at
        <a href="tel:+966123456789" class="text-blue-600 hover:underline">
          +966 12 345 6789
        </a>
        or
        <a
          href="mailto:support@events.sa"
          class="text-blue-600 hover:underline"
        >
          support@events.sa
        </a>
      </p>
    </section>
  </div>
</template>

<script setup>
// Still static – no props yet, but easy to extend later
</script>
