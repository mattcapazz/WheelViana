package com.mattcapazz.wheelviana.api


import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("/paulo14052/marker1/marker/")
    fun getMarker():Call<List<Marker>>

}