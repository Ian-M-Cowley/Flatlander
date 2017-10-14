package com.flatlander.flatlander.data

import com.flatlander.flatlander.model.Site
import com.flatlander.flatlander.model.siteitem.ImageSiteItem
import com.flatlander.flatlander.model.siteitem.MapSiteItem
import io.reactivex.Single

/**
 * Created by iancowley on 8/24/17.
 */
class TestSiteRepository : SiteRepository {

    private object Holder { val INSTANCE = TestSiteRepository() }

    companion object {
        val instance: TestSiteRepository by lazy { Holder.INSTANCE }
    }

    override fun getSiteById(id: String): Single<Site> {
        val siteItem1 = MapSiteItem(0, "map", 30.180544f, -97.721697f, "Mckinney Falls", "1234")
        val siteItem2 = ImageSiteItem(1, "image", "https://i.imgur.com/Sl1708Kh.jpg", 4928f, 3264f)
        val siteItem3 = ImageSiteItem(1, "image", "https://i.imgur.com/izA16IEh.jpg", 3264f, 4928f)
        val siteItem4 = ImageSiteItem(1, "image", "https://i.imgur.com/GQyXyg0h.jpg", 3264f, 4928f)

        val site = Site()
        site.id = "1"
        site.name = "Mckinney Falls"
        site.imageUrl = "https://i.imgur.com/Sl1708Kh.jpg"
        site.title = "Hiking At Mckinney"
        site.description = "Mckinney Falls is one of our favorite places to hike. Just 20 minutes from downtown, it's a real Austin gem. Mckinney has hiking and swimming. Be sure to check out the rock tunnel and the wooden bridge. Mckinney Falls is one of our favorite places to hike. Just 20 minutes from downtown, its a real Austin gem. Mckinney has hiking and swimming. Be sure to check out the rock tunnel and the wooden bridge."
        site.siteItems = listOf(siteItem1, siteItem2, siteItem3, siteItem4)
        return Single.just(site)
    }
}