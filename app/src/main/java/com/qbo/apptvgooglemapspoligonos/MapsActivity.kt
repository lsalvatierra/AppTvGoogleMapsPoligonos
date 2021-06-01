package com.qbo.apptvgooglemapspoligonos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.PolyUtil
import com.qbo.apptvgooglemapspoligonos.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPolygonClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        //Habilitar los controles de Zoom
        mMap.uiSettings.isZoomControlsEnabled = true
        // Ampliar el tamaño del mapa
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-12.115896, -77.033486), 16f))
        // Add polygons to indicate areas on the map.
        val polygon1 = googleMap.addPolygon(
            PolygonOptions()
            .clickable(true)
            .add(
                LatLng(-12.114093, -77.036811),
                LatLng(-12.113846, -77.029859),
                LatLng(-12.118865, -77.029087),
                LatLng(-12.118912, -77.036962)))
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon1.tag = "alpha"
        polygon1.strokeColor = -0xc771c4
        //polygon1.fillColor = -0x7e387c

        googleMap.setOnPolygonClickListener(this)
        //Validar si un punto X se encuentra dentro del polígono
        //Utilizamos la libreria https://github.com/googlemaps/android-maps-utils
        var inside = PolyUtil.containsLocation(LatLng(-12.114872, -77.033678), polygon1.points, false)
        val mensaje = if(inside){
            "El punto buscando se encuentra dentro del polígono"
        }else{
            "El punto buscando NO se encuentra dentro del polígono"
        }
        Toast.makeText(applicationContext,
            mensaje,
            Toast.LENGTH_LONG).show()

    }

    override fun onPolygonClick(polygon: Polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        var color = polygon.strokeColor xor -0xa80e9
        polygon.strokeColor = color
        color = polygon.fillColor xor -0xa80e9
        polygon.fillColor = color

    }
}