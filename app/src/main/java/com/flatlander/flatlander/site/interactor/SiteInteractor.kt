package com.flatlander.flatlander.site.interactor

import com.flatlander.flatlander.data.TestSiteRepository
import com.flatlander.flatlander.model.Site
import com.flatlander.flatlander.site.SiteContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by iancowley on 8/28/17.
 */
class SiteInteractor : SiteContract.Interactor {

    override fun getSite(siteId: String) : Single<Site> {
        return TestSiteRepository.instance.getSiteById(siteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}