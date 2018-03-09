package com.flatlander.flatlander.intro.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import com.flatlander.flatlander.R
import com.flatlander.flatlander.base.BaseContractActivity
import com.flatlander.flatlander.categories.list.view.CategoriesActivity
import com.flatlander.flatlander.intro.IntroContract
import com.flatlander.flatlander.intro.interactor.IntroInteractor
import com.flatlander.flatlander.intro.presenter.IntroPresenter
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by iancowley on 3/8/18.
 */
class IntroActivity : BaseContractActivity(), IntroContract.View {

    @BindView(R.id.button_allow) lateinit var allowButton: Button
    @BindView(R.id.button_deny) lateinit var denyButton: Button

    lateinit var presenter: IntroContract.Presenter

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 214

        fun newIntent(caller: Context): Intent {
            return Intent(caller, IntroActivity::class.java)
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_intro
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)

        allowButton.setOnClickListener { presenter.onAllowClicked() }
        denyButton.setOnClickListener { presenter.onDenyClicked() }

        presenter = IntroPresenter(this, IntroInteractor())
        presenter.onViewAdded()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onDestroy() {
        presenter.onViewRemoved()
        super.onDestroy()
    }

    override fun goToCategoriesScreen() {
        startActivity(CategoriesActivity.newIntent(this))
        finish()
    }

    override fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    presenter.onPermissionRequestResult(true)
                } else {
                    presenter.onPermissionRequestResult(false)
                }
                return
            }
        }
    }

    override fun onErrorDismissed(id: Int) {
        presenter.onErrorDismissed(id)
    }

    override fun onMessagePositive(id: Int) {
        presenter.onMessagePositive(id)
    }

    override fun onMessageNegative(id: Int) {
        presenter.onMessageNegative(id)
    }
}