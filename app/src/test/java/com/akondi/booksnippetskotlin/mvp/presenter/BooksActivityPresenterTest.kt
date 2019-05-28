package com.akondi.booksnippetskotlin.mvp.presenter

import com.akondi.booksnippetskotlin.mvp.model.Book
import com.akondi.booksnippetskotlin.mvp.view.BooksActivityView
import com.akondi.booksnippetskotlin.repository.BooksRepository
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import java.util.*

class BooksActivityPresenterTest {

    @get:Rule
    var mockitoRule = MockitoJUnit.rule() // allows different runners
    @Mock
    private lateinit var booksRepository: BooksRepository
    @Mock
    private lateinit var view: BooksActivityView

    private lateinit var presenter: BooksActivityPresenter
    private val MANY_BOOKS = Arrays.asList<Book>(Book(), Book(), Book())

    @Before
    fun setUp() {
        //Schedulers.trampoline() refers to current thread(the same that runs the test)
        presenter = BooksActivityPresenter(booksRepository, Schedulers.trampoline())
        presenter.view = view
        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }

    @After
    fun cleanUp() {
        //reset for next test because they are static methods and they will have old values
        RxJavaPlugins.reset()
    }

    @Test
    fun shouldPassBooksToView() {
        Mockito.`when`(booksRepository.getBooks()).thenReturn(Single.just(MANY_BOOKS))

        presenter.loadBooks()

        Mockito.verify(presenter.view).displayBooks(MANY_BOOKS)
    }

    @Test
    fun shouldHandleBooksFound() {
        Mockito.`when`(booksRepository.getBooks()).thenReturn(Single.just(emptyList()))

        presenter.loadBooks()

        Mockito.verify(presenter.view).displayNoBooks()
    }

    @Test
    fun shouldHandleError() {
        Mockito.`when`(booksRepository!!.getBooks()).thenReturn(Single.error<List<Book>>(Throwable("boom")))

        presenter.loadBooks()

        Mockito.verify(presenter.view).displayError()
    }
}