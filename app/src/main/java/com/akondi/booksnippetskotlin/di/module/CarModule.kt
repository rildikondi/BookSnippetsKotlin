package com.akondi.booksnippetskotlin.di.module

import com.akondi.booksnippetskotlin.api.CarApiService
import com.akondi.booksnippetskotlin.di.scope.PerActivity
import com.akondi.booksnippetskotlin.mvp.view.CarActivityView
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CarModule (val carActivityView: CarActivityView) {

    @PerActivity
    @Provides
    fun providesCarApiService(retrofit: Retrofit): CarApiService {
        return retrofit.create<CarApiService>(CarApiService::class.java)
    }

    @PerActivity
    @Provides
    fun providesCarActivityView(): CarActivityView {
        return carActivityView
    }
}