<script setup>
import { useI18n } from "vue-i18n";
import { watch } from "vue";

const { locale } = useI18n();
watch(
  locale,
  (newLocale) => {
    if (newLocale === "ar") {
      document.documentElement.setAttribute("dir", "rtl");
    } else {
      document.documentElement.setAttribute("dir", "ltr");
    }
  },
  { immediate: true }
);
</script>

<template>
  <div class="min-h-screen flex flex-col">
    <main class="flex-grow">
      <router-view />
    </main>
  </div>
</template>
