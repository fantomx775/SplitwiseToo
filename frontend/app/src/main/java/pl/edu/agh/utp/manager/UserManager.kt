package pl.edu.agh.utp.manager

import android.content.Context
import android.content.SharedPreferences
import pl.edu.agh.utp.model.User

class UserManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("UserManager", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_ID = "id"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    fun saveUser(user: User) {
        prefs.edit().apply {
            putLong(KEY_ID, user.userId)
            putString(KEY_USERNAME, user.name)
            putString(KEY_EMAIL, user.email)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun getUser(): User? {
        if (!prefs.getBoolean(KEY_IS_LOGGED_IN, false)) return null

        val id = prefs.getLong(KEY_ID, -1)
        val username = prefs.getString(KEY_USERNAME, null)
        val email = prefs.getString(KEY_EMAIL, null)

        return if (id != -1L && username != null && email != null) User(
            id,
            username,
            email
        ) else null
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logOut() {
        prefs.edit().apply {
            remove(KEY_ID)
            remove(KEY_USERNAME)
            remove(KEY_EMAIL)
            putBoolean(KEY_IS_LOGGED_IN, false)
            apply()
        }
    }
}
