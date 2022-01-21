package com.mattcapazz.wheelviana.api



data class Marker(
    val id: Int,
    val name: String,
    val marker: Location

)

data class  Location(
    val lat: String,
    val long: String
)