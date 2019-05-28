package com.akondi.booksnippetskotlin.mvp.model

data class CarsResponse (
   var placemarks: Array<CarsResponsePlacemarks>
)
{
   constructor() : this(

      emptyArray<CarsResponsePlacemarks>()

   )
}