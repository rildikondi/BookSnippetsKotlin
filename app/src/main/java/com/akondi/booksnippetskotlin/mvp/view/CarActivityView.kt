package com.akondi.booksnippetskotlin.mvp.view

import com.akondi.booksnippetskotlin.base.BaseView
import com.akondi.booksnippetskotlin.mvp.model.Car

interface CarActivityView : BaseView {

    fun onShowDialog(message: String)

    fun onCarsLoaded(carList: List<Car>)

    fun onHideDialog()
}