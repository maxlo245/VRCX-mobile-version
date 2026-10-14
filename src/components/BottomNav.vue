<template>
  <nav class="footer-nav">
    <router-link to="/">Feed</router-link>
    <router-link to="/friends">Amis</router-link>
    <router-link to="/notifications">Notifications</router-link>
    <router-link to="/settings">Paramètres</router-link>
  </nav>
</template>

<script>
export default { name: 'BottomNav' }
</script>

<style scoped>
.footer-nav { position: fixed; left: 0; right: 0; bottom: 0; height: 56px; display:flex; justify-content: space-around; align-items:center; background: #171717 }
.footer-nav a { color: #ddd; text-decoration:none }
</style>
