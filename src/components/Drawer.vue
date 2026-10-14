<template>
  <aside class="drawer" :class="{open: isOpen}">
    <header>VRCX</header>
    <ul>
      <li><router-link to="/">Feed</router-link></li>
      <li><router-link to="/friends">Amis</router-link></li>
      <li><router-link to="/notifications">Notifications</router-link></li>
      <li><router-link to="/settings">Paramètres</router-link></li>
    </ul>
  </aside>
</template>

<script>
export default { name: 'Drawer', props: { isOpen: Boolean } }
</script>

<style scoped>
.drawer { position: fixed; left: -260px; top: 0; bottom: 0; width: 260px; background: #111; color: #fff; transition: left .2s }
.drawer.open { left: 0 }
.drawer header { padding: 16px; font-weight: bold }
.drawer ul { list-style:none; padding:0 }
.drawer li { padding: 12px 16px }
</style>
