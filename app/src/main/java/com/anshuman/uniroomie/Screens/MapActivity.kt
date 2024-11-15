package com.anshuman.uniroomie.Screens

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anshuman.uniroomie.R
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var city: String? = null
    private var state: String? = null
    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapview)

        // Get the city and state passed from the previous activity
        city = intent.getStringExtra("CITY")
        state = intent.getStringExtra("STATE")

        // Initialize the MapView
        mapView = findViewById(R.id.mapview)

        // The MapView requires us to call onCreate() and onResume() for lifecycle management
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Clear the map and try to get location data for the provided city
        mMap.clear()

        if (city != null) {
            getLocationFromAddress(city!!)

            // If valid latitude and longitude are found, proceed to display the marker
            if (latitude != null && longitude != null) {
                val latLng = LatLng(latitude!!, longitude!!)

                // Add a marker and move the camera
                mMap.addMarker(MarkerOptions().position(latLng).title("Marker Location"))
                val cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 8f)
                mMap.moveCamera(cameraUpdate)
            } else {
                Toast.makeText(this, "Could not find location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLocationFromAddress(location: String) {
        val geocoder = Geocoder(this)
        val list: List<Address> = geocoder.getFromLocationName(location, 5) ?: return

        if (list.isNotEmpty()) {
            val address = list[0]
            latitude = address.latitude
            longitude = address.longitude
        }
    }

    // Lifecycle methods to ensure MapView is properly managed
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
