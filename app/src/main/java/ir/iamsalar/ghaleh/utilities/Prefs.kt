package ir.iamsalar.ghaleh.utilities

import android.content.Context

class Prefs(ctx:Context) {
    private val pref = ctx.getSharedPreferences("GhalehPrefs", Context.MODE_PRIVATE)

    fun setString(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return pref.getString(key, "")
    }

    fun setInt(key: String, value: Int) {
        pref.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return pref.getInt(key, 0)
    }
    fun getInt2(key: String): Int {
        return pref.getInt(key, 2)
    }

    fun getInt20(key: String): Int {
        return pref.getInt(key, 1200)
    }

    fun setBoolean(key: String, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun setLong(key: String, value: Long) {
        pref.edit().putLong(key, value).apply()
    }

    fun getLong(key: String): Long {
        return pref.getLong(key, 0)
    }

    fun setFloat(key: String, value: Float) {
        pref.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String): Float {
        return pref.getFloat(key, 0f)
    }

    fun setStringSet(key: String, value: Set<String>) {
        pref.edit().putStringSet(key, value).apply()
    }

    fun getStringSet(key: String): Set<String>? {
        return pref.getStringSet(key, null)
    }

    fun remove(key: String) {
        pref.edit().remove(key).apply()
    }

    fun clear() {
        pref.edit().clear().apply()
    }
}