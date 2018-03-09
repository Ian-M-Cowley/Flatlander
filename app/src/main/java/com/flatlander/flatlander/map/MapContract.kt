package com.flatlander.flatlander.map

import com.flatlander.flatlander.base.BaseContract
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import com.google.android.gms.maps.model.LatLng

/**
 * Created by iancowley on 1/30/18.
 */
interface MapContract {

    interface Interactor {
    }

    interface View : BaseContract.View {
        fun goToGoogleMaps(mapSiteItem: MapSiteItem, location: LatLng)

        fun setLocation(name: String, location: LatLng)
        fun showNavigationFab(show: Boolean)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onNavigateClicked()
    }
}