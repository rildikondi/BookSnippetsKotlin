package com.akondi.booksnippetskotlin.mvp.view

import com.akondi.booksnippetskotlin.base.BaseView
import com.akondi.booksnippetskotlin.mvp.model.Book

interface BooksActivityView : BaseView{

    fun displayBooks(books: List<Book>)

    fun displayNoBooks()

    fun notifyBookSaved()

    fun displayError()
}