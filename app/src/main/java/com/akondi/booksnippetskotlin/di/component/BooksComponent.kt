package com.akondi.booksnippetskotlin.di.component

import com.akondi.booksnippetskotlin.base.BaseActivity
import com.akondi.booksnippetskotlin.di.module.BooksModule
import com.akondi.booksnippetskotlin.di.scope.PerActivity
import com.akondi.booksnippetskotlin.modules.books.BooksActivity
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@PerActivity
@Subcomponent(modules = arrayOf(BooksModule::class)/*, dependencies = arrayOf(ApplicationComponent::class)*/)
interface BooksComponent {

    fun inject(activity: BooksActivity)
}