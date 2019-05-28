package com.akondi.booksnippetskotlin.di.module

import android.content.Context
import com.akondi.booksnippetskotlin.application.BookSnippetsApp
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module
class ApplicationModule(val app: BookSnippetsApp) {

    @Provides
    @Singleton
    fun provideContext() = app as Context

    @Provides
    @Singleton
    fun provideScheduler() = AndroidSchedulers.mainThread() as Scheduler

    @Provides
    @Singleton
    fun provideSharedPrefs() = app.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
}