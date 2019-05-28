package com.akondi.booksnippetskotlin.modules.car

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akondi.booksnippetskotlin.R
import com.akondi.booksnippetskotlin.base.BaseActivity
import com.akondi.booksnippetskotlin.di.module.CarModule
import com.akondi.booksnippetskotlin.listeners.OnCarItemClickListener
import com.akondi.booksnippetskotlin.mapper.CarMapper
import com.akondi.booksnippetskotlin.modules.books.BooksActivity
import com.akondi.booksnippetskotlin.modules.car.adapter.CarAdapter
import com.akondi.booksnippetskotlin.mvp.model.Car
import com.akondi.booksnippetskotlin.mvp.presenter.CarActivityPresenter
import com.akondi.booksnippetskotlin.mvp.view.CarActivityView
import com.akondi.booksnippetskotlin.utils.NetworkUtils.isNetAvailable
import kotlinx.android.synthetic.main.activity_car.*

import javax.inject.Inject

class CarActivity : BaseActivity(), CarActivityView{

    override fun onCarsLoaded(carList: List<Car>) {
        carAdapter.addCars(carList)
    }

    override fun onShowToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onHideDialog() {
        hideDialog()
    }

    override fun onShowDialog(message: String) {
        showDialog(message)
    }

    private lateinit var carAdapter: CarAdapter

    @Inject
    lateinit var carActivityPresenter: CarActivityPresenter

    override fun getContentView(): Int = R.layout.activity_car

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onViewReady(savedInstanceState, intent)

        initializeList()
        if (isNetAvailable(this))
            carActivityPresenter.getCars()
        else
            carActivityPresenter.getCarsFromDatabase()
    }

    override fun onDestroy() {
        super.onDestroy()
        carActivityPresenter.clearCompositeDisposable()
    }

    override fun resolveDaggerDependency() {
        super.resolveDaggerDependency()

        val component by lazy { app.mApplicationComponent.plus(CarModule(this)) }
        component.inject(this)
    }

    private fun initializeList() {
        rv_carList.setHasFixedSize(true)
        //rv_carList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) added in xml
        carAdapter = CarAdapter(layoutInflater, object : OnCarItemClickListener {
            override fun onCarClicked() {
                goToBooks()
            }
        })
        rv_carList.adapter = carAdapter
    }

    private fun goToBooks() {
        val intent = Intent(this, BooksActivity::class.java)
        startActivity(intent)
    }
}
