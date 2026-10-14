package com.vrcx.mobile.capacitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.annotation.PluginMethod
import com.getcapacitor.JSArray

@CapacitorPlugin(name = "EventBridge")
class EventBridge : Plugin() {
	private var receiver: BroadcastReceiver? = null

	override fun load() {
		super.load()
		val filter = IntentFilter("com.vrcx.EVENT")
		receiver = object : BroadcastReceiver() {
			override fun onReceive(context: Context?, intent: Intent?) {
				val payload = intent?.getStringExtra("event")
				val js = JSObject()
				js.put("payload", payload)
				notifyListeners("wsEvent", js)
			}
		}
		bridge?.activity?.applicationContext?.registerReceiver(receiver, filter)
	}

	override fun handleOnDestroy() {
		try {
			bridge?.activity?.applicationContext?.unregisterReceiver(receiver)
		} catch (e: Exception) {
			// ignore
		}
		super.handleOnDestroy()
	}

	@PluginMethod
	fun startService(call: PluginCall) {
		val ctx = bridge?.activity?.applicationContext
		val svc = Intent(ctx, Class.forName("com.vrcx.mobile.websocket.WebSocketForegroundService"))
		try {
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
				ctx?.startForegroundService(svc)
			} else {
				ctx?.startService(svc)
			}
			call.resolve()
		} catch (e: Exception) {
			call.reject(e.message)
		}
	}

	@PluginMethod
	fun stopService(call: PluginCall) {
		val ctx = bridge?.activity?.applicationContext
		val svc = Intent(ctx, Class.forName("com.vrcx.mobile.websocket.WebSocketForegroundService"))
		try {
			ctx?.stopService(svc)
			call.resolve()
		} catch (e: Exception) {
			call.reject(e.message)
		}
	}
}
