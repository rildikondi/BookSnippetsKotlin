package com.akondi.booksnippetskotlin.mvp.presenter

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.akondi.booksnippetskotlin.api.CarApiService
import com.akondi.booksnippetskotlin.base.BasePresenter
import com.akondi.booksnippetskotlin.mapper.CarMapper
import com.akondi.booksnippetskotlin.modules.car.CarActivity
import com.akondi.booksnippetskotlin.mvp.model.CarsResponse
import com.akondi.booksnippetskotlin.mvp.view.CarActivityView
import com.akondi.booksnippetskotlin.utils.Storage
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CarActivityPresenter
@Inject constructor(
    val apiService: CarApiService,
    val carMapper: CarMapper,
    val storage: Storage,
    val mainScheduler: Scheduler
) : BasePresenter<CarActivityView>(), Observer<CarsResponse> {
    override fun onComplete() {
        view.onHideDialog()
        view.onShowToast("Cars loading complete!")
    }

    override fun onSubscribe(d: Disposable) {
        addToCompositeDisposable(d)
    }

    override fun onNext(carsResponse: CarsResponse) {
        if (carsResponse.placemarks.isNotEmpty())
            view.onCarsLoaded(carMapper.mapCars(carsResponse, storage))
        else
            view.onShowToast("Car list is empty!")
    }

    override fun onError(e: Throwable) {
        view.onHideDialog()
        view.onShowToast("Error loading cars: " + e.message)
    }

    fun getCars() {
        view.onShowDialog("Loading cars...")
        val carsResponseObservable: Observable<CarsResponse> = apiService.getCars()
        subscribe(carsResponseObservable, this, mainScheduler)
    }

    fun getCarsFromDatabase() {
        view.onCarsLoaded(storage.savedCars)
    }
}