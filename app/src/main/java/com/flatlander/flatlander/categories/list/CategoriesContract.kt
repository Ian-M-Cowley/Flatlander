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
    }

    interface View : BaseContract.View {
        fun goToCategoryDetailScreen(category: Category)

        fun setCategories(categories: List<Category>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun onCategoryClicked(category : Category)
    }
}