<!-- Total Payment Summary -->
<template>
  <div class="mt-6 grow sm:mt-8 lg:mt-0">
    <!-- Summary Card -->
    <div
      class="space-y-4 rounded-xl border border-gray-200 bg-white shadow-md p-6 my-6 dark:border-gray-700 dark:bg-gray-800 dark:shadow-lg"
    >
      <div class="space-y-3">
        <!-- Discount Code Input -->
        <div class="flex">
          <input
            v-model="discountCode"
            type="text"
            placeholder="Discount code"
            class="flex-1 rounded-l-lg border border-gray-300 px-4 py-2 text-sm shadow-sm focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
          />

          <button
            @click="applyDiscount"
            class="rounded-r-lg bg-indigo-600 px-6 py-2 text-sm font-semibold text-white shadow hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-1 dark:bg-indigo-500 dark:hover:bg-indigo-600 dark:focus:ring-offset-gray-800 transition-all cursor-pointer"
          >
            Apply
          </button>
        </div>

        <!-- Feedback Message -->
        <p
          v-if="discountMessage"
          class="text-sm mt-1 font-medium"
          :class="
            isDiscountApplied
              ? 'text-green-600 dark:text-green-400'
              : 'text-red-600 dark:text-red-400'
          "
        >
          {{ discountMessage }}
        </p>

        <!-- Payment Breakdown -->
        <dl class="flex items-center justify-between">
          <dt class="text-gray-500 dark:text-gray-400">Tickets</dt>
          <dd class="font-medium text-gray-900 dark:text-white">
            {{ formatCurrency(paymentStore.totalAmount) }}
          </dd>
        </dl>

        <dl class="flex items-center justify-between">
          <dt class="text-gray-500 dark:text-gray-400">Discount</dt>
          <dd
            :class="
              isDiscountApplied
                ? 'text-green-500 font-medium'
                : 'text-gray-900 dark:text-white font-medium'
            "
          >
            {{ isDiscountApplied ? "-" : ""
            }}{{ formatCurrency(discountValue) }}
          </dd>
        </dl>

        <dl class="flex items-center justify-between">
          <dt class="text-gray-500 dark:text-gray-400">Total excl. VAT</dt>
          <dd class="font-medium text-gray-900 dark:text-white">
            {{ formatCurrency(paymentStore.totalAmount - discountValue) }}
          </dd>
        </dl>

        <dl class="flex items-center justify-between">
          <dt class="text-gray-500 dark:text-gray-400">VAT (15%)</dt>
          <dd class="font-medium text-gray-900 dark:text-white">
            {{ formatCurrency(vat) }}
          </dd>
        </dl>
      </div>

      <!-- Final Total -->
      <dl
        class="flex items-center justify-between border-t border-gray-200 pt-3 dark:border-gray-700"
      >
        <dt class="text-lg font-bold text-gray-900 dark:text-white">
          Total incl. VAT
        </dt>
        <dd class="text-lg font-bold text-indigo-600 dark:text-indigo-400">
          {{ formatCurrency(paymentStore.totalAmount - discountValue + vat) }}
        </dd>
      </dl>
    </div>

    <!-- old payment button  -->
    <!-- 
            class="w-full rounded-lg bg-indigo-600 px-6 py-3 text-base font-semibold text-white shadow-lg hover:bg-indigo-700 focus:outline-none focus:ring-4 focus:ring-indigo-300 dark:bg-indigo-500 dark:hover:bg-indigo-600 dark:focus:ring-indigo-800 transition-all cursor-pointer" -->

    <!-- Footer Button -->
    <div id="payment-footer" class="mt-4">
      <button
        @click="goToConfirmationPage"
        type="submit"
        :class="applyButtonStyle"
      >
        {{ buttonText }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import { usePaymentStore } from "../stores/paymentStore";
import { useRouter } from "vue-router";

const paymentStore = usePaymentStore();
const router = useRouter();

const baseClasses =
  "w-full rounded-xl px-8 py-4 text-lg font-bold shadow-xl focus:outline-none focus:ring-4 transition-all duration-300 cursor-pointer transform hover:scale-[1.02] active:scale-[0.98]";

// button matching each payment method's branding
const applyButtonStyle = computed(() => {
  switch (paymentStore.selectedPaymentMethod) {
    case "credit-card":
      return `${baseClasses} bg-gradient-to-r from-blue-600 to-blue-700 hover:from-blue-700 hover:to-blue-800 text-white focus:ring-blue-300 shadow-blue-500/25 dark:from-blue-500 dark:to-blue-600 dark:hover:from-blue-600 dark:hover:to-blue-700 dark:focus:ring-blue-800`;

    case "mada":
      return `${baseClasses} bg-gradient-to-r from-emerald-600 to-green-600 hover:from-emerald-700 hover:to-green-700 text-white focus:ring-emerald-300 shadow-emerald-500/25 dark:from-emerald-500 dark:to-green-500 dark:hover:from-emerald-600 dark:hover:to-green-600 dark:focus:ring-emerald-800`;

    case "apple-pay":
      return `${baseClasses} bg-gradient-to-r from-gray-900 to-black hover:from-black hover:to-gray-800 text-white focus:ring-gray-400 shadow-gray-900/50 dark:from-gray-800 dark:to-gray-900 dark:hover:from-gray-700 dark:hover:to-gray-800 dark:focus:ring-gray-600`;

    case "stc-pay":
      return `${baseClasses} bg-gradient-to-r from-purple-600 to-violet-600 hover:from-purple-700 hover:to-violet-700 text-white focus:ring-purple-300 shadow-purple-500/25 dark:from-purple-500 dark:to-violet-500 dark:hover:from-purple-600 dark:hover:to-violet-600 dark:focus:ring-purple-800`;

    default:
      return `${baseClasses} bg-gradient-to-r from-indigo-600 to-purple-600 hover:from-indigo-700 hover:to-purple-700 text-white focus:ring-indigo-300 shadow-indigo-500/25 dark:from-indigo-500 dark:to-purple-500 dark:hover:from-indigo-600 dark:hover:to-purple-600 dark:focus:ring-indigo-800`;
  }
});

// Dynamic button text based on payment method
const buttonText = computed(() => {
  switch (paymentStore.selectedPaymentMethod) {
    case "credit-card":
      return "Pay with Card";
    case "mada":
      return "Pay with Mada";
    case "apple-pay":
      return "Pay with Apple Pay";
    case "stc-pay":
      return "Pay with STC Pay";
    default:
      return "Pay Now";
  }
});

const discountCode = ref("");
const discountValue = ref(0);
const discountMessage = ref("");
const isDiscountApplied = ref(false);

function applyDiscount() {
  if (discountCode.value.trim().toUpperCase() === "SAVE10") {
    discountValue.value = paymentStore.totalAmount * 0.1; // 10% discount
    discountMessage.value = "Discount applied successfully!";
    isDiscountApplied.value = true;
  } else {
    discountValue.value = 0;
    discountMessage.value = "Invalid discount code.";
    isDiscountApplied.value = false;
  }
}

const vat = computed(() => {
  return (paymentStore.totalAmount - discountValue.value) * 0.15;
});

function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "SAR",
  }).format(amount);
}

function goToConfirmationPage() {
  router.push({
    name: "EventConfirmation",
    params: { eventId: paymentStore.eventId },
  });
}
</script>
