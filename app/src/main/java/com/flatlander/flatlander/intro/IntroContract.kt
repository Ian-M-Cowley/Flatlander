package com.flatlander.flatlander.intro

import com.flatlander.flatlander.base.BaseContract

/**
 * Created by iancowley on 3/8/18.
 */
interface IntroContract {

    interface Interactor {
    }

    interface View : BaseContract.View {
        fun goToCategoriesScreen()
        fun requestLocationPermission()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onAllowClicked()
        fun onDenyClicked()
        fun onPermissionRequestResult(didAllow: Boolean)
    }
}