package com.vrcx.mobile.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotificationDao {
	@Insert
	suspend fun insert(notification: NotificationEntity)

	@Query("SELECT * FROM notifications ORDER BY timestamp DESC")
	suspend fun getAll(): List<NotificationEntity>
}
