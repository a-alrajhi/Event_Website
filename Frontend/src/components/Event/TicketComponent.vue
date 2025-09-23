<!--
Ticket Component
@author: Abdulrahman Al Rajhi
@since: 9/23/2025
@version: 1.0
-->
<template>
  <!-- Card Container -->
  <div class="max-w-5xl mx-auto mt-8 px-4 relative">
    <!-- Ticket Card -->
    <div
      class="relative bg-white/70 backdrop-blur-2xl rounded-3xl shadow-2xl hover:shadow-3xl transition-all duration-700 border border-white/30 overflow-hidden transform hover:-translate-y-2 hover:scale-[1.01] group"
      style="
        box-shadow:
          0 25px 50px -12px rgba(0, 0, 0, 0.15),
          0 0 0 1px rgba(255, 255, 255, 0.2) inset;
      "
    >
      <!-- Main Header -->
      <div class="relative overflow-hidden">
        <!-- Background color -->
        <div
          class="absolute inset-0 bg-gradient-to-r from-purple-700/90 via-violet-700/90 to-indigo-700/90 backdrop-blur-xl"
        ></div>

        <!-- small dots across header -->
        <div class="absolute inset-0 opacity-100">
          <div
            class="absolute inset-0"
            style="
              background-image:
                radial-gradient(
                  circle at 25px 25px,
                  rgba(255, 255, 255, 0.3) 2px,
                  transparent 0
                ),
                radial-gradient(
                  circle at 75px 75px,
                  rgba(255, 255, 255, 0.2) 2px,
                  transparent 0
                );
              background-size: 100px 100px;
              animation: float 6s ease-in-out infinite;
            "
          ></div>
        </div>
        <!-- Floating Orbs -->
        <div class="absolute inset-0">
          <div
            class="absolute top-4 left-8 w-3 h-3 bg-white/30 rounded-full animate-bounce delay-300 shadow-lg"
          ></div>
          <div
            class="absolute bottom-6 left-20 w-2.5 h-2.5 bg-white/25 rounded-full animate-bounce delay-500 shadow-md"
          ></div>
        </div>

        <div class="relative z-10 px-8 py-6">
          <!--ticket content header section -->
          <div class="flex items-center justify-between">
            <!-- ticket header box -->
            <div class="flex items-center space-x-4">
              <!-- Ticket Icon -->
              <div class="relative">
                <div
                  class="absolute inset-0 bg-white/20 rounded-xl blur-sm"
                ></div>
                <div
                  class="relative bg-white/15 backdrop-blur-md rounded-xl p-3 border border-white/30 shadow-lg"
                >
                  <svg
                    class="w-6 h-6 text-white drop-shadow-lg"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z"
                    ></path>
                  </svg>
                </div>
              </div>

              <!-- Ticket Name and Access -->
              <div>
                <h3
                  class="text-xl font-black text-white drop-shadow-lg tracking-tight"
                >
                  {{ ticketType?.name || "Premium Event Ticket" }}
                </h3>
                <p class="text-white/80 text-sm font-medium">
                  Exclusive Access
                </p>
              </div>
            </div>

            <!-- Ticket code display -->
            <div class="relative">
              <div
                class="absolute inset-0 bg-white/15 rounded-xl blur-sm"
              ></div>
              <div
                class="relative bg-white/20 backdrop-blur-md rounded-xl px-4 py-2 border border-white/40 shadow-lg"
              >
                <div class="flex items-center space-x-2">
                  <div
                    class="w-2 h-2 bg-green-400 rounded-full animate-pulse"
                  ></div>
                  <span class="text-white font-mono font-bold tracking-wider">{{
                    ticket.ticketCode
                  }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Card Body -->
      <div class="p-10 relative">
        <!-- Background Gradient Overlay -->
        <div
          class="absolute inset-0 bg-gradient-to-br from-blue-500/10 via-transparent to-purple/5 pointer-events-none"
        ></div>

        <!-- Card Content Section -->
        <div
          class="flex flex-col xl:flex-row items-center gap-10 relative z-10"
        >
          <!-- QR Code Section -->
          <div class="flex-shrink-0 text-center relative">
            <!-- QR Code Glow Effect -->
            <div
              class="absolute inset-0 bg-gradient-to-r from-blue-500/20 to-purple-500/20 rounded-3xl blur-2xl"
            ></div>

            <!-- QR Code Container -->
            <div
              class="relative bg-white/40 backdrop-blur-xl rounded-3xl p-8 border border-white/30 shadow-2xl"
              style="
                background: linear-gradient(
                  135deg,
                  rgba(255, 255, 255, 0.4) 0%,
                  rgba(255, 255, 255, 0.1) 100%
                );
              "
            >
              <!-- Scanning Frame Animation -->
              <div
                class="absolute inset-4 border-2 border-blue-500/40 rounded-2xl"
              >
                <!-- top left corner -->
                <div
                  class="absolute top-0 left-0 w-6 h-6 border-t-4 border-l-4 border-blue-500 rounded-tl-lg"
                ></div>
                <!-- top right corner -->
                <div
                  class="absolute top-0 right-0 w-6 h-6 border-t-4 border-r-4 border-blue-500 rounded-tr-lg"
                ></div>
                <!-- bottom left corner -->
                <div
                  class="absolute bottom-0 left-0 w-6 h-6 border-b-4 border-l-4 border-blue-500 rounded-bl-lg"
                ></div>
                <!-- bottom right corner -->
                <div
                  class="absolute bottom-0 right-0 w-6 h-6 border-b-4 border-r-4 border-blue-500 rounded-br-lg"
                ></div>

                <!-- Scanning Line -->
                <div
                  class="absolute top-0 left-0 right-0 h-1 bg-gradient-to-r from-transparent via-blue-500 to-transparent animate-scan"
                ></div>
              </div>

              <VQrcode
                :value="ticket.ticketCode"
                :size="200"
                class="mx-auto relative z-10"
                :options="{
                  color: {
                    dark: '#1f2937',
                    light: '#ffffff',
                  },
                }"
              />
            </div>

            <!-- Scan at Entrance -->
            <div class="mt-6 space-y-2">
              <p class="text-lg font-bold text-gray-800">Scan at Entrance</p>
              <p class="text-sm text-gray-600">
                Keep this accessible on your device
              </p>
              <div
                class="flex items-center justify-center space-x-2 text-xs text-green-600"
              >
                <!-- scan indicator -->
                <div
                  class="w-2 h-2 bg-green-500 rounded-full animate-pulse"
                ></div>
                <span class="font-semibold">Ready to Scan</span>
              </div>
            </div>
          </div>

          <!-- Details Section -->
          <div class="flex-1 space-y-8 w-full">
            <!-- Info Grid -->
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <!-- Ticket Type Card -->
              <div class="relative group">
                <div
                  class="absolute inset-0 bg-gradient-to-r from-blue-500/20 to-cyan-500/20 rounded-2xl blur-xl group-hover:blur-2xl transition-all duration-300"
                ></div>
                <div
                  class="relative bg-white/30 backdrop-blur-xl rounded-2xl p-6 border border-white/40 shadow-lg hover:shadow-xl transition-all duration-300"
                  style="
                    background: linear-gradient(
                      135deg,
                      rgba(59, 130, 246, 0.1) 0%,
                      rgba(255, 255, 255, 0.2) 100%
                    );
                  "
                >
                  <div class="flex items-center mb-3">
                    <div
                      class="bg-blue-500/80 backdrop-blur-sm rounded-lg p-2 mr-3 shadow-lg"
                    >
                      <svg
                        class="w-5 h-5 text-white"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"
                        ></path>
                      </svg>
                    </div>
                    <label
                      class="text-sm font-black text-blue-900 uppercase tracking-wide"
                      >Ticket Type</label
                    >
                  </div>
                  <p class="text-lg font-black text-blue-950">
                    {{ ticketType?.name || "Premium" }}
                  </p>
                </div>
              </div>

              <!-- Price Card -->
              <div class="relative group">
                <div
                  class="absolute inset-0 bg-gradient-to-r from-emerald-500/20 to-green-500/20 rounded-2xl blur-xl group-hover:blur-2xl transition-all duration-300"
                ></div>
                <div
                  class="relative bg-white/30 backdrop-blur-xl rounded-2xl p-6 border border-white/40 shadow-lg hover:shadow-xl transition-all duration-300"
                  style="
                    background: linear-gradient(
                      135deg,
                      rgba(34, 197, 94, 0.1) 0%,
                      rgba(255, 255, 255, 0.2) 100%
                    );
                  "
                >
                  <div class="flex items-center mb-3">
                    <div
                      class="bg-emerald-500/80 backdrop-blur-sm rounded-lg p-2 mr-3 shadow-lg"
                    >
                      <svg
                        class="w-5 h-5 text-white"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"
                        ></path>
                      </svg>
                    </div>
                    <label
                      class="text-sm font-black text-emerald-900 uppercase tracking-wide"
                      >Price</label
                    >
                  </div>
                  <p class="text-lg font-black text-emerald-950">
                    {{ ticketType?.price || "N/A" }} SAR
                  </p>
                </div>
              </div>

              <!-- Ticket Code Card -->
              <div class="relative group">
                <div
                  class="absolute inset-0 bg-gradient-to-r from-purple-500/20 to-violet-500/20 rounded-2xl blur-xl group-hover:blur-2xl transition-all duration-300"
                ></div>
                <div
                  class="relative bg-white/30 backdrop-blur-xl rounded-2xl p-6 border border-white/40 shadow-lg hover:shadow-xl transition-all duration-300"
                  style="
                    background: linear-gradient(
                      135deg,
                      rgba(147, 51, 234, 0.1) 0%,
                      rgba(255, 255, 255, 0.2) 100%
                    );
                  "
                >
                  <div class="flex items-center mb-3">
                    <div
                      class="bg-purple-500/80 backdrop-blur-sm rounded-lg p-2 mr-3 shadow-lg"
                    >
                      <svg
                        class="w-5 h-5 text-white"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4"
                        ></path>
                      </svg>
                    </div>
                    <label
                      class="text-sm font-black text-purple-900 uppercase tracking-wide"
                      >Ticket Code</label
                    >
                  </div>
                  <p
                    class="text-lg font-mono font-black text-purple-950 break-all"
                  >
                    {{ ticket.ticketCode }}
                  </p>
                </div>
              </div>

              <!-- Status Card -->
              <div class="relative group">
                <div
                  class="absolute inset-0 bg-gradient-to-r from-green-500/20 to-emerald-500/20 rounded-2xl blur-xl group-hover:blur-2xl transition-all duration-300"
                ></div>
                <div
                  class="relative bg-white/30 backdrop-blur-xl rounded-2xl p-6 border border-white/40 shadow-lg hover:shadow-xl transition-all duration-300"
                  style="
                    background: linear-gradient(
                      135deg,
                      rgba(34, 197, 94, 0.1) 0%,
                      rgba(255, 255, 255, 0.2) 100%
                    );
                  "
                >
                  <div class="flex items-center mb-3">
                    <div
                      class="bg-green-500/80 backdrop-blur-sm rounded-lg p-2 mr-3 shadow-lg"
                    >
                      <svg
                        class="w-5 h-5 text-white"
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
                        ></path>
                      </svg>
                    </div>
                    <label
                      class="text-sm font-black text-green-900 uppercase tracking-wide"
                      >Status</label
                    >
                  </div>
                  <div class="flex items-center">
                    <div
                      class="w-3 h-3 bg-green-500 rounded-full mr-3 animate-pulse shadow-lg shadow-green-500/50"
                    ></div>
                    <span class="text-lg font-black text-green-950">Valid</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Premium Access Instructions -->
            <div class="relative group">
              <div
                class="absolute inset-0 bg-gradient-to-r from-amber-400/20 to-orange-400/20 rounded-2xl blur-xl"
              ></div>
              <div
                class="relative bg-white/25 backdrop-blur-xl border border-white/40 rounded-2xl p-6 shadow-lg"
                style="
                  background: linear-gradient(
                    135deg,
                    rgba(245, 158, 11, 0.1) 0%,
                    rgba(255, 255, 255, 0.2) 100%
                  );
                "
              >
                <div class="flex items-start">
                  <div
                    class="bg-gradient-to-r from-amber-500/80 to-orange-500/80 backdrop-blur-sm rounded-full p-3 mr-5 flex-shrink-0 shadow-lg"
                  >
                    <svg
                      class="w-5 h-5 text-white"
                      fill="currentColor"
                      viewBox="0 0 20 20"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z"
                        clip-rule="evenodd"
                      ></path>
                    </svg>
                  </div>
                  <div>
                    <h4 class="text-lg font-black text-amber-900 mb-2">
                      Premium Access Instructions
                    </h4>
                    <p class="text-amber-800 leading-relaxed">
                      Present this QR code at the venue entrance. Ensure your
                      device is charged and the screen brightness is adequate
                      for scanning. Screenshots are acceptable as backup.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- card footer -->
      <!-- main footer container -->
      <div
        class="relative bg-white/20 backdrop-blur-xl px-10 py-6 border-t border-white/30"
        style="
          background: linear-gradient(
            135deg,
            rgba(255, 255, 255, 0.2) 0%,
            rgba(255, 255, 255, 0.05) 100%
          );
        "
      >
        <!-- footer content container -->
        <div
          class="flex flex-col sm:flex-row items-center justify-between gap-6"
        >
          <!-- Timestamp -->
          <div class="flex items-center text-gray-700">
            <div
              class="bg-gray-500/80 backdrop-blur-sm rounded-lg p-2 mr-3 shadow-lg"
            >
              <svg
                class="w-4 h-4 text-white"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                ></path>
              </svg>
            </div>
            <span class="font-semibold"
              >Generated on
              {{
                new Date().toLocaleDateString("en-US", {
                  year: "numeric",
                  month: "long",
                  day: "numeric",
                })
              }}</span
            >
          </div>

          <!-- Footer Buttons -->
          <div class="flex space-x-4">
            <!-- Download Button -->
            <button
              @click="handleDownload"
              class="group relative bg-blue-600/80 backdrop-blur-sm hover:bg-blue-700/80 text-white px-6 py-3 rounded-xl font-bold flex items-center transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-1 border border-blue-500/30"
            >
              <div
                class="absolute inset-0 bg-white/10 rounded-xl opacity-0 group-hover:opacity-100 transition-opacity duration-300"
              ></div>
              <svg
                class="w-4 h-4 mr-2 relative z-10"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 10v6m0 0l-3-3m3 3l3-3m2 8H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                ></path>
              </svg>
              <span class="relative z-10">Download</span>
            </button>

            <!-- Share Button -->
            <button
              @click="handleShare"
              class="group relative bg-green-600/80 backdrop-blur-sm hover:bg-green-700/80 text-white px-6 py-3 rounded-xl font-bold flex items-center transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-1 border border-green-500/30"
            >
              <div
                class="absolute inset-0 bg-white/10 rounded-xl opacity-0 group-hover:opacity-100 transition-opacity duration-300"
              ></div>
              <svg
                class="w-4 h-4 mr-2 relative z-10"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M8.684 13.342C8.886 12.938 9 12.482 9 12c0-.482-.114-.938-.316-1.342m0 2.684a3 3 0 110-2.684m0 2.684l6.632 3.316m-6.632-6l6.632-3.316m0 0a3 3 0 105.367-2.684 3 3 0 00-5.367 2.684zm0 9.316a3 3 0 105.367 2.684 3 3 0 00-5.367-2.684z"
                ></path>
              </svg>
              <span class="relative z-10">Share</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import VQrcode from "qrcode.vue";
