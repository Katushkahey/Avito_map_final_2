package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.utils.JsonUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    Button chooseService;
    Intent intent;

    JsonUtils jsonUtils = new JsonUtils();
    List<String> names;
    List<Service> resultListOfServices;

    private float[] colours = {BitmapDescriptorFactory.HUE_AZURE, BitmapDescriptorFactory.HUE_VIOLET, BitmapDescriptorFactory.HUE_GREEN, BitmapDescriptorFactory.HUE_MAGENTA, BitmapDescriptorFactory.HUE_RED};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_maps__map);
        mapFragment.getMapAsync(this);
        names = jsonUtils.parsingToServices(this);
        jsonUtils.parsingToList(this);
        chooseService = findViewById(R.id.activity_maps__button);
        chooseService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent("com.example.myapplication.ChoosingServiceActivity");
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        } else {
            mMap.clear();
            resultListOfServices = data.getParcelableArrayListExtra("resultServices");
            mapFragment.getMapAsync(this);
            onMapReady(mMap);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng services = null;
        double[] latTotal = jsonUtils.getLatTotal();
        double[] lngTotal = jsonUtils.getLngTotal();
        if (resultListOfServices == null || resultListOfServices.isEmpty()) {
            for (int i = 0; i < latTotal.length; i++) {
                services = new LatLng(latTotal[i], lngTotal[i]);
                mMap.addMarker(new MarkerOptions().position(services));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latTotal[latTotal.length - 1], lngTotal[lngTotal.length - 1])));
        } else {
            for (int i = 0; i < resultListOfServices.size(); i++) {
                String s = resultListOfServices.get(i).getName();
                System.out.println(s);
                double[] latResult = resultListOfServices.get(i).getLat();
                System.out.println(latResult.length);
                double[] lngResult = resultListOfServices.get(i).getLng();
                System.out.println(lngResult.length);
                for (int j = 0; j < latResult.length; j++) {
                    services = new LatLng(latResult[j], lngResult[j]);
                    mMap.addMarker(new MarkerOptions().position(services).title("service " + s).icon(BitmapDescriptorFactory
                            .defaultMarker(colours[i % 5])));
                }
            }
        }
    }
}
