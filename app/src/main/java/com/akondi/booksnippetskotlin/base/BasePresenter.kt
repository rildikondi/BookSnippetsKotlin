package com.akondi.booksnippetskotlin.base

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class BasePresenter<V : BaseView> {

    private var compositeDisposable = CompositeDisposable()

    //Should be protected to change on clean  architecture
    @Inject
    public lateinit var view: V

    protected fun <T> subscribe(observable: Observable<T>, observer: Observer<T>, scheduler : Scheduler) {
        observable.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.computation())
            .observeOn(scheduler)
            .subscribe(observer)
    }

    protected fun addToCompositeDisposable(disposable: Disposable) =
        compositeDisposable.add(disposable)

    fun clearCompositeDisposable() = compositeDisposable.clear()
}