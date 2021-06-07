package com.example.projektgooglemaps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.projektgooglemaps.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private GoogleMap map;
    private ActivityMapsBinding binding;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_HYBRID)
                .rotateGesturesEnabled(true)
                .tiltGesturesEnabled(true);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SupportMapFragment.newInstance(options);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;


        // lokalizacja usera
        enableUserLocation();
        goToUserLocation();

        // natężenie ruchu/korek
        map.setTrafficEnabled(true);

        // opcje UI - zoom i lokalizacja
        UiSettings settings = googleMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);
        settings.setZoomControlsEnabled(true);

        // Markery - szpitale
        LatLng SzpitalPomorskiPCK = new LatLng(54.48971997195148, 18.553453267076463);
        map.addMarker(new MarkerOptions()
                .position(SzpitalPomorskiPCK)
                .title("Szpital Pomorski PCK")
                .snippet("Telefon: 22 480 08 00 | Strona: szpitalepomorskie.eu")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1)));
        map.setOnMarkerClickListener(this);

        LatLng SzpitalWincentego = new LatLng(54.522186003958865, 18.541957363458856);
        map.addMarker(new MarkerOptions()
                .position(SzpitalWincentego)
                .title("Szpital św. Wincentego A Paulo")
                .snippet("Telefon: 22 480 08 00 | Strona: szpitalepomorskie.eu")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1)));

        LatLng SzpitalSpecjalistyczny = new LatLng(54.61493540155073, 18.24569937617383);
        map.addMarker(new MarkerOptions()
                .position(SzpitalSpecjalistyczny)
                .title("Szpital Specjalistyczny im. F.Ceynowy")
                .snippet("Telefon: 22 480 08 00 | Strona: szpitalepomorskie.eu")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1)));

        LatLng SzpitalChorobZakaznych = new LatLng(54.36433546974532, 18.617304007669492);
        map.addMarker(new MarkerOptions()
                .position(SzpitalChorobZakaznych)
                .title("Pomorskie Centrum Chorób Zakaźnych")
                .snippet("Telefon: 22 480 08 00 | Strona: szpitalepomorskie.eu")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital1)));

        // markery urzędy
        LatLng UrzadMiastaGdynia = new LatLng(54.51005, 18.53882);
        map.addMarker(new MarkerOptions()
                .position(UrzadMiastaGdynia)
                .title("Urząd Miasta w Gdyni")
                .snippet("Telefon: 58 626 26 26 | Godziny: 08:00-16:00")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pobrane)));

        LatLng UrzadMiastaSopot = new LatLng(54.44019, 18.56490);
        map.addMarker(new MarkerOptions()
                .position(UrzadMiastaSopot)
                .title("Urząd Miasta w Sopocie")
                .snippet("Telefon: 58 521 37 51 | Pon: 10:00-18:00, wt-czw: 08:00-16:00, pt: 07:30-15:30")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pobrane)));

        LatLng UrzadMiastaGdansk = new LatLng(54.35153, 18.64101);
        map.addMarker(new MarkerOptions()
                .position(UrzadMiastaGdansk)
                .title("Urząd Miasta w Gdańsku")
                .snippet("Telefon: 58 524 45 00 | Godziny: 08:00-16:00")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pobrane)));

        LatLng UrzadPracyGdansk = new LatLng(54.34803, 18.65198);
        map.addMarker(new MarkerOptions()
                .position(UrzadPracyGdansk)
                .title("Urząd Pracy w Gdańsku")
                .snippet("Telefon: 58 743 13 00 | Godziny: 08:00-14:00")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pobrane)));

        LatLng UrzadPracyGdynia = new LatLng(54.52307, 18.52454);
        map.addMarker(new MarkerOptions()
                .position(UrzadPracyGdynia)
                .title("Urząd Pracy w Gdyni")
                .snippet("Telefon: 58 776 12 28 | Godziny: 08:00-15:00")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pobrane)));

        LatLng UrzadMorskiGdynia = new LatLng(54.53363, 18.54079);
        map.addMarker(new MarkerOptions()
                .position(UrzadMorskiGdynia)
                .title("Urząd Morski w Gdyni")
                .snippet("Telefon: 58 355 33 33 | Godziny: 08:00-14:00")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pobrane)));
    }

    // lokalizowanie usera
    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
    }

    // centrowanie widoku na lokalizacji usera przy starcie aplikacji
    private void goToUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            }
        });
    }

    // toast dla markera
    // animacja przejścia do markera po kliknięciu
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Toast.makeText(this, "Kliknij w niebieski przycisk na dole mapy aby ustawić trasę", Toast.LENGTH_SHORT).show();
        map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        map.animateCamera(CameraUpdateFactory.zoomTo(14));
        return false;
    }

}