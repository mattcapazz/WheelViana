package com.mattcapazz.wheelviana.api

data class User2(
  val id: Int,
  val name: String,
  val marker: Markerr
)

data class Markerr(
  val lat: String,
  val long: String
)


