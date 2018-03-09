package com.flatlander.flatlander.site.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.ViewGroup
import com.flatlander.flatlander.R
import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import com.flatlander.flatlander.site.adapter.component.BaseComponent
import com.flatlander.flatlander.site.adapter.component.ImageComponent
import com.flatlander.flatlander.site.adapter.component.MapComponent
import com.flatlander.flatlander.site.adapter.component.TextComponent

/**
 * Created by iancowley on 9/13/17.
 */
class SiteItemRecyclerAdapter(private val context : Context,
                              private val siteItems: List<BaseSiteItem>,
                              private val listener: Listener) : RecyclerView.Adapter<BaseComponent>() {

    private val inflater : LayoutInflater = LayoutInflater.from(context)

    companion object {
        private const val TYPE_INVALID = -1
        private const val TYPE_IMAGE = 0
        private const val TYPE_TEXT = 1
        private const val TYPE_MAP = 2
    }

    override fun getItemViewType(position: Int): Int {
        val item = siteItems[position]
        return when (item.type) {
            BaseSiteItem.TYPE_IMAGE -> TYPE_IMAGE
            BaseSiteItem.TYPE_TEXT -> TYPE_TEXT
            BaseSiteItem.TYPE_MAP -> TYPE_MAP
            else -> TYPE_INVALID
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseComponent? {
        return when (viewType) {
            TYPE_IMAGE -> ImageComponent(inflater.inflate(R.layout.item_image_component, parent, false))
            TYPE_TEXT -> TextComponent(inflater.inflate(R.layout.item_text_component, parent, false))
            TYPE_MAP -> MapComponent(inflater.inflate(R.layout.item_map_component, parent, false))
            else -> null
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: BaseComponent?, position: Int) {
        val siteItem = siteItems[position]

        holder?.bind(siteItem)

        if (siteItem is MapSiteItem) {
            val mapComponent = holder as MapComponent
            mapComponent.touchShield.tag = siteItem
            mapComponent.touchShield.setOnTouchListener(View.OnTouchListener { v, event ->
                when(event.action) {
                    ACTION_UP -> {
                        listener.onMapItemSelected(v.tag as MapSiteItem)
                    }
                }
                return@OnTouchListener true
            })
        }
    }

    override fun getItemCount(): Int {
        return siteItems.size
    }

    interface Listener {
        fun onMapItemSelected(mapSiteItem: MapSiteItem)
    }

}