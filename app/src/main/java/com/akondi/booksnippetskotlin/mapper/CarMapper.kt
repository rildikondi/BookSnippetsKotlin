package com.akondi.booksnippetskotlin.mapper

import android.util.Log
import com.akondi.booksnippetskotlin.mvp.model.Car
import com.akondi.booksnippetskotlin.mvp.model.CarsResponse
import com.akondi.booksnippetskotlin.utils.Storage
import java.util.ArrayList
import javax.inject.Inject

open class CarMapper @Inject constructor() {

    private val TAG = "CarMapper"

    fun mapCars(response: CarsResponse?, mStorage: Storage): List<Car> {
        val carList = ArrayList<Car>()
        if (response != null) {
            val placemarksList = response.placemarks
            if (placemarksList.isNotEmpty()) {
                for (crp in placemarksList!!) {
                    val car = Car()
                    car.address = crp.address

                    val coordinates = ArrayList<Double>()
                    if (crp.coordinates.isNotEmpty()) {
                        coordinates.add(crp.coordinates[0])
                        coordinates.add(crp.coordinates[1])
                    }
                    car.coordinates = coordinates

                    car.engineType = crp.engineType
                    car.exterior = crp.exterior
                    car.interior = crp.interior
                    car.fuel = crp.fuel
                    car.name = crp.name
                    car.vin = crp.vin
                    //Log.d(TAG, "mapCars: vin" + car.vin)
                    //mStorage.addCar(car)
                    carList.add(car)
                }
            } else {
                Log.d(TAG, "mapCars: placemarksList " + "is null")
            }
        } else {
            Log.d(TAG, "mapCars: response " + "is null")
        }
        return carList
    }
}