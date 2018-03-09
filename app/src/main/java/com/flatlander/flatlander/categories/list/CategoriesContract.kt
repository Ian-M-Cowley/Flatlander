package com.flatlander.flatlander.categories.list

import com.flatlander.flatlander.base.BaseContract
import com.flatlander.flatlander.model.Category
import io.reactivex.Single

/**
 * Created by iancowley on 8/10/17.
 */
interface CategoriesContract {

    interface Interactor {
        fun getCategories() : Single<List<Category>>
        fun getMinimumVersion() : Single<Long>
    }

    interface View : BaseContract.View {

        fun close()

        fun goToCategoryDetailScreen(category: Category)

        fun setCategories(categories: List<Category>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onCategoryClicked(category : Category)
    }
}