package com.vrcx.mobile.notifications

import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.annotation.PluginMethod

@CapacitorPlugin(name = "NotifPlugin")
class NotificationPlugin : Plugin() {
	@PluginMethod
	fun createChannels(call: PluginCall) {
		NotificationHelper.createChannels(context)
		call.resolve()
	}

	@PluginMethod
	fun friendOnline(call: PluginCall) {
		val id = call.getString("id") ?: return call.reject("id required")
		val name = call.getString("name") ?: "ami"
		val avatar = call.getString("avatarUrl")
		NotificationHelper.showFriendOnline(context, id, name, avatar)
		call.resolve()
	}
}
