package com.flatlander.flatlander.site.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.flatlander.flatlander.R
import com.flatlander.flatlander.analytics.*
import com.flatlander.flatlander.base.BaseContractActivity
import com.flatlander.flatlander.map.view.MapActivity
import com.flatlander.flatlander.model.Category
import com.flatlander.flatlander.model.SiteLite
import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import com.flatlander.flatlander.site.SiteContract
import com.flatlander.flatlander.site.adapter.SiteItemRecyclerAdapter
import com.flatlander.flatlander.site.interactor.SiteInteractor
import com.flatlander.flatlander.site.presenter.SitePresenter
import com.flatlander.flatlander.utils.loadImage
import com.google.firebase.analytics.FirebaseAnalytics
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import uk.co.chrisjenx.calligraphy.CalligraphyUtils


/**
 * Created by iancowley on 9/10/17.
 */
class SiteActivity : BaseContractActivity(), SiteContract.View {

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.image_site)  lateinit var siteImage: ImageView
    @BindView(R.id.text_site_name) lateinit var siteName: TextView
    @BindView(R.id.recycler_site_items) lateinit var recyclerView: RecyclerView
    @BindView(R.id.fab_favorite) lateinit var favoriteButton: FloatingActionButton

    lateinit var presenter: SiteContract.Presenter

    private lateinit var siteItemRecyclerAdapter: SiteItemRecyclerAdapter
    private var isFavorite: Boolean = false
    private val analyticsParams = Bundle()

    companion object {
        private const val EXTRA_SITE_LITE = "siteLite"
        private const val EXTRA_CATEGORY = "category"

        fun newIntent(caller: Context, category: Category, siteLite: SiteLite): Intent {
            val intent = Intent(caller, SiteActivity::class.java)
            intent.putExtra(EXTRA_SITE_LITE, siteLite)
            intent.putExtra(EXTRA_CATEGORY, category)
            return intent
        }
    }

    @LayoutRes override fun getLayoutResourceId(): Int {
        return R.layout.activity_site
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)

        if (SDK_INT >= LOLLIPOP) {
            siteName.letterSpacing = 0.25f
        }

        val siteLite = intent.getSerializableExtra(EXTRA_SITE_LITE) as SiteLite
        val category = intent.getSerializableExtra(EXTRA_CATEGORY) as Category

        analyticsParams.putString(PARAM_SITE, siteLite.name)
        analyticsParams.putString(PARAM_CATEGORY, category.name)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = SitePresenter(this, SiteInteractor(), siteLite)

        recyclerView.layoutManager = LinearLayoutManager(this)

        favoriteButton.setOnClickListener {
            val event = if (isFavorite) {
                EVENT_SITE_UN_FAVORITED
            } else {
                EVENT_SITE_FAVORITED
            }
            FirebaseAnalytics.getInstance(this).logEvent(event, analyticsParams)

            presenter.onFavoriteClicked()
        }
        favoriteButton.hide()

        presenter.onViewAdded()

        if (savedInstanceState == null) {
            FirebaseAnalytics.getInstance(this).logEvent(EVENT_SITE_VIEW, analyticsParams)
        }
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

    override fun goToMapScreen(mapSiteItem: MapSiteItem) {
        FirebaseAnalytics.getInstance(this).logEvent(EVENT_MAP_VIEW, analyticsParams)
        startActivity(MapActivity.newIntent(this, mapSiteItem, analyticsParams))
    }

    override fun setSiteName(name: String) {
        siteName.text = name
    }

    override fun loadSiteImage(url: String) {
        siteImage.loadImage(url, resources.getColor(R.color.creamyWhite))
    }

    override fun setSiteItems(siteItems: List<BaseSiteItem>) {
        siteItemRecyclerAdapter = SiteItemRecyclerAdapter(this, siteItems, object : SiteItemRecyclerAdapter.Listener {
            override fun onMapItemSelected(mapSiteItem: MapSiteItem) {
                presenter.onMapItemSelected(mapSiteItem)
            }
        })
        recyclerView.adapter = siteItemRecyclerAdapter
    }

    override fun notifySiteItemsChanged() {
        siteItemRecyclerAdapter.notifyDataSetChanged()
    }

    override fun setFavorite(isFavorite: Boolean) {
        this.isFavorite = isFavorite
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_heart_creamy_white_24dp)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_heart_outline_creamy_white_24dp)
        }
        favoriteButton.show()
    }

    override fun showSnackbar(@StringRes message: Int) {
        val snackbar = Snackbar.make(favoriteButton, message, Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(resources.getColor(R.color.favoritesYellow))
        val text = snackbar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text)

        CalligraphyUtils.applyFontToTextView(this, text, "fonts/highway-gothic-wide.ttf")
        text.setTextColor(Color.WHITE)
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)

        if (SDK_INT >= LOLLIPOP) {
            text.letterSpacing = 0.1f
        }

        snackbar.show()
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