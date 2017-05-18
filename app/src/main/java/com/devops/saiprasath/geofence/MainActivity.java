package com.devops.saiprasath.geofence;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.PowerManager;
import android.preference.PreferenceScreen;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.nearby.connection.Connections;

import java.security.Permission;
import java.security.Permissions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    public Location mLocation;
    public CameraPosition mcameraposition;
    public String TAG = "MainActivity.this";
    private boolean mPermissionsGranted;
    public static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION=1;
    public GoogleApiClient googleapiclient;
    public GoogleMap googlemap;
    private double latitude;
    private double longitude;
    public Home home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleApiClient apiclient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
//                .addApi(FusedLocationProviderApi)
                .enableAutoManage(this,this)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        apiclient.connect();
        googleapiclient=apiclient;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsGranted=false;
        switch (requestCode){
            case PERMISSION_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0){
                   mPermissionsGranted=true;
                }
        }
        updateLocationUI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googlemap=googleMap;

        getDeviceLocation();

        updateLocationUI();

//        mcameraposition = CameraPosition.fromLatLngZoom();
    }
    public void getDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mPermissionsGranted = true;
        } else
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);


        if (mPermissionsGranted) {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(googleapiclient);
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
        }
        if (mcameraposition!=null){
            googlemap.moveCamera(CameraUpdateFactory.newCameraPosition(mcameraposition));
        }
        if (mLocation!=null){
            googlemap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)));
            googlemap.addCircle(new CircleOptions().center(new LatLng(latitude,longitude)).radius(200).fillColor(Color.rgb(127, 140, 141)).strokeColor(Color.RED).strokeWidth(2));
            googlemap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLocation.getLatitude(),mLocation.getLongitude()),16.8f));
        }
    }
    public void updateLocationUI()
    {
        if (googlemap==null){
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mPermissionsGranted=true;
        }
        else
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);

        if (mPermissionsGranted){
            googlemap.setMyLocationEnabled(true);
            googlemap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        else
        {
            googlemap.setMyLocationEnabled(false);
            googlemap.getUiSettings().setMyLocationButtonEnabled(false);
        }

    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG,"On connection created");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG,"Connect Suspended during initiating to Google Maps");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG,"Connect Failed during Connection to onConnectionFailed Listener");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        home.id_edit.setText("");
        home.pwd_edit2.setText("");
        Intent intent2 = new Intent(MainActivity.this,Home.class);
        startActivity(intent2);
    }
}

