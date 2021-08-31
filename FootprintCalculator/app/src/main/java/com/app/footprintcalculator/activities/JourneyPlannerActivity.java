package com.app.footprintcalculator.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.app.footprintcalculator.R;
import com.app.footprintcalculator.models.FootprintModel;
import com.app.footprintcalculator.models.TransportModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//Journey Planner
public class JourneyPlannerActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    Context context = JourneyPlannerActivity.this;
    TextInputEditText edtLocation1, edtLocation2;
    Button btnGetDistance, btnCalculateFootprint, btnSearch1, btnSearch2;
    ListView listView;
    MapView mapView;
    List<Address> addressList;
    int count = 0;
    Location location1, location2;
    double distance=0;
    AlertDialog alertDialog;
    public static double vehicle, bus, train, plane, metro, taxi;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_planner);

        mapView = findViewById(R.id.mapView);
        edtLocation1 = findViewById(R.id.edtLocation);
        btnSearch1 = findViewById(R.id.btnSearch1);
        edtLocation2 = findViewById(R.id.edtLocation2);
        btnSearch2 = findViewById(R.id.btnSearch2);
        btnGetDistance = findViewById(R.id.btnGetDistance);
        btnCalculateFootprint = findViewById(R.id.btnCalculateFootprint);
        listView = findViewById(R.id.listView);

        btnGetDistance.setVisibility(View.GONE);
        btnCalculateFootprint.setVisibility(View.GONE);

        location1 = new Location("Location1");
        location2 = new Location("Location2");

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        //get location 1
        btnSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = edtLocation1.getText().toString().trim();
                addressList = null;
                if(location.isEmpty()){
                    listView.setVisibility(View.GONE);
                }

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(context);
                    try {
                        addressList = geocoder.getFromLocationName(location,2);
                        if(addressList != null){
                            listView.setVisibility(View.VISIBLE);
                            PlacesListAdapter adapter = new PlacesListAdapter(context, addressList);
                            listView.setAdapter(adapter);
                        }else {
                            Toast.makeText(context, "No location found", Toast.LENGTH_SHORT).show();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //Get location 2
        btnSearch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = edtLocation2.getText().toString().trim();
                addressList = null;
                if(location.isEmpty()){
                    listView.setVisibility(View.GONE);
                }

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(context);
                    try {
                        addressList = geocoder.getFromLocationName(location,2);
                        if(addressList != null){
                            listView.setVisibility(View.VISIBLE);
                            PlacesListAdapter adapter = new PlacesListAdapter(context, addressList);
                            listView.setAdapter(adapter);
                        }else {
                            Toast.makeText(context, "No location found", Toast.LENGTH_SHORT).show();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Address address = addressList.get(position);
                if(count == 0){
                    location1.setLatitude(address.getLatitude());
                    location1.setLongitude(address.getLongitude());
                    edtLocation1.setText(address.getLocality()+", "+address.getAdminArea()+", "+address.getCountryName());
                    count = count+1;
                    listView.setVisibility(View.GONE);
                    Toast.makeText(context, "Location 1 selected", Toast.LENGTH_SHORT).show();
                }else if(count==1){
                    location2.setLatitude(address.getLatitude());
                    location2.setLongitude(address.getLongitude());
                    edtLocation2.setText(address.getLocality()+", "+address.getAdminArea()+", "+address.getCountryName());
                    count = count+1;
                    listView.setVisibility(View.GONE);
                    btnGetDistance.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Location 2 selected", Toast.LENGTH_SHORT).show();
                }else {
                    btnGetDistance.setVisibility(View.GONE);
                }
            }
        });

        btnGetDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance = location1.distanceTo(location2)/1000;
                Toast.makeText(context, "Total Distance : "+distance, Toast.LENGTH_SHORT).show();
                btnCalculateFootprint.setVisibility(View.VISIBLE);
                mapView.setVisibility(View.VISIBLE);

                //add markers
                LatLng userLocation = new LatLng(location1.getLatitude(), location2.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Location 1").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,10));

                LatLng userLocation2 = new LatLng(location2.getLatitude(), location2.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation2).title("Location 2").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation2,10));

                //add line to markers
                mMap.addPolyline(
                        new PolylineOptions()
                                .add(userLocation)
                                .add(userLocation2)
                                .width(5f)
                                .color(Color.RED)
                );
                btnGetDistance.setVisibility(View.GONE);
            }
        });
        btnCalculateFootprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EF values
                vehicle = distance*0.75;
                bus = distance*0.65;
                train = distance*0.55;
                plane = distance*0.85;
                metro = distance*0.45;
                taxi  = distance*0.59;


                //throw up dialog of results
                AlertDialog.Builder dailogBuilder = new AlertDialog.Builder(JourneyPlannerActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_layout5, null);
                dailogBuilder.setView(dialogView);

                final TextView tvVehicleScore = dialogView.findViewById(R.id.tvVehicleScore);
                final TextView tvBusScore = dialogView.findViewById(R.id.tvElectricityScore);
                final TextView tvTrainScore = dialogView.findViewById(R.id.tvGasScore);
                final TextView tvPlaneScore = dialogView.findViewById(R.id.tvOilScore);
                final TextView tvMetroScore = dialogView.findViewById(R.id.tvWasteScore);
                final TextView tvTaxiScore = dialogView.findViewById(R.id.tvWaterScore);
                final Button btnSub = dialogView.findViewById(R.id.btnSub);

                order(tvVehicleScore,tvBusScore  ,tvTrainScore ,tvPlaneScore , tvMetroScore,tvTaxiScore );

                tvVehicleScore.setText("Vehicle Emission : "+String.format("%.2f",vehicle));
                tvBusScore.setText("Bus Emission : "+String.format("%.2f",bus));
                tvTrainScore.setText("Train Emission : "+String.format("%.2f",train));
                tvPlaneScore.setText("Plane Emission : "+String.format("%.2f",plane));
                tvMetroScore.setText("Metro Emission : "+String.format("%.2f",metro));
                tvTaxiScore.setText("Taxi Emission : "+String.format("%.2f",taxi));

                btnSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count = 0;
                        startActivity(new Intent(getApplicationContext(), ShowGraphPlannerActivity.class));
                        alertDialog.dismiss();
                        finish();
                    }
                });
                alertDialog = dailogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });

    }

    public void order(TextView tv,TextView tv2,TextView tv3,TextView tv4,TextView tv5,TextView tv6){

        List<Double> emissions = new ArrayList<>();
        emissions.add(vehicle);
        emissions.add(bus);
        emissions.add(plane);
        emissions.add(train);
        emissions.add(metro);
        emissions.add(taxi);

        Collections.sort(emissions);

        for(int i =0; i<1;i++){

            System.out.println(emissions.get(i));

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    public class PlacesListAdapter extends ArrayAdapter<Address> {
        private Context context;
        private List<Address> list, searchList;

        public PlacesListAdapter(Context context , List<Address> list){
            super(context , R.layout.footprint_list_layout, list);
            this.context = context;
            this.list = list;

            searchList = new ArrayList<>();
            searchList.addAll(list);
        }

        @Override
        public View getView(final int position, final View convertView , ViewGroup parent) {

            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            View listViewItem = inflater.inflate(R.layout.footprint_list_layout, null , true);

            TextView tvFootprintName = (TextView) listViewItem.findViewById(R.id.tvFootprintName);
            TextView tvDate = (TextView) listViewItem.findViewById(R.id.tvDate);
            tvDate.setVisibility(View.GONE);

            final Address address = list.get(position);
            tvFootprintName.setText(address.getLocality()+", "+address.getAdminArea()+", "+address.getCountryName());

            return listViewItem;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        mapView.onStart();
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}