package com.flatlander.flatlander.categories.list.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.flatlander.flatlander.R
import com.flatlander.flatlander.base.BaseContractActivity
import com.flatlander.flatlander.categories.detail.view.CategoryDetailActivity
import com.flatlander.flatlander.categories.list.CategoriesContract
import com.flatlander.flatlander.categories.list.adapter.CategoriesRecyclerAdapter
import com.flatlander.flatlander.categories.list.adapter.CategoriesRecyclerAdapter.Listener
import com.flatlander.flatlander.categories.list.interactor.CategoriesInteractor
import com.flatlander.flatlander.categories.list.presenter.CategoriesPresenter
import com.flatlander.flatlander.model.Category

class CategoriesActivity : BaseContractActivity(), CategoriesContract.View {

    @BindView(R.id.recycler_categories) lateinit var categoriesRecycler: RecyclerView
    lateinit var presenter: CategoriesContract.Presenter
    lateinit var categoryRecyclerAdapter: CategoriesRecyclerAdapter

    companion object {

        fun newIntent(caller: Context): Intent {
            return Intent(caller, CategoriesActivity::class.java)
        }
    }

    override @LayoutRes fun getLayoutResourceId(): Int {
        return R.layout.activity_categories
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ButterKnife.bind(this)

        categoriesRecycler.layoutManager = LinearLayoutManager(this)

        presenter = CategoriesPresenter(this, CategoriesInteractor())
        presenter.onViewAdded()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewRemoved()
    }

    override fun goToCategoryDetailScreen(category: Category) {
        startActivity(CategoryDetailActivity.newIntent(this, category))
    }

    override fun setCategories(categories: List<Category>) {
        categoryRecyclerAdapter = CategoriesRecyclerAdapter(this, categories, object : Listener {
            override fun onCategoryClicked(category: Category) {
                presenter.onCategoryClicked(category)
            }
        })
        categoriesRecycler.adapter = categoryRecyclerAdapter
    }

    override fun onErrorDismissed(id: Int) {
        presenter.onErrorDismissed(id)
    }

    override fun onMessagePositive(id: Int) {
        presenter.onMessagePositive(id)
    }

    override fun onMessageNegative(id: Int) {
        presenter.onMessageNegative(id)
    }
}
