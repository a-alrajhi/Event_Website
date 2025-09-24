<template>
  <div class="admin-stat-card group cursor-pointer">
    <div class="flex items-center justify-between mb-3">
      <h3
        class="text-sm font-medium uppercase tracking-wider opacity-90"
        style="color: var(--color-text);"
      >
        {{ props.title }}
      </h3>
      <div
        class="w-10 h-10 rounded-lg flex items-center justify-center transition-all duration-200 group-hover:scale-110"
        style="background-color: rgba(255, 255, 255, 0.2);"
      >
        <i
          :class="getIconForTitle(props.title)"
          class="text-lg"
          style="color: var(--color-text);"
        ></i>
      </div>
    </div>
    <div class="flex items-baseline gap-2">
      <p
        class="text-3xl font-bold"
        style="color: var(--color-text);"
      >
        {{ formatStat(props.stat) }}
      </p>
      <span
        class="text-sm opacity-75"
        style="color: var(--color-text);"
      >
        {{ getUnitForTitle(props.title) }}
      </span>
    </div>
    <div
      class="mt-3 text-xs opacity-75"
      style="color: var(--color-text);"
    >
      Updated just now
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  title: {
    required: true,
    type: String,
  },
  stat: {
    default: 0,
  },
});

const getIconForTitle = (title) => {
  const lowerTitle = title.toLowerCase();
  if (lowerTitle.includes('user')) return 'pi pi-users';
  if (lowerTitle.includes('event')) return 'pi pi-calendar';
  if (lowerTitle.includes('revenue') || lowerTitle.includes('earning')) return 'pi pi-dollar';
  if (lowerTitle.includes('booking') || lowerTitle.includes('ticket')) return 'pi pi-ticket';
  return 'pi pi-chart-bar';
};

const getUnitForTitle = (title) => {
  const lowerTitle = title.toLowerCase();
  if (lowerTitle.includes('revenue') || lowerTitle.includes('earning')) return 'SAR';
  if (lowerTitle.includes('rate') || lowerTitle.includes('percentage')) return '%';
  return '';
};

const formatStat = (stat) => {
  if (typeof stat === 'number' && stat >= 1000) {
    return (stat / 1000).toFixed(1) + 'K';
  }
  return stat;
};
</script>

<style scoped>
@import "../../styles/admin.css";
</style>
