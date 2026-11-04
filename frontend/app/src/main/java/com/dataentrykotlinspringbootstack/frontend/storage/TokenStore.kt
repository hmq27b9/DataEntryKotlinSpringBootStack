package com.dataentrykotlinspringbootstack.frontend.storage

import android.content.Context
import androidx.core.content.edit

class TokenStore(context: Context) {
    private val prefs = context.applicationContext
        .getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun saveToken(token: String) = prefs.edit { putString("jwt", token) }
    fun getToken(): String? = prefs.getString("jwt", null)
    fun clear() = prefs.edit { remove("jwt") }
}