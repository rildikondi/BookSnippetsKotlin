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

//@RunWith(PowerMockRunner::class)
//@PrepareForTest(Observable::class, AndroidSchedulers::class, Looper::class, CarsResponse::class)
//class CarActivityPresenterTest {
//
//    val TEST_ERROR_MESSAGE = "error_message"
//
//    @InjectMocks
//    private lateinit var presenter: CarActivityPresenter
//
//    @Mock
//    private lateinit var mApiService: CarApiService
//
//    @Mock
//    private lateinit var mCakeMapper: CarMapper
//
//    @Mock
//    private lateinit var mStorage: Storage
//
//    @Mock
//    private lateinit var mView: CarActivityView
//
//    @Mock
//    private lateinit var mObservable: Observable<CarsResponse>
//
//    @Captor
//    private lateinit var captor: ArgumentCaptor<Subscriber<CarsResponse>>
//
////    private val mRxJavaSchedulersHook = object : RxJava2CallAdapterFactory {
////        val ioScheduler: Scheduler
////            get() = Schedulers.io()
////
////        val newThreadScheduler: Scheduler
////            get() = Schedulers.newThread()
////    }
//
//    @Before
//    @Throws(Exception::class)
//    fun setUp() {
//        initMocks(this)
//
//        val cars = ArrayList<Car>()
//        cars.add(Car())
//        `when`(mStorage!!.savedCars).thenReturn(cars)
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun getCakes() {
//        PowerMockito.mockStatic(Looper::class.java)
//        `when`(AndroidSchedulers.mainThread()).thenReturn(Schedulers.computation())
//
//        `when`(mApiService!!.getCars()).thenReturn(mObservable)
//        //        when(observable.subscribeOn(Schedulers.newThread())).thenReturn(observable);
//        //        when(observable.observeOn(AndroidSchedulers.mainThread())).thenReturn(observable);
//
//        presenter!!.getCars()
//        verify(mView, atLeastOnce())!!.onShowDialog("Loading cakes....")
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun onCompleted() {
//        presenter!!.onComplete()
//        verify(mView, times(1))!!.onHideDialog()
//        verify(mView, times(1))!!.onShowToast("Cakes loading complete!")
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun onError() {
//        presenter!!.onError(Throwable(TEST_ERROR_MESSAGE))
//        verify(mView, times(1))!!.onHideDialog()
//        verify(mView, times(1))!!.onShowToast("Error loading cakes $TEST_ERROR_MESSAGE")
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun onNext() {
//        val response = mock(CarsResponse::class.java)
//        val responseCars = arrayOfNulls<CarsResponsePlacemarks>(1)
//        //`when`(response.placemarks).thenReturn(responseCars)
//        presenter!!.onNext(response)
//
//        verify(mCakeMapper, times(1)).mapCars(response, mStorage)
//        //verify(mView, times(1)).onClearItems()
//        verify(mView, times(1)).onCarsLoaded(anyList())
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun getCarsFromDatabase() {
//        presenter!!.getCarsFromDatabase()
//        //verify(mView, times(1))!!.onClearItems()
//        verify(mView, times(1))!!.onCarsLoaded(anyList())
//    }
//
//}