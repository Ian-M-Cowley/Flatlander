package com.flatlander.flatlander.site.view

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.flatlander.flatlander.R
import com.flatlander.flatlander.base.BaseContractActivity
import com.flatlander.flatlander.model.Category
import com.flatlander.flatlander.model.SiteLite
import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import com.flatlander.flatlander.site.SiteContract
import com.flatlander.flatlander.site.adapter.SiteItemRecyclerAdapter
import com.flatlander.flatlander.site.interactor.SiteInteractor
import com.flatlander.flatlander.site.presenter.SitePresenter
import com.flatlander.flatlander.utils.loadImage

/**
 * Created by iancowley on 9/10/17.
 */
class SiteActivity : BaseContractActivity(), SiteContract.View {

    @BindView(R.id.toolbar) lateinit var toolbar : Toolbar
    @BindView(R.id.image_site) lateinit var siteImage : ImageView
    @BindView(R.id.text_site_name) lateinit var siteName : TextView
    @BindView(R.id.recycler_site_items) lateinit var recyclerView : RecyclerView
    @BindView(R.id.fab_favorite) lateinit var favoriteButton : FloatingActionButton

    lateinit var presenter : SiteContract.Presenter

    private lateinit var siteItemRecyclerAdapter : SiteItemRecyclerAdapter

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_site
    }

    companion object {
        private const val EXTRA_SITE_LITE = "siteLite"
        private const val EXTRA_CATEGORY = "category"

        fun newIntent(caller : Context, category: Category, siteLite: SiteLite) : Intent {
            val intent = Intent(caller, SiteActivity::class.java)
            intent.putExtra(EXTRA_SITE_LITE, siteLite)
            intent.putExtra(EXTRA_CATEGORY, category)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)

        val siteLite = intent.getSerializableExtra(EXTRA_SITE_LITE) as SiteLite
        val category = intent.getSerializableExtra(EXTRA_CATEGORY) as Category

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = SitePresenter(this, SiteInteractor(), siteLite)

        recyclerView.layoutManager = LinearLayoutManager(this)

        favoriteButton.setOnClickListener { presenter.onFavoriteClicked() }
        favoriteButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor(category.backgroundColor))

        presenter.onViewAdded()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setSiteName(name: String) {
        siteName.text = name
    }

    override fun loadSiteImage(url: String) {
        siteImage.loadImage(url, Color.WHITE)
    }

    override fun setSiteItems(siteItems: List<BaseSiteItem>) {
        siteItemRecyclerAdapter = SiteItemRecyclerAdapter(this, siteItems)
        recyclerView.adapter = siteItemRecyclerAdapter
    }

    override fun setFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_heart_white_24dp)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_heart_outline_white_24dp)
        }
    }

    override fun showSnackbar(@StringRes message: Int) {
        Snackbar.make(favoriteButton, message, Snackbar.LENGTH_SHORT).show()
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