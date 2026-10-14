package com.vrcx.mobile.capacitor

import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.vrcx.mobile.api.VrchatApiClient

@CapacitorPlugin(name = "VRCXBridge")
class CapacitorBridge : Plugin() {
	private val api = VrchatApiClient()

	@PluginMethod
	fun login(call: PluginCall) {
		val username = call.getString("username") ?: return call.reject("username required")
		val password = call.getString("password") ?: return call.reject("password required")
		api.login(username, password) { success, body ->
			val ret = JSObject()
			ret.put("success", success)
			ret.put("body", body)
			if (success) call.resolve(ret) else call.reject(body ?: "error")
		}
	}
}
