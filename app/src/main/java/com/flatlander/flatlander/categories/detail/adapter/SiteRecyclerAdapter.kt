package com.flatlander.flatlander.categories.detail.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.flatlander.flatlander.R
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
        viewHolder.siteImage.loadImage(site.imageUrl, Color.WHITE)
    }

    override fun getItemCount(): Int {
        return sites.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val siteImage : ImageView = view.image_site
        val siteNameText : TextView = view.text_site_name
    }

    interface Listener {
        fun onSiteClicked(site: SiteLite)
    }
}