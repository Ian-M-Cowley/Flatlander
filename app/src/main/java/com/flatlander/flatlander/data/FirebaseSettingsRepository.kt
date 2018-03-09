package com.flatlander.flatlander.data

import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Single

/**
 * Created by iancowley on 3/5/18.
 */
class FirebaseSettingsRepository: SettingsRepository {

    private object Holder { val INSTANCE = FirebaseSettingsRepository() }

    companion object {
        private val TAG = FirebaseSettingsRepository::class.java.simpleName
        val instance: FirebaseSettingsRepository by lazy { Holder.INSTANCE }
    }

    override fun getMinimumVersion(): Single<Long> {
        return Single.create(DataOnSubscribe(FirebaseDatabase.getInstance().getReference("settings").child("minimum_android_version_code")))
                .map({
                    if (it.exists()) {
                        return@map it.value as Long
                    } else {
                        return@map 0L
                    }
                })
    }

}