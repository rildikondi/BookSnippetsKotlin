package com.akondi.booksnippetskotlin.modules.launcher

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.akondi.booksnippetskotlin.R
import com.akondi.booksnippetskotlin.USER_LOGGED_IN
import com.akondi.booksnippetskotlin.application.BookSnippetsApp
import com.akondi.booksnippetskotlin.modules.books.BooksActivity
import com.akondi.booksnippetskotlin.base.BaseActivity
import com.akondi.booksnippetskotlin.modules.car.CarActivity
import javax.inject.Inject

class LauncherActivity : BaseActivity() {

    override fun getContentView(): Int = R.layout.activity_launcher


    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (prefs.getBoolean(USER_LOGGED_IN, false)) {
        startActivity(Intent(this, CarActivity::class.java))
//        }
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)
    }
}
