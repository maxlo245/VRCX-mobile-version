import { createApp } from 'vue'
import { createPinia } from 'pinia'
import router from './router'
import App from './App.vue'
import BottomNav from './components/BottomNav.vue'
import './styles.css'

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.component('BottomNav', BottomNav)
app.component('Drawer', () => import('./components/Drawer.vue'))
app.mount('#app')
