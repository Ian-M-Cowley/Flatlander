package com.flatlander.flatlander.data

import io.reactivex.Single

/**
 * Created by iancowley on 3/5/18.
 */
interface SettingsRepository {

    fun getMinimumVersion() : Single<Long>
}