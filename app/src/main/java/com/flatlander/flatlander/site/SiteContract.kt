package com.flatlander.flatlander.site

import com.flatlander.flatlander.base.BaseContract
import com.flatlander.flatlander.model.Site
import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import io.reactivex.Single

/**
 * Created by iancowley on 8/28/17.
 */
interface SiteContract {

    interface Interactor {
        fun getSite(siteId: String): Single<Site>
    }

    interface View : BaseContract.View {
        fun setSiteItems(siteItems: List<BaseSiteItem>)

        fun setSiteName(name: String)

        fun loadSiteImage(url: String)

        fun setHeaderTitle(title: String)

        fun setHeaderDescription(description: String)

        fun setFavorite(isFavorite : Boolean)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onFavoriteClicked()
    }
}