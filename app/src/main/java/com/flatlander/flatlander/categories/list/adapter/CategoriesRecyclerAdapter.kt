package com.flatlander.flatlander.categories.list.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.flatlander.flatlander.R
import com.flatlander.flatlander.model.Category
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by iancowley on 7/31/17.
 */
class CategoriesRecyclerAdapter(context: Context, val categories: List<Category>, val listener: Listener) : RecyclerView.Adapter<CategoriesRecyclerAdapter.ViewHolder>() {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater.inflate(R.layout.item_category, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        val category = categories[position]
        bind(viewHolder!!, category)
        viewHolder.itemView.tag = category
        viewHolder.itemView.setOnClickListener({ v -> listener.onCategoryClicked(v.tag as Category) })
    }

    private fun bind(viewHolder: ViewHolder, category: Category) {
        viewHolder.image.setImageResource(category.imageId)
        viewHolder.image.setBackgroundColor(Color.parseColor(category.backgroundColor))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image : ImageView = view.image_category
    }

    interface Listener {
        fun onCategoryClicked(category: Category)
    }
}