import { usePaymentStore } from "../../stores/paymentStore";
import { computed } from "vue";

const paymentStore = usePaymentStore();

const props = defineProps({
  ticket: {
    type: Object,
    required: true,
  },
});

const ticketType = computed(() => {
  return paymentStore.tickets.find(
    (t) => t.id === props.ticket.slotTicketTypeCapacityDTO.ticketTypeId
  );
});

/**
 * Handle the share button click event.
 */
function handleShare() {
  const shareData = {
    title: "Check this out",
    text: "Here is something interesting for you!",
    url: window.location.href,
  };

  if (navigator.share) {
    navigator.share(shareData).catch((error) => {
      console.error("Error sharing:", error);
    });
  } else {
    alert("Share not supported on this browser");
  }
}

/**
 * Handle the download button click event.
 */
function handleDownload() {
  const link = document.createElement("a");
  link.href = document.querySelector("canvas").toDataURL("image/jpg");
  link.download = "ticket.jpg";
  link.click();
}
</script>

<style scoped>
/* Premium Glassmorphism Animations */
@keyframes float {
  0%,
  100% {
    transform: translateY(0px) rotate(0deg);
  }
  33% {
    transform: translateY(-10px) rotate(1deg);
  }
  66% {
    transform: translateY(-5px) rotate(-1deg);
  }
}

