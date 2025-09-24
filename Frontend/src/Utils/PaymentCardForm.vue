<!-- PaymentCardVisa.vue -->
<template>
  <div class="w-full">
    <!-- Form Fields -->
    <form class="space-y-4">
      <div class="grid grid-cols-2 gap-4">
        <!-- Full Name -->
        <div :class="`col-span-2 ${barSize}`">
          <label
            class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2"
          >
            Cardholder Name *
          </label>
          <div class="relative group">
            <input
              v-model="formData.fullName"
              type="text"
              class="w-full px-4 py-3 bg-white dark:bg-gray-800 border-2 border-gray-200 dark:border-gray-700 rounded-xl focus:border-blue-500 focus:ring-4 focus:ring-blue-500/20 transition-all duration-300 text-gray-900 dark:text-white placeholder-gray-400 group-hover:border-gray-300 dark:group-hover:border-gray-600"
              placeholder="Enter your full name"
              required
            />
          </div>
        </div>

        <!-- Card Number -->
        <div :class="`col-span-2 ${barSize}`">
          <label
            class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2"
          >
            Card Number *
          </label>
          <div class="relative group">
            <input
              v-model="formData.cardNumber"
              @input="formatCardNumber"
              type="text"
              class="w-full px-4 py-3 bg-white dark:bg-gray-800 border-2 border-gray-200 dark:border-gray-700 rounded-xl focus:border-blue-500 focus:ring-4 focus:ring-blue-500/20 transition-all duration-300 text-gray-900 dark:text-white placeholder-gray-400 font-mono tracking-wider group-hover:border-gray-300 dark:group-hover:border-gray-600"
              placeholder="1234 5678 9012 3456"
              maxlength="19"
              required
            />
          </div>
        </div>

        <!-- Expiry Date -->
        <div :class="`col-span-1 ${barSize}`">
          <label
            class="block text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2"
          >
            Expiry Date *
          </label>
          <div class="relative group">
            <input
              v-model="formData.expiry"
              @input="formatExpiry"
              type="text"
              class="w-full px-4 py-3 pl-12 bg-white dark:bg-gray-800 border-2 border-gray-200 dark:border-gray-700 rounded-xl focus:border-blue-500 focus:ring-4 focus:ring-blue-500/20 transition-all duration-300 text-gray-900 dark:text-white placeholder-gray-400 font-mono group-hover:border-gray-300 dark:group-hover:border-gray-600"
              placeholder="MM/YY"
              maxlength="5"
              required
            />
          </div>
        </div>

        <!-- CVV -->
        <div :class="`col-span-1 ${barSize}`">
          <label
            class="flex items-center gap-2 text-sm font-semibold text-gray-700 dark:text-gray-300 mb-2"
          >
            CVV *
            <button
              type="button"
              @mouseenter="showCvvTooltip = true"
              @mouseleave="showCvvTooltip = false"
              class="relative text-gray-400 hover:text-blue-500 transition-colors"
            ></button>
          </label>
          <div class="relative group">
            <input
              v-model="formData.cvv"
              @input="formatCvv"
              type="text"
              class="w-full px-4 py-3 bg-white dark:bg-gray-800 border-2 border-gray-200 dark:border-gray-700 rounded-xl focus:border-blue-500 focus:ring-4 focus:ring-blue-500/20 transition-all duration-300 text-gray-900 dark:text-white placeholder-gray-400 font-mono group-hover:border-gray-300 dark:group-hover:border-gray-600"
              placeholder="123"
              maxlength="3"
              required
            />
          </div>
        </div>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";

const showCvvTooltip = ref(false);
const barSize = "w-[16rem]";

const formData = reactive({
  fullName: "",
  cardNumber: "",
  expiry: "",
  cvv: "",
});

const formatCardNumber = (event) => {
  let value = event.target.value.replace(/\s/g, "").replace(/[^0-9]/gi, "");
  let formattedValue = value.match(/.{1,4}/g)?.join(" ") || value;
  if (formattedValue.length > 19) formattedValue = formattedValue.substr(0, 19);
  formData.cardNumber = formattedValue;
};

const formatExpiry = (event) => {
  let value = event.target.value.replace(/\D/g, "");
  let formattedValue = value.replace(/(\d{2})(\d)/, "$1/$2");
  if (formattedValue.length > 5) formattedValue = formattedValue.substr(0, 5);
  formData.expiry = formattedValue;
};

const formatCvv = (event) => {
  let value = event.target.value.replace(/\D/g, "");
  formData.cvv = value.substr(0, 3);
};
</script>
