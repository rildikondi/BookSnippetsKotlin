package com.akondi.booksnippetskotlin.di.component

import com.akondi.booksnippetskotlin.di.module.CarModule
import com.akondi.booksnippetskotlin.di.scope.PerActivity
import com.akondi.booksnippetskotlin.modules.car.CarActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(CarModule::class)/*, dependencies = arrayOf(ApplicationComponent::class)*/)
interface CarComponent {

    fun inject(activity: CarActivity)
}