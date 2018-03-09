package com.flatlander.flatlander.data

import com.flatlander.flatlander.R
import com.flatlander.flatlander.model.Category
import com.flatlander.flatlander.model.SiteLite
import io.reactivex.Single

/**
 * Created by iancowley on 8/24/17.
 */
class TestCategoriesRepository private constructor() : CategoriesRepository {

    private object Holder {
        val INSTANCE = TestCategoriesRepository()
    }

    companion object {
        val instance: TestCategoriesRepository by lazy { Holder.INSTANCE }
    }

    override fun getCategories(): Single<List<Category>> {
        val hiking = Category("hiking", "Hiking", R.drawable.ic_hiking, "#EB6132")
        val camping = Category("camping", "Camping", R.drawable.ic_camping, "#2A723D")
        val swimming = Category("swimming", "Swimming", R.drawable.ic_swimming, "#0095BC")
        val favorites = Category("3", "Favorites", R.drawable.ic_favorites, "#EF9E29")

        val categories = listOf(hiking, camping, swimming, favorites)
        return Single.just(categories)
    }

    override fun getSitesForCategory(category: Category): Single<List<SiteLite>> {
//        val site1 = SiteLite("1", "McKinney Falls", "https://i.imgur.com/Sl1708Kh.jpg")
//        val site2 = SiteLite("2", "Walnut Creek Park", "https://i.imgur.com/4Yme6gIh.jpg")
//        val site3 = SiteLite("3", "Pedernales Falls State Park", "https://i.imgur.com/29g99Dfh.jpg")
//        val site4 = SiteLite("4", "Garner State Park", "https://i.imgur.com/oXBtgiUh.jpg")
//        val site5 = SiteLite("5", "Kraus Springs", "https://i.imgur.com/pJ1JhA4h.jpg")
//        val site6 = SiteLite("6", "Balcones Canyonlands Wilderness Preserve", "https://i.imgur.com/8PzQEc4h.jpg")
        return Single.just(listOf())//site1, site2, site3, site4, site5, site6))
    }
}