package com.flatlander.flatlander.site.adapter.component

import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.flatlander.flatlander.R
import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import com.flatlander.flatlander.model.siteitem.ImageSiteItem
import com.flatlander.flatlander.utils.loadImage

/**
 * Created by iancowley on 9/13/17.
 */
class ImageComponent(itemView: View?) : BaseComponent(itemView) {

    @BindView(R.id.image_item) lateinit var itemImage : ImageView

    init {
        ButterKnife.bind(this, itemView!!)
    }

    override fun bind(siteItem: BaseSiteItem) {
        val imageSiteItem = siteItem as ImageSiteItem

        itemImage.loadImage(imageSiteItem.imageUrl, itemView.context.resources.getColor(R.color.creamyWhite))
    }
}