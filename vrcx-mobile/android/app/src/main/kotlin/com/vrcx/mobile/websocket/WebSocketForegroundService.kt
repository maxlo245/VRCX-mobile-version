package com.vrcx.mobile.websocket

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import okhttp3.*
import java.util.concurrent.TimeUnit

class WebSocketForegroundService: Service() {
	private val CHANNEL_ID = "vrcx_ws_channel"
	private var client: OkHttpClient? = null
	private var ws: WebSocket? = null
	private val handler = Handler(Looper.getMainLooper())
	private var backoff = 1000L

	override fun onCreate() {
		super.onCreate()
		createNotificationChannel()
		client = OkHttpClient.Builder().pingInterval(20, TimeUnit.SECONDS).build()
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		val notification = buildNotification("VRCX: connexion en cours")
		startForeground(1, notification)
		connectWebSocket()
		return START_STICKY
	}

	override fun onBind(intent: Intent?): IBinder? { return null }

	override fun onDestroy() {
		ws?.close(1000, "Service destroyed")
		client?.dispatcher?.executorService?.shutdown()
		super.onDestroy()
	}

	private fun createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val name = "VRCX WebSocket"
			val importance = NotificationManager.IMPORTANCE_LOW
			val channel = NotificationChannel(CHANNEL_ID, name, importance)
			val nm = getSystemService(NotificationManager::class.java)
			nm.createNotificationChannel(channel)
		}
	}

	private fun buildNotification(content: String): Notification {
		return NotificationCompat.Builder(this, CHANNEL_ID)
			.setContentTitle("VRCX Mobile")
			.setContentText(content)
			.setSmallIcon(android.R.drawable.ic_dialog_info)
			.setOngoing(true)
			.build()
	}

	private fun connectWebSocket() {
		val request = Request.Builder().url("wss://api.vrchat.cloud/stream")
			.build()
		ws = client?.newWebSocket(request, object: WebSocketListener() {
			override fun onOpen(webSocket: WebSocket, response: Response) {
				backoff = 1000L
				updateNotification("Connecté")
			}

			override fun onMessage(webSocket: WebSocket, text: String) {
				// Forward event to JS via broadcast intent
				try {
					val intent = android.content.Intent("com.vrcx.EVENT")
					intent.putExtra("event", text)
					sendBroadcast(intent)
				} catch (e: Exception) {
					// ignore
				}
			}

			override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
				scheduleReconnect()
			}

			override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
				scheduleReconnect()
			}
		})
	}

	private fun scheduleReconnect() {
		updateNotification("Reconnecte dans ${backoff}ms")
		handler.postDelayed({ connectWebSocket() }, backoff)
		backoff = (backoff * 2).coerceAtMost(120000L)
	}

	private fun updateNotification(text: String) {
		val nm = getSystemService(NotificationManager::class.java)
		val n = buildNotification(text)
		nm.notify(1, n)
	}
}
