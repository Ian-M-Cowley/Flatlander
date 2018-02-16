package com.flatlander.flatlander.categories.detail.adapter

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.flatlander.flatlander.R
import com.flatlander.flatlander.data.LocalFavoritesRepository
import com.flatlander.flatlander.model.SiteLite
import com.flatlander.flatlander.utils.loadImage
import kotlinx.android.synthetic.main.item_site.view.*

/**
 * Created by iancowley on 8/24/17.
 */
class SiteRecyclerAdapter(context: Context, val sites: List<SiteLite>, val listener: Listener) : RecyclerView.Adapter<SiteRecyclerAdapter.ViewHolder>() {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.item_site, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        val site = sites[position]
        bind(viewHolder!!, site)
        viewHolder.itemView.tag = site
        viewHolder.itemView.setOnClickListener({ v -> listener.onSiteClicked(v.tag as SiteLite) })
    }

    private fun bind(viewHolder: ViewHolder, site: SiteLite) {
        viewHolder.siteNameText.text = site.name
        if (SDK_INT >= LOLLIPOP) {
            viewHolder.siteNameText.letterSpacing = 0.1f
        }
        viewHolder.siteImage.loadImage(site.imageUrl, viewHolder.itemView.context.resources.getColor(R.color.brownBlack))

        if (LocalFavoritesRepository.instance.isSiteFavorite(site)) {
            viewHolder.siteCategoryText.text = site.category
        }
    }

    override fun getItemCount(): Int {
        return sites.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val siteImage : ImageView = view.image_site
        val siteNameText : TextView = view.text_site_name
        val siteCategoryText: TextView = view.text_site_category
    }

    interface Listener {
        fun onSiteClicked(site: SiteLite)
    }
}