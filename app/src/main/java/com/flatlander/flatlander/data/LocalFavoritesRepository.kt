package com.flatlander.flatlander.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.flatlander.flatlander.model.SiteLite
import com.google.gson.Gson
import io.reactivex.Single

/**
 * Created by iancowley on 1/28/18.
 */
class LocalFavoritesRepository : FavoritesRepository {

    private object Holder { val INSTANCE = LocalFavoritesRepository() }
    private lateinit var context: Context
    private lateinit var sharedPrefs: SharedPreferences

    companion object {
        private val TAG = LocalFavoritesRepository::class.java.simpleName
        private val PREF_FAVORITE_IDS = "ids"
        private val PREF_FAVORITE_SITES = "sites"
        val instance: LocalFavoritesRepository by lazy { Holder.INSTANCE }
    }

    fun init(context: Context) {
        this.context = context
        this.sharedPrefs = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
    }

    @SuppressLint("ApplySharedPref")
    override fun favoriteSite(siteLite: SiteLite): Single<Boolean> {
        val ids = sharedPrefs.getStringSet(PREF_FAVORITE_IDS, mutableSetOf())
        ids.add(siteLite.getUniqueId())
        sharedPrefs.edit().putStringSet(PREF_FAVORITE_IDS, ids).commit()

        val sites = sharedPrefs.getStringSet(PREF_FAVORITE_SITES, mutableSetOf())
        sites.add(Gson().toJson(siteLite))
        sharedPrefs.edit().putStringSet(PREF_FAVORITE_SITES, sites).commit()
        return Single.just(true)
    }

    @SuppressLint("ApplySharedPref")
    override fun unfavoriteSite(siteLite: SiteLite): Single<Boolean> {
        val ids = sharedPrefs.getStringSet(PREF_FAVORITE_IDS, mutableSetOf())
        ids.remove(siteLite.getUniqueId())
        sharedPrefs.edit().putStringSet(PREF_FAVORITE_IDS, ids).commit()

        val sites = sharedPrefs.getStringSet(PREF_FAVORITE_SITES, mutableSetOf())
        sites.remove(Gson().toJson(siteLite))
        sharedPrefs.edit().putStringSet(PREF_FAVORITE_SITES, sites).commit()
        return Single.just(true)
    }

    override fun isSiteFavorite(siteLite: SiteLite): Boolean {
        return sharedPrefs.getStringSet(PREF_FAVORITE_IDS, mutableSetOf()).contains(siteLite.getUniqueId())
    }

    override fun getFavoriteSites(): Single<List<SiteLite>> {
        val sitesSet = sharedPrefs.getStringSet(PREF_FAVORITE_SITES, mutableSetOf())
        val sitesList = mutableListOf<SiteLite>()

        val gson = Gson()
        sitesSet.iterator().forEach {
            val site = gson.fromJson<SiteLite>(it, SiteLite::class.java)
            sitesList.add(site)
        }
        return Single.just(sitesList)
    }
}