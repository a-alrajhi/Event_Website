<template>
  <div class="min-h-screen bg-white">
    <!-- Navbar -->
    <header class="flex justify-between items-center px-8 py-4 bg-white shadow">
      <div class="text-2xl font-bold text-teal-700">CROVILLAS</div>
      <nav class="flex items-center gap-6 text-gray-600">
        <input
            type="text"
            placeholder="Search for villas & locations"
            class="border rounded px-3 py-2 w-80 focus:ring-2 focus:ring-teal-600"
        />
        <a href="#" class="hover:text-teal-700">FAQ</a>
        <a href="#" class="hover:text-teal-700">Get Advice</a>
        <a href="#" class="hover:text-teal-700">English</a>
        <button class="md:hidden border p-2 rounded">☰</button>
      </nav>
    </header>

    <!-- Hero Section -->
    <section
        class="relative bg-cover bg-center h-[500px] flex items-center"
        style="background-image: url('https://source.unsplash.com/1600x900/?villa,pool');"
    >
      <div class="absolute inset-0 bg-black/40"></div>
      <div class="relative z-10 px-12 text-white max-w-3xl">
        <h1 class="text-4xl md:text-5xl font-bold mb-4">
          Rent exclusive luxury villas in Croatia – with pool & directly by the sea
        </h1>
        <!-- Search Bar -->
        <div class="flex gap-2 bg-white rounded-lg shadow p-2 w-fit mt-6">
          <input
              type="text"
              placeholder="Arrival & departure"
              class="px-3 py-2 border rounded w-56"
          />
          <input
              type="text"
              placeholder="1 guest"
              class="px-3 py-2 border rounded w-32"
          />
          <button
              class="bg-orange-600 text-white px-6 py-2 rounded hover:bg-orange-700"
          >
            Search holiday villas
          </button>
        </div>
      </div>
    </section>

    <!-- Features -->
    <section class="flex justify-center gap-16 py-10 text-center">
      <div>
        <div class="text-3xl">✔️</div>
        <h3 class="font-semibold">Certified & Verified</h3>
        <p class="text-gray-500 text-sm w-64">
          900+ handpicked holiday homes exclusively bookable – no hidden costs.
        </p>
      </div>
      <div>
        <div class="text-3xl">🎧</div>
        <h3 class="font-semibold">Carefree vacation</h3>
        <p class="text-gray-500 text-sm w-64">
          Personal service with a contact person for a worry-free stay.
        </p>
      </div>
      <div>
        <div class="text-3xl">⭐</div>
        <h3 class="font-semibold">Customer reviews</h3>
        <p class="text-gray-500 text-sm w-64">600+ reviews | 4.9/5.0</p>
      </div>
    </section>

    <!-- Best offers -->
    <section class="px-12 py-10 bg-gray-50">
      <h2 class="text-xl font-bold mb-6">Best offers</h2>
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6">
        <div
            v-for="(villa, index) in villas"
            :key="index"
            class="bg-white rounded-lg shadow overflow-hidden"
        >
          <img :src="villa.image" alt="villa" class="w-full h-40 object-cover" />
          <div class="p-4">
            <p class="text-sm text-gray-500">{{ villa.price }}</p>
            <h3 class="font-semibold text-lg">{{ villa.name }}</h3>
            <ul class="text-sm text-gray-600 mt-2 space-y-1">
              <li v-for="(f, i) in villa.features" :key="i">• {{ f }}</li>
            </ul>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
const villas = [
  {
    name: "Villa Stanzia Butori",
    price: "from €6,650 / week",
    image: "https://source.unsplash.com/400x300/?villa",
    features: ["Luxury designer villa", "Heated pool", "Absolute privacy"],
  },
  {
    name: "Villa Monaco",
    price: "from €3,430 / week",
    image: "https://source.unsplash.com/400x300/?house,pool",
    features: ["Private location", "Paradise garden", "Exclusive equipment"],
  },
  {
    name: "Villa Complex Kamenjak",
    price: "from €3,976 / week",
    image: "https://source.unsplash.com/400x300/?resort",
    features: ["Complex of 2 villas", "Modern", "2 pools"],
  },
  {
    name: "Villa Maslina",
    price: "from €1,960 / week",
    image: "https://source.unsplash.com/400x300/?sea,villa",
    features: ["Fantastic sea view", "Quiet location", "Outdoor area"],
  },
];
</script>
