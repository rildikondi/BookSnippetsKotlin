package com.akondi.booksnippetskotlin.mvp.presenter

import com.akondi.booksnippetskotlin.api.CarApiService
import com.akondi.booksnippetskotlin.mapper.CarMapper
import com.akondi.booksnippetskotlin.mvp.model.CarsResponse
import com.akondi.booksnippetskotlin.mvp.model.CarsResponsePlacemarks
import com.akondi.booksnippetskotlin.mvp.view.CarActivityView
import com.akondi.booksnippetskotlin.utils.Storage
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(
    MockitoJUnitRunner::class)
class CarActivityPresenterTest {

//    @get:Rule
//    var mockitoRule = MockitoJUnit.rule() // allows different runners

    @Mock
    private lateinit var carApiService: CarApiService

    @Mock
    private lateinit var carMapper: CarMapper

    @Mock
    private lateinit var storage: Storage

    @Mock
    private lateinit var view: CarActivityView

    private lateinit var presenter: CarActivityPresenter

    private val EMPTY_RESPONSE = CarsResponse()
    private val MANY_CARS = CarsResponse()
    private val carsResponsePlacemarks = arrayOf(CarsResponsePlacemarks(), CarsResponsePlacemarks(), CarsResponsePlacemarks())

    @Before
    fun setUp() {
        //Schedulers.trampoline() refers to current thread(the same that runs the test)
        presenter = CarActivityPresenter(carApiService, carMapper, storage, Schedulers.trampoline())
        presenter.view = view
        MANY_CARS.placemarks = carsResponsePlacemarks

        RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { scheduler -> Schedulers.trampoline() }
        //RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @After
    fun cleanUp() {
        //reset for next test because they are static methods and they will have old values
        RxJavaPlugins.reset()
    }

    @Test
    @Throws(InterruptedException::class)
    fun shouldLoadCarsToView() {
        //given
        Mockito.`when`(carApiService.getCars()).thenReturn(Observable.just(MANY_CARS))

        // when
        presenter.getCars()

        //then
        Mockito.verify(presenter.view).onCarsLoaded(carMapper.mapCars(MANY_CARS, storage))
    }

    @Test
    fun shouldHandleBooksFound() {
        //given
        Mockito.`when`(carApiService.getCars()).thenReturn(Observable.just(EMPTY_RESPONSE))

        // when
        presenter.getCars()

        //then
        Mockito.verify(presenter.view).onShowToast("Car list is empty!")
    }

    @Test
    fun shouldHandleError() {
        Mockito.`when`(carApiService.getCars()).thenReturn(Observable.error(Throwable("boom")))

        presenter.getCars()

        Mockito.verify(presenter.view).onShowToast("Error loading cars: " + "boom")
    }
}
