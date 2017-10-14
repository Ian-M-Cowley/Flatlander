package com.flatlander.flatlander.categories.detail

import android.support.annotation.ColorInt
import com.flatlander.flatlander.base.BaseContract
import com.flatlander.flatlander.model.Category
import com.flatlander.flatlander.model.SiteLite
import io.reactivex.Single

/**
 * Created by iancowley on 8/24/17.
 */
interface CategoryDetailContract {

    interface Interactor {
        fun getSitesForCategory(category: Category) : Single<List<SiteLite>>
    }

    interface View : BaseContract.View {
        fun goToSiteScreen(siteLite: SiteLite)

        fun setSites(sites: List<SiteLite>)
        fun setHeaderColor(@ColorInt color: Int)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onSiteClicked(site : SiteLite)
    }
}