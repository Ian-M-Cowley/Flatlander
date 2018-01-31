package com.flatlander.flatlander.map.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import butterknife.BindView
import butterknife.ButterKnife
import com.flatlander.flatlander.R
import com.flatlander.flatlander.base.BaseContractActivity
import com.flatlander.flatlander.map.MapContract
import com.flatlander.flatlander.map.interactor.MapInteractor
import com.flatlander.flatlander.map.presenter.MapPresenter
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by iancowley on 1/30/18.
 */
class MapActivity : BaseContractActivity(), MapContract.View {

    @BindView(R.id.map) lateinit var mapView: MapView

    lateinit var presenter: MapContract.Presenter
    lateinit var map: GoogleMap

    companion object {
        private const val EXTRA_MAP_SITE_ITEM = "mapSiteItem"
        private const val REQUEST_LOCATION_PERMISSION = 213

        fun newIntent(caller: Context, mapSiteItem: MapSiteItem): Intent {
            val intent = Intent(caller, MapActivity::class.java)
            intent.putExtra(EXTRA_MAP_SITE_ITEM, mapSiteItem)
            return intent
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_map
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)

        mapView.onCreate(savedInstanceState)

        val mapSiteItem = intent.getSerializableExtra(EXTRA_MAP_SITE_ITEM) as MapSiteItem

        presenter = MapPresenter(this, MapInteractor(), mapSiteItem)
        presenter.onViewAdded()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mapView.onLowMemory()
        super.onLowMemory()
    }

    override fun setLocation(name: String, location: LatLng) {
        mapView.getMapAsync({
            map = it
            map.addMarker(MarkerOptions().position(location).title(name)).showInfoWindow()
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 11.0f))
            map.uiSettings.setAllGesturesEnabled(true)

            val hasLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            if (hasLocationPermission == PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            }
        })
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    map.isMyLocationEnabled = true
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