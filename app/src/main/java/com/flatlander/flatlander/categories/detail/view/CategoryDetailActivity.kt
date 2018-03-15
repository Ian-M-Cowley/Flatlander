package com.flatlander.flatlander.categories.detail.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.flatlander.flatlander.R
import com.flatlander.flatlander.analytics.EVENT_CATEGORY_VIEW
import com.flatlander.flatlander.analytics.PARAM_CATEGORY
import com.flatlander.flatlander.base.BaseContractActivity
import com.flatlander.flatlander.categories.detail.CategoryDetailContract
import com.flatlander.flatlander.categories.detail.adapter.SiteRecyclerAdapter
import com.flatlander.flatlander.categories.detail.interactor.CategoryDetailInteractor
import com.flatlander.flatlander.categories.detail.presenter.CategoryDetailPresenter
import com.flatlander.flatlander.model.Category
import com.flatlander.flatlander.model.SiteLite
import com.flatlander.flatlander.site.view.SiteActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.analytics.FirebaseAnalytics
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*

/**
 * Created by iancowley on 8/24/17.
 */
class CategoryDetailActivity : BaseContractActivity(), CategoryDetailContract.View {

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.background) lateinit var background: View
    @BindView(R.id.header_bar) lateinit var headerView: View
    @BindView(R.id.recycler_sites) lateinit var sitesRecycler: RecyclerView
    @BindView(R.id.layout_favorites_empty) lateinit var favoritesEmptyLayout: View
    @BindView(R.id.text_favorites_empty_message) lateinit var favoritesEmptyMessage: TextView

    lateinit var presenter: CategoryDetailContract.Presenter
    private lateinit var siteRecyclerAdapter: SiteRecyclerAdapter
    private lateinit var category: Category
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var userLocation: Location? = null

    companion object {

        private const val EXTRA_CATEGORY = "category"
        private const val REQUEST_SITE = 123

        fun newIntent(caller: Context, category: Category): Intent {
            val intent = Intent(caller, CategoryDetailActivity::class.java)
            intent.putExtra(EXTRA_CATEGORY, category)
            return intent
        }
    }

    @LayoutRes
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_category_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)

        category = intent.getSerializableExtra(EXTRA_CATEGORY) as Category

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        sitesRecycler.layoutManager = LinearLayoutManager(this)

        if (SDK_INT >= LOLLIPOP) {
            favoritesEmptyMessage.letterSpacing = 0.1f
        }

        presenter = CategoryDetailPresenter(this, CategoryDetailInteractor(), category)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                userLocation = location
                presenter.onViewAdded()
            }
        } else {
            presenter.onViewAdded()
        }

        if (savedInstanceState == null) {
            val params = Bundle()
            params.putString(PARAM_CATEGORY, category.name)
            FirebaseAnalytics.getInstance(this).logEvent(EVENT_CATEGORY_VIEW, params)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewRemoved()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SITE) {
            presenter.onViewReturnedTo()
        }
    }

    override fun goToSiteScreen(category: Category, siteLite: SiteLite) {
        startActivityForResult(SiteActivity.newIntent(this, category, siteLite), REQUEST_SITE)
    }

    override fun setSites(sites: List<SiteLite>) {
        if (userLocation != null) {
            val userLocationLatLng = LatLng(userLocation!!.latitude, userLocation!!.longitude)
            val results = floatArrayOf(0f)
            for (site in sites) {
                val siteLocation = LatLng(site.defaultMapSiteItem!!.lat.toDouble(), site.defaultMapSiteItem!!.long.toDouble())
                Location.distanceBetween(siteLocation.latitude, siteLocation.longitude, userLocationLatLng.latitude, userLocationLatLng.longitude, results)
                site.distance = (results[0]/1609.34f).toInt()
            }
            Collections.sort(sites, { site1, site2 -> site1.distance - site2.distance })
        }

        siteRecyclerAdapter = SiteRecyclerAdapter(this, category, sites, object : SiteRecyclerAdapter.Listener {
            override fun onSiteClicked(site: SiteLite) {
                presenter.onSiteClicked(site)
            }
        })
        sitesRecycler.adapter = siteRecyclerAdapter
    }

    override fun setHeaderColor(@ColorInt color: Int) {
        background.setBackgroundColor(color)
        headerView.setBackgroundColor(color)
    }

    override fun showFavoritesEmptyState(show: Boolean) {
        if (show) {
            favoritesEmptyLayout.visibility = VISIBLE
        } else {
            favoritesEmptyLayout.visibility = GONE
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