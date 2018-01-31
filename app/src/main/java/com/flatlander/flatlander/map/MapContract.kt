package com.flatlander.flatlander.map

import com.flatlander.flatlander.base.BaseContract
import com.google.android.gms.maps.model.LatLng

/**
 * Created by iancowley on 1/30/18.
 */
interface MapContract {

    interface Interactor {
    }

    interface View : BaseContract.View {

        fun setLocation(name: String, location: LatLng)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onNavigateClicked()
    }
}