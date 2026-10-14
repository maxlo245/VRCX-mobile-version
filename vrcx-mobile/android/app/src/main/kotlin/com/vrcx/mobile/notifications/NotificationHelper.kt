package com.vrcx.mobile.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {
	const val CHANNEL_FRIENDS = "vrcx_channel_friends"
	const val CHANNEL_INVITES = "vrcx_channel_invites"

	fun createChannels(ctx: Context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val nm = ctx.getSystemService(NotificationManager::class.java)
			val ch1 = NotificationChannel(CHANNEL_FRIENDS, "Amis", NotificationManager.IMPORTANCE_DEFAULT)
			val ch2 = NotificationChannel(CHANNEL_INVITES, "Invitations", NotificationManager.IMPORTANCE_HIGH)
			nm.createNotificationChannel(ch1)
			nm.createNotificationChannel(ch2)
		}
	}

	fun showFriendOnline(ctx: Context, id: String, name: String, avatarUrl: String?) {
		createChannels(ctx)
		val nm = ctx.getSystemService(NotificationManager::class.java)
		val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vrcx://profile/$id"))
		val pi = PendingIntent.getActivity(ctx, id.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

		val builder = NotificationCompat.Builder(ctx, CHANNEL_FRIENDS)
			.setContentTitle("$name est en ligne")
			.setContentText("Cliquez pour voir le profil")
			.setContentIntent(pi)
			.setSmallIcon(android.R.drawable.sym_action_chat)
			.setAutoCancel(true)

		// try to load avatar synchronously from assets or network (simple placeholder)
		if (avatarUrl != null) {
			try {
				val url = java.net.URL(avatarUrl)
				val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
				builder.setLargeIcon(bmp)
			} catch (e: Exception) {
				// ignore
			}
		}

		nm.notify(id.hashCode(), builder.build())
	}
}
