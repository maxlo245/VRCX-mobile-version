VRCX Mobile Port
=================

This project is a port of VRCX to Android using Vue.js + Capacitor. It reuses the VRCX frontend assets under the MIT license. See LICENSE for details.

Quick start
-----------

1. Install dependencies:
   npm install
2. Build web assets:
   npm run build
3. Sync Capacitor and open Android Studio:
   npm run android:sync
   npm run android:open

Development
-----------

Use `npm run dev` to run the vite dev server. For Android testing, use `npx cap copy android` then open Android Studio.

Android build (one command):
- npm run build && npx cap sync android && npx cap open android

Automated tests (vite + vitest):
- npm run test (vitest needs to be installed)

Notes
-----

- Native Android plugins (Foreground Service, Notifications, Secure Storage) implemented under android/app/src/main/kotlin.
- Some desktop-only features (game logs, overlays) are not available on mobile and will be hidden.

Checklist (manual)
- [ ] Notification received when app is backgrounded
- [ ] Notification survives device reboot
- [ ] Permission POST_NOTIFICATIONS requested on Android 13+
- [ ] Foreground service maintains WebSocket connection
- [ ] Deep link opens correct page when tapping notification
- [ ] Secure storage used for credentials (Android Keystore)

Known limits
- VRChat API rate limits and auth flows may need runtime adjustments.
- Push via FCM not configured; requires server relay.


# VRCX-mobile-version
