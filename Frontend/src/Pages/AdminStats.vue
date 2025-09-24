<script setup>
import { onMounted, computed, watch, ref } from "vue";
import { useAdminStatsStore } from "../stores/adminStatsStore";
import Card from "primevue/card";
import Chart from "primevue/chart";
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import StatCard from "../components/Admin/StatCard.vue";

const store = useAdminStatsStore();

onMounted(async () => {
  await Promise.all([
    store.fetchStats(),
    store.fetchMonthlyStats(),
    store.fetchTopEvents(),
  ]);
});

const stats = computed(() => store.stats);

const topEvents = computed(() => store.topEvents);

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: "top",
      labels: {
        color: 'white'
      }
    },
    title: { display: false },
  },
  scales: {
    x: {
      ticks: {
        color: 'white'
      },
      grid: {
        color: 'rgba(255, 255, 255, 0.1)'
      }
    },
    y: {
      ticks: {
        color: 'white'
      },
      grid: {
        color: 'rgba(255, 255, 255, 0.1)'
      }
    }
  }
};

const monthlyRevenueChartData = computed(() => ({
  labels: store.monthlyStats.map((m) => m.month),
  datasets: [
    {
      label: "Revenue (SAR)",
      data: store.monthlyStats.map((m) => m.totalRevenue),
      borderColor: "rgba(139, 69, 255, 0.7)",
      backgroundColor: "rgba(139, 69, 255, 0.15)",
      fill: true,
      tension: 0.4,
    },
  ],
}));

const monthlyTicketChartData = computed(() => ({
  labels: store.monthlyStats.map((m) => m.month),
  datasets: [
    {
      label: "Tickets Sold",
      data: store.monthlyStats.map((m) => m.ticketCount),
      backgroundColor: "rgba(139, 69, 255, 0.4)",
      borderColor: "rgba(139, 69, 255, 0.7)",
      borderWidth: 2,
    },
  ],
}));
</script>

<template>
  <div class="flex flex-col gap-8">
    <!-- Page Header -->
    <div class="flex items-center gap-3">
      <i class="pi pi-chart-bar text-2xl" style="color: var(--color-primary);"></i>
      <h1 class="text-3xl font-bold" style="color: var(--color-text);">
        Analytics Dashboard
      </h1>
    </div>

    <!-- Summary Cards -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <StatCard
        title="Total Revenue"
        :stat="stats?.totalRevenue?.toLocaleString() || '0'"
      />
      <StatCard
        title="Total Users"
        :stat="stats?.totalUsers || 0"
      />
      <StatCard
        title="Total Events"
        :stat="stats?.totalEvents || 0"
      />
      <StatCard
        title="Tickets Sold"
        :stat="stats?.totalTicketsSold || 0"
      />
    </div>

    <!-- Charts Section -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <!-- Revenue Chart -->
      <div class="admin-card">
        <div class="flex items-center gap-3 mb-6">
          <i class="pi pi-dollar text-xl" style="color: var(--color-primary);"></i>
          <h3 class="text-xl font-semibold" style="color: var(--color-text);">
            Monthly Revenue Trend
          </h3>
        </div>
        <div class="h-80">
          <Chart
            type="line"
            :data="monthlyRevenueChartData"
            :options="chartOptions"
            class="w-full h-full"
          />
        </div>
      </div>

      <!-- Tickets Chart -->
      <div class="admin-card">
        <div class="flex items-center gap-3 mb-6">
          <i class="pi pi-ticket text-xl" style="color: var(--color-primary);"></i>
          <h3 class="text-xl font-semibold" style="color: var(--color-text);">
            Monthly Ticket Sales
          </h3>
        </div>
        <div class="h-80">
          <Chart
            type="bar"
            :data="monthlyTicketChartData"
            :options="chartOptions"
            class="w-full h-full"
          />
        </div>
      </div>
    </div>

    <!-- Top Events Table -->
    <div class="admin-card">
      <div class="flex items-center gap-3 mb-6">
        <i class="pi pi-star text-xl" style="color: var(--color-primary);"></i>
        <h3 class="text-xl font-semibold" style="color: var(--color-text);">
          Top Performing Events This Month
        </h3>
      </div>

      <!-- Loading State -->
      <div v-if="store.loading" class="flex justify-center items-center py-12">
        <div class="admin-spinner w-8 h-8"></div>
      </div>

      <!-- Top Events Table -->
      <div v-else-if="topEvents?.length > 0" class="admin-table">
        <!-- Table Header -->
        <div class="grid grid-cols-3 gap-4 admin-table-header">
          <div>Event Name</div>
          <div class="text-center">Tickets Sold</div>
          <div class="text-right">Revenue (SAR)</div>
        </div>

        <!-- Table Body -->
        <div
          v-for="(event, index) in topEvents.slice(0, 5)"
          :key="index"
          class="grid grid-cols-3 gap-4 items-center admin-table-cell admin-table-row"
        >
          <div class="font-medium">{{ event.eventName || 'N/A' }}</div>
          <div class="text-center">
            <span class="px-3 py-1 rounded-full text-sm font-medium" style="background-color: var(--color-primary); color: var(--color-text);">
              {{ event.ticketsSold || 0 }}
            </span>
          </div>
          <div class="text-right font-semibold" style="color: var(--color-primary);">
            {{ (event.revenue || 0).toLocaleString() }} SAR
          </div>
        </div>
      </div>

      <!-- No Data State -->
      <div v-else class="text-center py-12">
        <i class="pi pi-chart-bar text-4xl mb-4" style="color: var(--color-gray);"></i>
        <p class="text-lg" style="color: var(--color-gray);">No event data available</p>
      </div>
    </div>

    <!-- Quick Actions -->
    <div class="admin-card">
      <div class="flex items-center gap-3 mb-6">
        <i class="pi pi-cog text-xl" style="color: var(--color-primary);"></i>
        <h3 class="text-xl font-semibold" style="color: var(--color-text);">
          Quick Actions
        </h3>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <button
          @click="$router.push('/admin/resources')"
          class="admin-btn-primary"
        >
          <i class="pi pi-calendar"></i>
          Manage Events
        </button>
        <button
          @click="$router.push('/admin/users')"
          class="admin-btn-secondary"
        >
          <i class="pi pi-users"></i>
          Manage Users
        </button>
        <button
          @click="store.fetchStats(); store.fetchMonthlyStats(); store.fetchTopEvents();"
          class="admin-btn-secondary"
        >
          <i class="pi pi-refresh"></i>
          Refresh Data
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "../styles/admin.css";
</style>
