package com.vrcx.mobile.api

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class VrchatApiClient(private val client: OkHttpClient = OkHttpClient()) {
	private val baseUrl = "https://api.vrchat.cloud/api/1"

	fun login(username: String, password: String, callback: (success: Boolean, body: String?) -> Unit) {
		val url = "$baseUrl/auth/user"
		val json = JSONObject()
		json.put("username", username)
		json.put("password", password)
		val reqBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
		val request = Request.Builder().url(url).post(reqBody).build()
		client.newCall(request).enqueue(object: Callback {
			override fun onFailure(call: Call, e: IOException) {
				callback(false, e.message)
			}

			override fun onResponse(call: Call, response: Response) {
				val body = response.body()?.string()
				callback(response.isSuccessful, body)
			}
		})
	}
}
