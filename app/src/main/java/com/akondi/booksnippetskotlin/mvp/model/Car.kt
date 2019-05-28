package com.akondi.booksnippetskotlin.mvp.model

data class Car(
    var address: String,
    var fuel: Int,
    var exterior: String,
    var coordinates: List<Double>,
    var name: String,
    var engineType: String,
    var vin: String,
    var interior: String
) {
    constructor() : this(
        "",
        0,
        "",
        emptyList(),
        "",
        "",
        "",
        ""
    )
}