package com.example.littlelemon.data

import android.content.Context
import androidx.core.content.edit

data class User(val firstName: String, val lastName: String, val email: String)

/** Persists the onboarding data so it survives app restarts. */
class UserPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("little_lemon_user", Context.MODE_PRIVATE)

    val isLoggedIn: Boolean
        get() = prefs.getBoolean(KEY_LOGGED_IN, false)

    val user: User
        get() = User(
            firstName = prefs.getString(KEY_FIRST_NAME, "").orEmpty(),
            lastName = prefs.getString(KEY_LAST_NAME, "").orEmpty(),
            email = prefs.getString(KEY_EMAIL, "").orEmpty(),
        )

    fun save(user: User) = prefs.edit {
        putString(KEY_FIRST_NAME, user.firstName)
        putString(KEY_LAST_NAME, user.lastName)
        putString(KEY_EMAIL, user.email)
        putBoolean(KEY_LOGGED_IN, true)
    }

    fun clear() = prefs.edit { clear() }

    private companion object {
        const val KEY_FIRST_NAME = "first_name"
        const val KEY_LAST_NAME = "last_name"
        const val KEY_EMAIL = "email"
        const val KEY_LOGGED_IN = "logged_in"
    }
}
