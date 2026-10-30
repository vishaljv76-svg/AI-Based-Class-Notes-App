package com.example.Simtas.data.remote

import android.content.Context
import android.content.SharedPreferences

/**
 * Manages user authentication session persistence in SharedPreferences.
 */
object SessionManager {
    private const val PREF_NAME = "smartnotes_ai_prefs"
    private const val KEY_AUTH_TOKEN = "auth_token"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"

    private var inMemoryToken: String? = null
    private var inMemoryUserId: Int = 1
    private var inMemoryUserName: String = "Alex Morgan"
    private var inMemoryUserEmail: String = "alex.morgan@campus.edu"

    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        inMemoryToken = prefs.getString(KEY_AUTH_TOKEN, null)
        inMemoryUserId = prefs.getInt(KEY_USER_ID, 1)
        inMemoryUserName = prefs.getString(KEY_USER_NAME, "Alex Morgan") ?: "Alex Morgan"
        inMemoryUserEmail = prefs.getString(KEY_USER_EMAIL, "alex.morgan@campus.edu") ?: "alex.morgan@campus.edu"
    }

    fun saveSession(context: Context?, token: String, userId: Int, name: String, email: String) {
        inMemoryToken = token
        inMemoryUserId = userId
        inMemoryUserName = name
        inMemoryUserEmail = email

        context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)?.edit()?.apply {
            putString(KEY_AUTH_TOKEN, token)
            putInt(KEY_USER_ID, userId)
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_EMAIL, email)
            apply()
        }
    }

    fun updateUserName(context: Context?, name: String) {
        inMemoryUserName = name
        context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)?.edit()?.apply {
            putString(KEY_USER_NAME, name)
            apply()
        }
    }

    fun getToken(): String? = inMemoryToken

    fun getUserId(): Int = inMemoryUserId

    fun getUserName(): String = inMemoryUserName

    fun getUserEmail(): String = inMemoryUserEmail

    fun isLoggedIn(): Boolean = !inMemoryToken.isNullOrEmpty()

    fun clearSession(context: Context?) {
        inMemoryToken = null
        inMemoryUserId = 1
        inMemoryUserName = "Alex Morgan"
        inMemoryUserEmail = "alex.morgan@campus.edu"

        context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)?.edit()?.clear()?.apply()
    }
}

