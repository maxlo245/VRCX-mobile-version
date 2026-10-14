package com.vrcx.mobile.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class BootReceiver: BroadcastReceiver() {
	override fun onReceive(context: Context, intent: Intent) {
		if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
			val svc = Intent(context, Class.forName("com.vrcx.mobile.websocket.WebSocketForegroundService"))
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				context.startForegroundService(svc)
			} else {
				context.startService(svc)
			}
		}
	}
}
