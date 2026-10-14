<template>
  <div id="app">
    <header>
      <button @click="toggleDrawer">☰</button>
      <h2>{{ title }}</h2>
    </header>

    <Drawer :isOpen="drawerOpen" />

    <main>
      <router-view />
    </main>

    <BottomNav />

  </div>
</template>

<script>
export default {
  name: 'App',
  data() { return { title: 'VRCX Mobile', drawerOpen: false } },
  methods: { toggleDrawer() { this.drawerOpen = !this.drawerOpen } }
}
</script>

<style>
html, body, #app { height: 100%; margin: 0; }
</style>
