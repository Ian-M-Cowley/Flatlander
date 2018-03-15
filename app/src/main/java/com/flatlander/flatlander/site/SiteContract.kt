package com.flatlander.flatlander.site

import android.support.annotation.StringRes
import com.flatlander.flatlander.base.BaseContract
import com.flatlander.flatlander.model.Site
import com.flatlander.flatlander.model.SiteLite
import com.flatlander.flatlander.model.siteitem.BaseSiteItem
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import io.reactivex.Single

/**
 * Created by iancowley on 8/28/17.
 */
interface SiteContract {

    interface Interactor {
        fun getSite(siteId: String): Single<Site>

        fun isSiteFavorite(siteLite: SiteLite): Boolean
        fun favoriteSite(siteLite: SiteLite): Single<Boolean>
        fun unfavoriteSite(siteLite: SiteLite): Single<Boolean>
    }

    interface View : BaseContract.View {

        fun goToMapScreen(mapSiteItem: MapSiteItem)

        fun setSiteItems(siteItems: List<BaseSiteItem>)

        fun notifySiteItemsChanged()

        fun setSiteName(name: String)

        fun loadSiteImage(url: String)

        fun setSiteDistance(distance: String)

        fun setFavorite(isFavorite: Boolean)

        fun showSnackbar(@StringRes message: Int)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onFavoriteClicked()
        fun onMapItemSelected(mapSiteItem: MapSiteItem)
    }
}