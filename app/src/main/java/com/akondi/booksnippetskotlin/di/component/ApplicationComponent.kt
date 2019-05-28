package com.akondi.booksnippetskotlin.di.component

import android.content.Context
import android.content.SharedPreferences
import com.akondi.booksnippetskotlin.di.module.ApplicationModule
import com.akondi.booksnippetskotlin.di.module.BooksModule
import com.akondi.booksnippetskotlin.di.module.CarModule
import com.akondi.booksnippetskotlin.di.module.WebServiceModule
import dagger.Component
import io.reactivex.Scheduler
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, WebServiceModule::class))
interface ApplicationComponent {

    fun exposeSharedPreferences(): SharedPreferences

    fun exposeScheduler(): Scheduler

    fun exposeContext(): Context

    fun exposeRetrofit() : Retrofit

    fun plus (booksModule: BooksModule): BooksComponent

    fun plus (carModule: CarModule): CarComponent
}