package com.flatlander.flatlander

import android.app.Application
import com.flatlander.flatlander.data.LocalFavoritesRepository
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


/**
 * Created by iancowley on 1/28/18.
 */
class FlatlanderApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalFavoritesRepository.instance.init(this)
        CalligraphyConfig.initDefault(CalligraphyConfig.get())
    }
}