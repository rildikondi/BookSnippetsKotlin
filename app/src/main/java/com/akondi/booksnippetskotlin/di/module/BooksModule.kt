package com.akondi.booksnippetskotlin.di.module

import android.content.Context
import com.akondi.booksnippetskotlin.repository.impl.DBHelper
import com.akondi.booksnippetskotlin.di.scope.PerActivity
import com.akondi.booksnippetskotlin.mvp.view.BooksActivityView
import com.akondi.booksnippetskotlin.repository.BooksRepository
import dagger.Module
import dagger.Provides

@Module
class BooksModule(val booksActivityView: BooksActivityView) {

    @Provides
    @PerActivity
    fun provideBooksActivityView() = booksActivityView


    @Provides
    @PerActivity
    fun provideBooksRepository(context: Context) = DBHelper(context) as BooksRepository
}