package com.flatlander.flatlander.data

import com.flatlander.flatlander.model.Site
import com.flatlander.flatlander.model.siteitem.ImageSiteItem
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import com.flatlander.flatlander.model.siteitem.TextSiteItem
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Single

/**
 * Created by iancowley on 1/27/18.
 */
class FirebaseSitesRepository private constructor() : SitesRepository {

    private object Holder {
        val INSTANCE = FirebaseSitesRepository()
    }

    companion object {
        private val TAG = FirebaseSitesRepository::class.java.simpleName
        val instance: FirebaseSitesRepository by lazy { Holder.INSTANCE }
    }

    override fun getSiteById(id: String): Single<Site> {
        return Single.create(DataOnSubscribe(FirebaseDatabase.getInstance().getReference("sites").child(id)))
                .map {
                    val site = Site()
                    site.defaultMapSiteItem = it.child("defaultMapSiteItem").getValue(MapSiteItem::class.java)
                    site.id = it.child("id").value as String
                    site.name = it.child("name").value as String
                    site.title = it.child("title").value as String
                    site.imageUrl = it.child("imageUrl").value as String
                    site.description = it.child("description").value as String
                    val siteItemsSnapshot = it.child("siteItems")
                    siteItemsSnapshot.children.forEach {
                        val type = it.child("type").value as String
                        when (type) {
                            "image" -> {
                                val imageSiteItem = ImageSiteItem(
                                        it.child("id").value as String,
                                        it.child("type").value as String,
                                        it.child("imageUrl").value as String,
                                        it.child("height").value as String,
                                        it.child("width").value as String
                                )
                                site.siteItems.add(imageSiteItem)
                            }
                            "map" -> {
                                val mapSiteItem = MapSiteItem(
                                        it.child("id").value as String,
                                        it.child("type").value as String,
                                        it.child("lat").value as String,
                                        it.child("long").value as String,
                                        it.child("name").value as String,
                                        it.child("googlePlaceId").value as String
                                )
                                site.siteItems.add(mapSiteItem)
                            }
                            "text" -> {
                                val textSiteItem = TextSiteItem(
                                        it.child("id").value as String,
                                        it.child("type").value as String,
                                        it.child("title").value as String?,
                                        it.child("description").value as String
                                )
                                site.siteItems.add(textSiteItem)
                            }
                        }
                    }
                    return@map site
                }
    }

}