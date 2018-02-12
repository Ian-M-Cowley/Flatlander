package com.flatlander.flatlander.site.adapter.component

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.flatlander.flatlander.R
import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import com.flatlander.flatlander.model.siteitem.TextSiteItem

/**
 * Created by iancowley on 9/13/17.
 */
class TextComponent(itemView: View?) : BaseComponent(itemView) {

    @BindView(R.id.text_title) lateinit var titleText: TextView
    @BindView(R.id.text_description) lateinit var descriptionText: TextView

    init {
        ButterKnife.bind(this, itemView!!)
    }

    override fun bind(siteItem: BaseSiteItem) {
        val textSiteItem = siteItem as TextSiteItem
        titleText.text = textSiteItem.title
        descriptionText.text = textSiteItem.description
        if (textSiteItem.title == null) {
            titleText.visibility = View.GONE
        } else {
            titleText.visibility = View.VISIBLE
        }

        if (SDK_INT >= LOLLIPOP) {
            titleText.letterSpacing = 0.1f
        }
    }
}