package com.mdp.if5b.myFirstGoogleMap;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mdp.if5b.myFirstGoogleMap.R;
import com.mdp.if5b.myFirstGoogleMap.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityMainBinding binding;
    private GoogleMap mMap;
    //tes
    private List<Lokasi> restaurantList = new ArrayList<>();
    private List<Lokasi> hospitalList = new ArrayList<>();
    private ActivityResultLauncher<String[]> locationPermissionRequest = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
                if (fineLocationGranted != null && fineLocationGranted) {
                    // Precise location access granted
                } else if (coarseLocationGranted != null && coarseLocationGranted) {
                    // Only approximate location access granted
                } else {
                    // No location access granted
                }
            });
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        restaurantList.add(new Lokasi("Pempek Raden", new LatLng(-2.9614593018651973, 104.7388806937143)));
        restaurantList.add(new Lokasi("Pempek Tince", new LatLng(-2.972202657628519, 104.75644641378996)));
        restaurantList.add(new Lokasi("Pancious", new LatLng(-2.9752230103651574, 104.76694270512694)));
        restaurantList.add(new Lokasi("Pempek Candy", new LatLng(-2.96122357835814, 104.73875194760694)));
        restaurantList.add(new Lokasi("River Side", new LatLng(-2.992960857626091, 104.75983377757626)));
        restaurantList.add(new Lokasi("Restaurant Sederhana Basuki Rahmat", new LatLng(-2.9595384624023464, 104.73896299819299)));
        restaurantList.add(new Lokasi("Restaurant Sarinande", new LatLng(-2.9734817661430717, 104.7581789646448)));
        restaurantList.add(new Lokasi("Abra19 Coffe And Resto", new LatLng(-2.9639109776456882, 104.73549912039093)));
        restaurantList.add(new Lokasi("Rumah Makan Surya", new LatLng(-2.9672967493687468, 104.739940858422)));
        restaurantList.add(new Lokasi("Resto Ayam Nyenyes", new LatLng(-2.957932279440224, 104.74045584247438)));

        hospitalList.add(new Lokasi("Rumah Sakit Bhayangkara", new LatLng(-2.9583822727938935, 104.73732302170008)));
        hospitalList.add(new Lokasi("Rumah Sakit Umum Sriwijaya Palembang", new LatLng(-2.9594242610692727, 104.73695019475514)));
        hospitalList.add(new Lokasi("Rumah Sakit Umum Daerah Muh Hosein", new LatLng(-2.96566009844806, 104.75021103572124)));
        hospitalList.add(new Lokasi("Rumah Sakit Bersalin Aryodilla", new LatLng(-2.964074356200722, 104.7393105387959)));
        hospitalList.add(new Lokasi("Rumah Sakit Rakyat Kristen Charitas", new LatLng(-2.97476735800793, 104.7526572104737)));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLngLast = new LatLng(latitude, longitude);

                    mMap.clear();
                    mMap.addMarker(new MarkerOptions()
                            .position(latLngLast)
                            .title("I'm HERE MOM")
                            .snippet("T O O O O O L L L L L O O O O N N N N N G G G G G"))
                            .showInfoWindow();

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngLast, 17));
                    mMap.addCircle(new CircleOptions()
                        .center(latLngLast)
                        .radius(100)
                        .strokeColor(Color.TRANSPARENT)
                        .fillColor(R.color.teal_200));
                }
            }
        });

        binding.fabRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();

                for(int i =0; i < restaurantList.size(); i++){
                    mMap.addMarker(new MarkerOptions()
                            .position(restaurantList.get(i).getLatLng())
                            .title(restaurantList.get(i).getNama())
                                    .snippet("Enak, murah, banyak"))
                            .showInfoWindow();
                }

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(restaurantList.get(4).getLatLng(), 17));
            }
        });

        binding.fabHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();

                for(int i =0; i < hospitalList.size(); i++){
                    mMap.addMarker(new MarkerOptions()
                                    .position(hospitalList.get(i).getLatLng())
                                    .title(hospitalList.get(i).getNama())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                                    .showInfoWindow();
                }

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(restaurantList.get(4).getLatLng(), 15));
            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMyLocationEnabled(true);
//        LatLng latLngUser = new LatLng(-2.961791, 104.739752);
//        mMap.addMarker(new MarkerOptions().position(latLngUser).title("Lokasi Saya"));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngUser, 19 ));
//        mMap.addCircle(new CircleOptions()
//                .center(latLngUser)
//                .radius(100)
//                .strokeColor(Color.TRANSPARENT)
//                .fillColor(R.color.teal_200));
    }
}