<template>
  <div class="w-full">
    <!-- Credit Card -->
    <PaymentMethods
      name="payment-method"
      value="credit-card"
      label="Credit Card"
      v-model="selectedPaymentMethod"
      :index="0"
    >
      <template #logo>
        <img class="h-6 w-auto" src="../assets/Visa_Logo.svg" alt="Visa" />
        <img
          class="h-6 w-auto"
          src="../assets/Mastercard_Logo.svg"
          alt="Mastercard"
        />
      </template>
      <PaymentCardForm />
    </PaymentMethods>

    <!-- Mada -->
    <PaymentMethods
      name="payment-method"
      value="mada"
      label="Mada"
      v-model="selectedPaymentMethod"
      :index="1"
    >
      <template #logo>
        <img class="h-8 w-auto" src="../assets/Mada_Logo.svg" alt="Mada" />
      </template>
      <PaymentCardForm />
    </PaymentMethods>

    <!-- STC PAY -->
    <PaymentMethods
      name="payment-method"
      value="stc-pay"
      label="STC Pay"
      v-model="selectedPaymentMethod"
      :index="2"
    >
      <template #logo>
        <img
          class="h-14 w-auto"
          src="../assets/STCPAY_Logo.svg"
          alt="STC Pay"
        />
      </template>
      <p class="text-gray-600 dark:text-gray-400 text-center">
        You'll be redirected to complete your payment with STC Pay
      </p>
    </PaymentMethods>

    <!-- Apple Pay -->
    <PaymentMethods
      name="payment-method"
      value="apple-pay"
      label="Apple Pay"
      v-model="selectedPaymentMethod"
      :index="3"
    >
      <template #logo>
        <img
          class="h-9 w-auto"
          src="../assets/Apple_Pay_Logo.svg"
          alt="Apple Pay"
        />
      </template>
      <p class="text-gray-600 dark:text-gray-400 text-center">
        You'll be redirected to complete your payment with Apple Pay
      </p>
    </PaymentMethods>
  </div>
</template>

<script setup>
import { computed } from "vue";
import PaymentMethods from "./PaymentMethods.vue";
import PaymentCardForm from "./PaymentCardForm.vue";

const selectedPaymentMethod = computed(() => {});

const selectedPaymentMethod = ref("credit-card");
</script>
