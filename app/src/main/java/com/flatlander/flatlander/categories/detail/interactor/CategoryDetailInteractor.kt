package com.flatlander.flatlander.categories.detail.interactor

import com.flatlander.flatlander.categories.detail.CategoryDetailContract
import com.flatlander.flatlander.data.CategoriesRepository
import com.flatlander.flatlander.data.FirebaseCategoriesRepository
import com.flatlander.flatlander.model.Category
import com.flatlander.flatlander.model.SiteLite
import io.reactivex.Single

/**
 * Created by iancowley on 8/24/17.
 */
class CategoryDetailInteractor : CategoryDetailContract.Interactor {

    private val dataSource : CategoriesRepository = FirebaseCategoriesRepository.instance

    override fun getSitesForCategory(category: Category): Single<List<SiteLite>> {
        return dataSource.getSitesForCategory(category)
    }
}