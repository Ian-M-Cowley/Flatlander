package com.flatlander.flatlander.map.presenter

import com.flatlander.flatlander.map.MapContract
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import com.google.android.gms.maps.model.LatLng

/**
 * Created by iancowley on 1/30/18.
 */
class MapPresenter(override val view: MapContract.View,
                   val interactor: MapContract.Interactor,
                   val mapSiteItem: MapSiteItem) : MapContract.Presenter {

    override fun onViewAdded() {
        view.setLocation(mapSiteItem.name,
                LatLng(mapSiteItem.lat.toDouble(), mapSiteItem.long.toDouble()))
    }

    override fun onViewRemoved() {
    }

    override fun onNavigateClicked() {

    }

    override fun onErrorDismissed(id: Int) {
    }

    override fun onMessagePositive(id: Int) {
    }

    override fun onMessageNegative(id: Int) {
    }
}