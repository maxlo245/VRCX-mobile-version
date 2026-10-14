<template>
  <div class="page">
    <h1>Feed</h1>
    <div v-if="events.length === 0">Aucun événement pour l'instant.</div>
    <ul>
      <li v-for="(e, idx) in events" :key="idx">{{ e }}</li>
    </ul>
  </div>
</template>

<script>
import { onMounted, onBeforeUnmount, ref } from 'vue'
import { startNativeWebSocket, stopNativeWebSocket, onWsEvent } from '../services/nativeWs'

export default {
  name: 'Home',
  setup() {
    const events = ref([])
    let unsubscribe = null

    onMounted(() => {
      startNativeWebSocket()
      unsubscribe = onWsEvent((payload) => {
        if (!payload) return
        events.value.unshift(payload)
        if (events.value.length > 200) events.value.pop()
      })
    })

    onBeforeUnmount(() => {
      if (unsubscribe) unsubscribe()
      stopNativeWebSocket()
    })

    return { events }
  }
}
</script>

<style scoped>
.page { padding: 16px; }
</style>
