package com.akondi.booksnippetskotlin.modules.books

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.akondi.booksnippetskotlin.R
import com.akondi.booksnippetskotlin.base.BaseActivity
import com.akondi.booksnippetskotlin.di.module.BooksModule
import com.akondi.booksnippetskotlin.mvp.model.Book
import com.akondi.booksnippetskotlin.mvp.presenter.BooksActivityPresenter
import com.akondi.booksnippetskotlin.mvp.view.BooksActivityView
import kotlinx.android.synthetic.main.activity_books.*
import javax.inject.Inject

class BooksActivity : BaseActivity(), BooksActivityView {
    override fun displayError() {
        Toast.makeText(this, "Error occurred !!!", Toast.LENGTH_SHORT).show()

    }

    override fun onShowToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG: String = "BooksActivity"
    }

    override fun displayNoBooks() {
        Toast.makeText(this, "found no book", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "displayNoBooks: found no book")
    }

    override fun notifyBookSaved() {
        Toast.makeText(this, "Book saved successfully", Toast.LENGTH_SHORT).show()
    }

    override fun displayBooks(books: List<Book>) {
        Toast.makeText(this, "found some books", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "displayBooks: found some books")
    }

    override fun getContentView(): Int = R.layout.activity_books

    @Inject
    lateinit var presenter: BooksActivityPresenter


    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)
        presenter.loadBooks()

        btn_insertBook.setOnClickListener {
            presenter.saveBook(
                Book(
                    ProductId = "Castle(Drum of rain)",
                    ProductName = "Kadare",
                    Quantity = "3",
                    Price = "25$",
                    Discount = "15%"
                )
            )
        }
    }

    override fun resolveDaggerDependency() {

        val component by lazy { app.mApplicationComponent.plus(BooksModule(this)) }
        component.inject(this)
//        DaggerBooksComponent.builder()
//            .applicationComponent(getApplicationComponent())
//            .booksModule(BooksModule(this))
//            .build().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearCompositeDisposable()
    }
}