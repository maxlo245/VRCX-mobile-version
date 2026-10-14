package com.vrcx.mobile.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
	@PrimaryKey(autoGenerate = true) val id: Long = 0,
	val type: String,
	val title: String,
	val body: String,
	val timestamp: Long
)