@keyframes scan {
  0% {
    top: 0;
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
  100% {
    top: 100%;
    opacity: 1;
  }
}

@keyframes shimmer {
  0% {
    transform: translateX(-100%) skewX(-15deg);
  }
  100% {
    transform: translateX(200%) skewX(-15deg);
  }
}

@keyframes glow {
  0%,
  100% {
    box-shadow: 0 0 20px rgba(59, 130, 246, 0.3);
  }
  50% {
    box-shadow:
      0 0 40px rgba(59, 130, 246, 0.6),
      0 0 60px rgba(147, 51, 234, 0.3);
  }
}

.animate-scan {
  animation: scan 2s ease-in-out infinite;
}

.animate-shimmer {
  animation: shimmer 2s infinite;
}

.animate-glow {
  animation: glow 3s ease-in-out infinite;
}

.shadow-3xl {
  box-shadow: 0 35px 60px -12px rgba(0, 0, 0, 0.3);
}

.group:hover .animate-scan {
  animation-duration: 1s;
}

/* Background Pattern Animation */
.group:hover [style*="background-image"] {
  animation: float 6s ease-in-out infinite;
}

/* Custom Backdrop Blur for better browser support */
@supports not (backdrop-filter: blur(20px)) {
  .backdrop-blur-2xl {
    background-color: rgba(255, 255, 255, 0.8);
  }
  .backdrop-blur-xl {
    background-color: rgba(255, 255, 255, 0.7);
  }
  .backdrop-blur-md {
    background-color: rgba(255, 255, 255, 0.6);
  }
}
</style>
