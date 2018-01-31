package com.flatlander.flatlander.site.adapter.component

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.flatlander.flatlander.R
import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by iancowley on 9/13/17.
 */
class MapComponent(itemView: View?) : BaseComponent(itemView) {

    @BindView(R.id.map) lateinit var mapView : MapView
    @BindView(R.id.touch_shield) lateinit var touchShield: View

    init {
        ButterKnife.bind(this, itemView!!)
        mapView.onCreate(null)
    }

    override fun bind(siteItem: BaseSiteItem) {
        val mapSiteItem = siteItem as MapSiteItem
        mapView.getMapAsync({
            val siteLocation = LatLng(mapSiteItem.lat.toDouble(), mapSiteItem.long.toDouble())
            it.addMarker(MarkerOptions().position(siteLocation).title(mapSiteItem.name))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(siteLocation, 12.0f))
            it.uiSettings.setAllGesturesEnabled(false)
            mapView.onResume()
        })
    }
}