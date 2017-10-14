package com.flatlander.flatlander.data

import com.flatlander.flatlander.R
import com.flatlander.flatlander.model.Category
import com.flatlander.flatlander.model.SiteLite
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Single

/**
 * Created by iancowley on 9/20/17.
 */
class FirebaseCategoriesRepository private constructor() : CategoriesRepository  {

    private object Holder { val INSTANCE = FirebaseCategoriesRepository() }

    companion object {
        private val TAG = FirebaseCategoriesRepository::class.java.simpleName
        val instance: FirebaseCategoriesRepository by lazy { Holder.INSTANCE }
    }

    override fun getCategories(): Single<List<Category>> {
        val hiking = Category("hiking", "Hiking", R.drawable.ic_hiking, "#EB6132")
        val camping = Category("camping", "Camping", R.drawable.ic_camping, "#2A723D")
        val swimming = Category("swimming", "Swimming", R.drawable.ic_swimming, "#0095BC")
        val favorites = Category("favorites", "Favorites", R.drawable.ic_favorites, "#EF9E29")

        val categories = listOf(hiking, camping, swimming, favorites)
        return Single.just(categories)
    }

    override fun getSitesForCategory(category: Category): Single<List<SiteLite>> {
        return Single.create(DataOnSubscribe(FirebaseDatabase.getInstance().getReference("categories").child(category.id)))
                .map({
                    if (it.exists()) {
                        val siteLites = mutableListOf<SiteLite>()
                        it.children.mapTo(siteLites) { it.getValue(SiteLite::class.java)!! }
                        return@map siteLites
                    } else {
                        return@map emptyList<SiteLite>()
                    }
                })
    }
}