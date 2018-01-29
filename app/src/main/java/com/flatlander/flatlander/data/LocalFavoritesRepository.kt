package com.flatlander.flatlander.data

import android.content.Context
import android.content.SharedPreferences
import com.flatlander.flatlander.model.Site
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

    override fun favoriteSite(site: Site): Single<Boolean> {
        val ids = sharedPrefs.getStringSet(PREF_FAVORITE_IDS, mutableSetOf())
        ids.add(site.id)
        sharedPrefs.edit().putStringSet(PREF_FAVORITE_IDS, ids).apply()

        val sites = sharedPrefs.getStringSet(PREF_FAVORITE_SITES, mutableSetOf())
        sites.add(Gson().toJson(site))
        sharedPrefs.edit().putStringSet(PREF_FAVORITE_SITES, sites).apply()
        return Single.just(true)
    }

    override fun unfavoriteSite(site: Site): Single<Boolean> {
        val ids = sharedPrefs.getStringSet(PREF_FAVORITE_IDS, mutableSetOf())
        ids.remove(site.id)
        sharedPrefs.edit().putStringSet(PREF_FAVORITE_IDS, ids).apply()

        val sites = sharedPrefs.getStringSet(PREF_FAVORITE_SITES, mutableSetOf())
        sites.remove(Gson().toJson(site))
        sharedPrefs.edit().putStringSet(PREF_FAVORITE_SITES, sites).apply()
        return Single.just(true)
    }

    override fun isSiteFavorite(id: String): Boolean {
        return sharedPrefs.getStringSet(PREF_FAVORITE_IDS, mutableSetOf()).contains(id)
    }

    override fun getFavoriteSites(): Single<List<SiteLite>> {
        val sitesSet = sharedPrefs.getStringSet(PREF_FAVORITE_SITES, mutableSetOf())
        val sitesList = mutableListOf<SiteLite>()

        val gson = Gson()
        sitesSet.iterator().forEach {
            val site = gson.fromJson<Site>(it, Site::class.java)
            sitesList.add(site)
        }
        return Single.just(sitesList)
    }
}