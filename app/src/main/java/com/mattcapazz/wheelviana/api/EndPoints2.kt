package com.mattcapazz.wheelviana.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints2 {
  @GET("/paulo14052/marker1/marker/")
  fun getMarkers(): Call<List<User2>>

  @GET("/paulo14052/marker1/marker/{id}")
  fun getMarkerById(@Path("id") id: Int): Call<User2>

  @FormUrlEncoded
  @POST("/posts")
  fun postTestCann(@Field("terpene") first: String?): Call<OutputPost2>
}