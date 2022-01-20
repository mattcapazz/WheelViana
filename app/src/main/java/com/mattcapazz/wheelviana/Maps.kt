package com.mattcapazz.wheelviana

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mattcapazz.wheelviana.api.EndPoints2
import com.mattcapazz.wheelviana.api.ServiceBuilder2
import com.mattcapazz.wheelviana.api.User2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

//source:https://cursokotlin.com/capitulo-28-google-maps-en-android-con-kotlin/

class Maps : AppCompatActivity(), OnMapReadyCallback,
  GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

  private lateinit var map: GoogleMap
  private lateinit var users: List<User2>

  companion object {
    const val REQUEST_CODE_LOCATION = 0
  }

  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap
    map.setOnMyLocationButtonClickListener(this)
    map.setOnMyLocationClickListener(this)
    enableLocation()

    //Para Apagar (marca com clique longo
    setMapLongClick(map)
    setPoiClick(map)
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_maps)
    val mapFragment: SupportMapFragment =
      supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)

    val request = ServiceBuilder2.buildService(EndPoints2::class.java)
    val call = request.getMarkers()
    var position: LatLng

    call.enqueue(object : Callback<List<User2>> {
      override fun onResponse(call: Call<List<User2>>, response: Response<List<User2>>) {
        if (response.isSuccessful) {
          users = response.body()!!
          for (user in users) {
            position = LatLng(
              user.marker.lat.toString().toDouble(),
              user.marker.long.toString().toDouble()
            )
            map.addMarker(MarkerOptions().position(position).title(user.name))
          }
        }
      }

      override fun onFailure(call: Call<List<User2>>, t: Throwable) {
        Toast.makeText(this@Maps, "${t.message}", Toast.LENGTH_SHORT).show()
      }
    })

  }


  private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
    this,
    Manifest.permission.ACCESS_FINE_LOCATION
  ) == PackageManager.PERMISSION_GRANTED

  private fun enableLocation() {
    if (!::map.isInitialized) return
    if (isLocationPermissionGranted()) {
      if (ActivityCompat.checkSelfPermission(
          this,
          Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
          this,
          Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
      ) return
      map.isMyLocationEnabled = true
    } else requestLocationPermission()
  }

  private fun requestLocationPermission() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
      )
    ) Toast.makeText(this, "É necessario aceitar as permissões", Toast.LENGTH_SHORT).show()
    else {
      ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        REQUEST_CODE_LOCATION
      )
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    when (requestCode) {
      REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
          ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
          ) != PackageManager.PERMISSION_GRANTED
        ) return
        map.isMyLocationEnabled = true
      } else {
        Toast.makeText(this, "É necessario aceitar as permições", Toast.LENGTH_SHORT).show()
      }
    }
  }

  override fun onResumeFragments() {
    super.onResumeFragments()
    if (!::map.isInitialized) return
    if (!isLocationPermissionGranted()) {
      !map.isMyLocationEnabled
      Toast.makeText(this, "É necessario aceitar as permissões", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onMyLocationButtonClick(): Boolean {
    Toast.makeText(this, "Butão pressionado", Toast.LENGTH_SHORT).show()
    // Se true, não leva a localização mal abra
    return false
  }

  override fun onMyLocationClick(p0: Location) {
    Toast.makeText(this, "Estás em ${p0.latitude}, ${p0.longitude}", Toast.LENGTH_SHORT).show()
  }

  private fun setMapLongClick(map: GoogleMap) {
    map.setOnMapLongClickListener {
      val snipper = String.format(
        Locale.getDefault(),
        "Lat:%1.5f, Lng:%2$.5f",
        it.latitude,
        it.longitude
      )

      map.addMarker(MarkerOptions().position(it).title("Marker here").snippet(snipper))

      val database =
        Firebase.database("https://wheelviana-default-rtdb.europe-west1.firebasedatabase.app/")
      val reference = database.reference
      val data = reference.push().child("Locations").setValue(it)

    }
  }

  private fun setPoiClick(map: GoogleMap) {
    map.setOnPoiClickListener { point ->
      val poiMarker = map.addMarker(MarkerOptions().position(point.latLng).title(point.name))
      poiMarker.showInfoWindow()
    }
  }
}
