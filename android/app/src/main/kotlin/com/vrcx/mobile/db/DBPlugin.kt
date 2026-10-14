package com.vrcx.mobile.db

import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.annotation.PluginMethod
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.getcapacitor.JSObject
import com.getcapacitor.JSONArray

@CapacitorPlugin(name = "DBPlugin")
class DBPlugin : Plugin() {
	private val scope = CoroutineScope(Dispatchers.IO)

	@PluginMethod
	fun insertNotification(call: PluginCall) {
		val type = call.getString("type") ?: "info"
		val title = call.getString("title") ?: ""
		val body = call.getString("body") ?: ""
		val ts = System.currentTimeMillis()
		val db = VrcxDatabase.getInstance(context)
		scope.launch {
			db.notificationDao().insert(NotificationEntity(type = type, title = title, body = body, timestamp = ts))
			call.resolve()
		}
	}

	@PluginMethod
	fun getNotifications(call: PluginCall) {
		val db = VrcxDatabase.getInstance(context)
		scope.launch {
			val list = db.notificationDao().getAll()
			val arr = JSONArray()
			for (n in list) {
				val obj = JSObject()
				obj.put("id", n.id)
				obj.put("type", n.type)
				obj.put("title", n.title)
				obj.put("body", n.body)
				obj.put("timestamp", n.timestamp)
				arr.put(obj)
			}
			val res = JSObject()
			res.put("items", arr)
			call.resolve(res)
		}
	}
}
