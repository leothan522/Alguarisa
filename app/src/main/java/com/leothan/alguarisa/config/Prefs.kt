package com.leothan.alguarisa.config

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    val PREFS_NAME = "com.leothan.alguarisa.login"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    val SHARED_LOGIN = "shared_login"
    val SHARED_ID = "shared_id"
    val SHARED_NAME = "shared_name"
    val SHARED_EMAIL = "shared_email"
    val SHARED_TELEFONO = "shared_telefono"

    var login: Boolean
    get() = prefs.getBoolean(SHARED_LOGIN, false)
    set(value) = prefs.edit().putBoolean(SHARED_LOGIN, value).apply()

    var id: Int
    get() = prefs.getInt(SHARED_ID, 0)
    set(value) = prefs.edit().putInt(SHARED_ID, value).apply()

    var name: String?
    get() = prefs.getString(SHARED_NAME, "Android Studio")
    set(value) = prefs.edit().putString(SHARED_NAME, value).apply()

    var email: String?
    get() = prefs.getString(SHARED_EMAIL, "androidstudio.example.com")
    set(value) = prefs.edit().putString(SHARED_EMAIL, value).apply()

    var telefono: String?
    get() = prefs.getString(SHARED_TELEFONO, "00000000000")
    set(value) = prefs.edit().putString(SHARED_TELEFONO, value).apply()

    fun remover(){
        prefs.edit().remove(SHARED_LOGIN).apply()
        prefs.edit().remove(SHARED_ID).apply()
        prefs.edit().remove(SHARED_NAME).apply()
        prefs.edit().remove(SHARED_EMAIL).apply()
        prefs.edit().remove(SHARED_TELEFONO).apply()
    }

}