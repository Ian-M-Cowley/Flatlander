package com.flatlander.flatlander.data

import com.flatlander.flatlander.model.Category
import com.flatlander.flatlander.model.SiteLite
import io.reactivex.Single

/**
 * Created by iancowley on 8/24/17.
 */
interface CategoriesRepository {

    fun getCategories(): Single<List<Category>>

    fun getSitesForCategory(category: Category): Single<List<SiteLite>>
}