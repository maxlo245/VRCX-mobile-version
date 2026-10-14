import { Plugins } from '@capacitor/core'
const { EventBridge, DBPlugin } = Plugins

export function startNativeWebSocket() {
  try {
    EventBridge.startService()
  } catch (e) {
    console.warn('startNativeWebSocket failed', e)
  }
}

export function stopNativeWebSocket() {
  try { EventBridge.stopService() } catch (e) { }
}

export function onWsEvent(cb) {
  if (!EventBridge || !EventBridge.addListener) return () => {}
  const handler = EventBridge.addListener('wsEvent', (ev) => {
    try { cb(ev.payload) } catch (e) {}
    // persist in native DB
    try {
      DBPlugin.insertNotification({ type: 'event', title: 'Event', body: ev.payload })
    } catch (e) {}
  })
  return () => { handler.remove() }
}
