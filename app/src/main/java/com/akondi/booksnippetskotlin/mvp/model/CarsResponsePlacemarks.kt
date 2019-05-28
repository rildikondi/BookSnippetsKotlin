package com.akondi.booksnippetskotlin.mvp.model

data class CarsResponsePlacemarks(
    val address: String,
    val fuel: Int,
    val exterior: String,
    val coordinates: DoubleArray,
    val name: String,
    val engineType: String,
    val vin: String,
    val interior: String
) {
    constructor() : this(
        "",
        0,
        "",
        DoubleArray(0),
        "",
        "",
        "",
        ""
    )
}