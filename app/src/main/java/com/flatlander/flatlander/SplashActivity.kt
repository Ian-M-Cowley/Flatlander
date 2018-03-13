package com.flatlander.flatlander

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.flatlander.flatlander.categories.list.view.CategoriesActivity
import com.flatlander.flatlander.data.SharedPrefs
import com.flatlander.flatlander.intro.view.IntroActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (SharedPrefs.instance.hasSeenIntro()) {
            startActivity(CategoriesActivity.newIntent(this))
        } else {
            startActivity(IntroActivity.newIntent(this))
        }
        finish()
    }
}
