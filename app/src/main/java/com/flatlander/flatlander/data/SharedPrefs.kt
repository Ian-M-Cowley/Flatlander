package com.flatlander.flatlander.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by iancowley on 3/12/18.
 */
class SharedPrefs {

    private lateinit var sharedPreferences: SharedPreferences

    private object Holder {
        val INSTANCE = SharedPrefs()
    }

    companion object {
        private val TAG = SharedPrefs::class.java.simpleName
        val instance: SharedPrefs by lazy { Holder.INSTANCE }

        private const val PREFS_FILE = "prefs_file"
        private const val PREF_HAS_SEEN_INTRO = "pref_has_seen_intro"
    }

    fun init(applicationContext: Context) {
        sharedPreferences = applicationContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
    }

    fun hasSeenIntro(): Boolean {
        return sharedPreferences.getBoolean(PREF_HAS_SEEN_INTRO, false)
    }

    fun sethasSeenIntro(hasSeen: Boolean) {
        sharedPreferences.edit().putBoolean(PREF_HAS_SEEN_INTRO, hasSeen).apply()
    }
}