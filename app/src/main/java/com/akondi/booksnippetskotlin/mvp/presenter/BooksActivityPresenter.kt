package com.akondi.booksnippetskotlin.mvp.presenter

import com.akondi.booksnippetskotlin.base.BasePresenter
import com.akondi.booksnippetskotlin.mvp.model.Book
import com.akondi.booksnippetskotlin.mvp.view.BooksActivityView
import com.akondi.booksnippetskotlin.repository.BooksRepository
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BooksActivityPresenter @Inject constructor (val booksRepository: BooksRepository, val mainScheduler: Scheduler)
    : BasePresenter<BooksActivityView>() {

    fun saveBook(book: Book) {
        booksRepository.insertBook(book)
        view.notifyBookSaved()
    }

    fun loadBooks() {
        booksRepository.getBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(mainScheduler)
            .doOnSubscribe({addToCompositeDisposable(it)})
            .subscribe({shouldDisplayBooks(it)},
                {shouldDisplayError(it)})
    }

    private fun shouldDisplayError(throwable: Throwable) {
        view.displayError()
    }

    private fun shouldDisplayBooks(bookList: List<Book>) {
        println("Thread subsribe: " + Thread.currentThread().id)
        if (!bookList.isEmpty())
            view.displayBooks(bookList)
        else
            view.displayNoBooks()
    }
}