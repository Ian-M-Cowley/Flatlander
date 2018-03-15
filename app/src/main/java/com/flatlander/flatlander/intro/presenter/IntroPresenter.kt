package com.flatlander.flatlander.intro.presenter

import com.flatlander.flatlander.intro.IntroContract

/**
 * Created by iancowley on 3/8/18.
 */
class IntroPresenter(override val view: IntroContract.View,
                     val interactor: IntroContract.Interactor): IntroContract.Presenter {

    override fun onViewAdded() {
    }

    override fun onViewRemoved() {
    }

    override fun onAllowClicked() {
        view.requestLocationPermission()
    }

    override fun onDenyClicked() {
        view.goToCategoriesScreen()
    }

    override fun onPermissionRequestResult(didAllow: Boolean) {
        view.goToCategoriesScreen()
    }

    override fun onErrorDismissed(id: Int) {
    }

    override fun onMessagePositive(id: Int) {
    }

    override fun onMessageNegative(id: Int) {
    }
}