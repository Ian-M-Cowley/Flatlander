package com.flatlander.flatlander.categories.list.interactor

import com.flatlander.flatlander.categories.list.CategoriesContract
import com.flatlander.flatlander.data.CategoriesRepository
import com.flatlander.flatlander.data.FirebaseCategoriesRepository
import com.flatlander.flatlander.model.Category
import io.reactivex.Single

/**
 * Created by iancowley on 8/10/17.
 */
class CategoriesInteractor : CategoriesContract.Interactor {

    private val dataSource : CategoriesRepository = FirebaseCategoriesRepository.instance

    override fun getCategories(): Single<List<Category>> {
        return dataSource.getCategories()
    }
}