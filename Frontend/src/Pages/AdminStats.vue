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
  plugins: {
    legend: { position: "top" },
    title: { display: false },
  },
};

const monthlyRevenueChartData = computed(() => ({
  labels: store.monthlyStats.map((m) => m.month),
  datasets: [
    {
      label: "Revenue (SAR)",
      data: store.monthlyStats.map((m) => m.totalRevenue),
      borderColor: "#42A5F5",
      backgroundColor: "#90CAF9",
      fill: true,
    },
  ],
}));

const monthlyTicketChartData = computed(() => ({
  labels: store.monthlyStats.map((m) => m.month),
  datasets: [
    {
      label: "Tickets Sold",
      data: store.monthlyStats.map((m) => m.ticketCount),
      backgroundColor: "#66BB6A",
    },
  ],
}));
</script>

<template>
  <div class="p-4 flex flex-col gap-3">
    <!-- Section 1: Summary Cards -->
    <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-5 gap-4 mb-6">
      <StatCard
        title="Revenue"
        :stat="`${stats?.totalRevenue?.toLocaleString() || 0} SAR`"
      />
      <StatCard title="Users" :stat="`${stats?.totalUsers || 0}`" />
      <StatCard title="Events" :stat="`${stats?.totalEvents || 0}`" />
      <StatCard
        title="Tickets Sold"
        :stat="`${stats?.totalTicketsSold || 0}`"
      />
      <StatCard
        title="Check-in Rate"
        :stat="`${(stats?.overallCheckInRate * 100 || 0).toFixed(1)}%`"
      />
    </div>

    <!-- Section 2: Charts -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-2">
      <Chart
        type="line"
        :data="monthlyRevenueChartData"
        :options="chartOptions"
        class="min-w-lg"
      />
      <Chart
        type="bar"
        :data="monthlyTicketChartData"
        :options="chartOptions"
        class="min-w-lg"
      />
    </div>

    <!-- Section 3: Top Events This Month -->
    <div class="flex flex-col gap-3">
      <h2 class="text-2xl text-black font-semibold">Top Events This Month</h2>
      <DataTable :value="topEvents" :rows="5">
        <Column field="eventName" header="Event" />
        <Column field="ticketsSold" header="Tickets Sold" />
        <Column field="revenue" header="Revenue" />
      </DataTable>
    </div>
  </div>
</template>
