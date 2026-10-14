package com.vrcx.mobile.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NotificationEntity::class], version = 1)
abstract class VrcxDatabase : RoomDatabase() {
	abstract fun notificationDao(): NotificationDao

	companion object {
		@Volatile private var INSTANCE: VrcxDatabase? = null

		fun getInstance(context: Context): VrcxDatabase {
			return INSTANCE ?: synchronized(this) {
				val inst = Room.databaseBuilder(context.applicationContext, VrcxDatabase::class.java, "vrcx.db").build()
				INSTANCE = inst
				inst
			}
		}
	}
}
