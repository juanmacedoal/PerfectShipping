package com.exampledemo.parsaniahardik.getcontactdetailsdemonuts;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        Intent iin= getIntent();
        Bundle bundle = iin.getExtras();

        address = bundle.getString("address");
    }


    @Override
    protected void onResume() {
        super.onResume();

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
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        double latitude = 0;
        double longitude = 0;
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
            if(addresses.size() > 0) {
                latitude= addresses.get(0).getLatitude();
                longitude= addresses.get(0).getLongitude();
                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in "+ address));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.animateCamera( CameraUpdateFactory.zoomTo( 10.0f ) );
                mMap.getUiSettings().setZoomControlsEnabled(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
