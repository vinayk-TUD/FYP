package com.app.footprintcalculator.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.app.footprintcalculator.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class EditPlaneValuesActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Context context = EditPlaneValuesActivity.this;
    double lat, longi, distance;
    int count = 0;
    Location location1, location2;
    LatLng userLocation = null, userLocation2;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_plane_values);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                mMap.setMyLocationEnabled(true);
            }
        }
        mMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        Toast.makeText(context, "true", Toast.LENGTH_SHORT).show();
        lat = latLng.latitude;
        longi = latLng.longitude;
        count = count+1;

        Toast.makeText(context, "true", Toast.LENGTH_SHORT).show();
        if(count == 1){
            location1 = new Location("location1");
            location1.setLatitude(lat);
            location1.setLongitude(longi);

            userLocation = new LatLng(location1.getLatitude(), location1.getLongitude());
            mMap.addMarker(new MarkerOptions().position(userLocation).title("Location 1").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,10));
        }else if(count == 2){
            location2 = new Location("location2");
            location2.setLatitude(lat);
            location2.setLongitude(longi);

            userLocation2 = new LatLng(location2.getLatitude(), location2.getLongitude());
            mMap.addMarker(new MarkerOptions().position(userLocation2).title("Location 2").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
           // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation2,10));

            mMap.addPolyline(
                    new PolylineOptions()
                            .add(userLocation)
                            .add(userLocation2)
                            .width(5f)
                            .color(Color.RED)
            );
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    distance = location1.distanceTo(location2)/1000;
                    if(GetTransportValuesActivity.flag){
                        GetTransportValuesActivity.planeValues.add(distance);
                        Toast.makeText(context, "Distance : "+distance, Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        GetTransportValuesActivity.planeValue = distance;
                        Toast.makeText(context, "Distance : "+distance, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            },2000);

        }
    }
}