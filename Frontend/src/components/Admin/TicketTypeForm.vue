<script setup>
import { FloatLabel, InputText, InputNumber } from "primevue";
import { watch, ref, onMounted } from "vue";
import { useCreateTTStore } from "../../stores/createTTStore";

const ttName = ref("");
const ttPrice = ref(0);
const createTTStore = useCreateTTStore();

onMounted(() => {
  if (createTTStore.isEditMode) {
    ttName.value = createTTStore.name;
    ttPrice.value = createTTStore.price;
  }
});

watch([ttName, ttPrice], ([name, price]) => {
  createTTStore.price = price;
  createTTStore.name = name;
  createTTStore.isAllowedNext = name.trim().length > 1 && price > 0;
});
</script>

<template>
  <div class="flex flex-col gap-3">
    <span class="text-primary/90 block">Create Ticket Type</span>
    <div class="flex flex-col gap-3 w-full">
      <div class="flex w-full gap-3">
        <FloatLabel variant="on" class="flex-1">
          <InputText
            id="tt-name"
            v-model="ttName"
            autocomplete="off"
            class="w-full"
          />
          <label for="tt-name">Ticket Type Name</label>
        </FloatLabel>

        <FloatLabel variant="on" class="flex-1">
          <InputNumber
            v-model="ttPrice"
            inputId="tt-price"
            mode="currency"
            currency="SAR"
            class="w-full"
          />
          <label for="tt-price">Price in SAR</label>
        </FloatLabel>
      </div>
    </div>
  </div>
</template>
