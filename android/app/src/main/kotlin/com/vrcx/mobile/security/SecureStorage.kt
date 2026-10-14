package com.vrcx.mobile.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SecureStorage {
	fun getEncryptedPrefs(context: Context) = EncryptedSharedPreferences.create(
		"vrcx_secure_prefs",
		MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
		context,
		EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
		EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
	)
}
