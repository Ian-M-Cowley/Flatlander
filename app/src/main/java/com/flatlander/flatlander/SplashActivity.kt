package com.flatlander.flatlander

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.flatlander.flatlander.categories.list.view.CategoriesActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(CategoriesActivity.newIntent(this))
        finish()
    }
}
