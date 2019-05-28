package com.akondi.booksnippetskotlin.api

import com.akondi.booksnippetskotlin.mvp.model.CarsResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CarApiService {

    @GET("/car2go/vehicles.json")
    fun getCars(): Observable<CarsResponse>

    @GET("/car2go/vehicles.json")
    fun getTheCars(): Call<CarsResponse>
}