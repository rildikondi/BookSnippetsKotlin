package com.akondi.booksnippetskotlin.application

import android.app.Application
import com.akondi.booksnippetskotlin.di.component.ApplicationComponent
import com.akondi.booksnippetskotlin.di.component.DaggerApplicationComponent
import com.akondi.booksnippetskotlin.di.module.ApplicationModule
import com.akondi.booksnippetskotlin.di.module.WebServiceModule
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import leakcanary.LeakSentry

class BookSnippetsApp : Application() {

    val mApplicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .webServiceModule(WebServiceModule("http://redirect.mytaxi.net"))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        initializeFresco()
        LeakSentry.config = LeakSentry.config.copy(watchFragmentViews = false)
        //mApplicationComponent.inject(this)
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    fun initializeFresco() {
        val config: ImagePipelineConfig = ImagePipelineConfig.newBuilder(this)
            .setDownsampleEnabled(true)
            .build()
        Fresco.initialize(this, config)
    }
}