package com.example.carworkshopbd.utils

import android.content.Context

class SharedManager(context: Context) {

    var isAuth: Boolean
        get() = sharedPref.getBoolean(AUTH_KEY, false)
        set(value) {
            sharedPref.edit().putBoolean(AUTH_KEY, value).apply()
        }

    var typeUser: Int
        get() = sharedPref.getInt(TYPE_USER_KEY, -1)
        set(value) {
            sharedPref.edit().putInt(TYPE_USER_KEY, value).apply()
        }

    val managerPassword: String
        get() = sharedPref.getString(MANAGER_PASSWORD_KEY, MANAGER_PASSWORD_VALUE)
            ?: MANAGER_PASSWORD_VALUE

    val directorPassword: String
        get() = sharedPref.getString(DIRECTOR_PASSWORD_KEY, DIRECTOR_PASSWORD_VALUE)
            ?: DIRECTOR_PASSWORD_VALUE

    private val sharedPref by lazy(LazyThreadSafetyMode.NONE) {
        context.getSharedPreferences(
            SHARED_KEY,
            Context.MODE_PRIVATE
        )
    }

    companion object {
        private const val SHARED_KEY = " shared_key"
        private const val MANAGER_PASSWORD_KEY = "manager_password_key"
        private const val MANAGER_PASSWORD_VALUE = "12345678"
        private const val DIRECTOR_PASSWORD_KEY = "director_password_key"
        private const val DIRECTOR_PASSWORD_VALUE = "87654321"
        private const val AUTH_KEY = "auth_key"
        private const val TYPE_USER_KEY = "type_user_key"

        const val MANAGER_TYPE = 0
        const val DIRECTOR_TYPE = 1
    }
}