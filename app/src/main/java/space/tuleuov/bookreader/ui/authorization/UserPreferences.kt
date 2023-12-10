package space.tuleuov.bookreader.ui.authorization

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import space.tuleuov.bookreader.db.entity.User

class UserPreferences(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(user: User)  {
        sharedPreferences.edit {
            putInt(KEY_UID, user.uid)
            putString(KEY_NAME, user.name)
            putString(KEY_EMAIL, user.email)
            putString(KEY_PASSWORD, user.password)
            putString(KEY_AVATAR, user.avatar)
            putString(KEY_STATUS, user.status)
            putInt(KEY_LEVEL, user.level)
        }
    }

    fun getUser(): User? {
        val uid = sharedPreferences.getInt(KEY_UID, 0)
        // If uid is 0, it means the user is not saved
        return if (uid != 0) {
            User(
                uid = uid,
                name = sharedPreferences.getString(KEY_NAME, "") ?: "",
                email = sharedPreferences.getString(KEY_EMAIL, "") ?: "",
                password = sharedPreferences.getString(KEY_PASSWORD, "") ?: "",
                avatar = sharedPreferences.getString(KEY_AVATAR, null),
                status = sharedPreferences.getString(KEY_STATUS, null),
                level = sharedPreferences.getInt(KEY_LEVEL, 0)
            )
        } else {
            null
        }
    }

    companion object {
        private const val PREF_NAME = "user_preferences"
        private const val KEY_UID = "uid"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        private const val KEY_AVATAR = "avatar"
        private const val KEY_STATUS = "status"
        private const val KEY_LEVEL = "level"
    }
}

