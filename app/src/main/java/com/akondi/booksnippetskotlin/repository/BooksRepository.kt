package com.akondi.booksnippetskotlin.repository

import com.akondi.booksnippetskotlin.mvp.model.Book
import io.reactivex.Single

interface BooksRepository {

    fun getBooks(): Single<List<Book>>

    fun insertBook(book: Book)
}