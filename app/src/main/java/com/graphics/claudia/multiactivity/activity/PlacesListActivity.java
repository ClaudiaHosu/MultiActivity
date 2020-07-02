package com.graphics.claudia.multiactivity.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.maps.model.LatLng;
import com.graphics.claudia.multiactivity.R;
import com.graphics.claudia.multiactivity.model.DisplayLocation;
import com.graphics.claudia.multiactivity.utils.ObjectSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PlacesListActivity extends AppCompatActivity {

    private List<DisplayLocation> displayLocations = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private List<String> displayLocationsInString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        buildListView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {



            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("com.graphics.claudia.multiactivity.activity", Context.MODE_PRIVATE);
        List<LatLng> savedLocations = null;
        try {
            savedLocations = (ArrayList<LatLng>) ObjectSerializer.deserialize(sharedPreferences.getString(PlacesMapActivity.LOCATIONS_KEY, ObjectSerializer.serialize(new ArrayList<LatLng>())));
            if (CollectionUtils.isEmpty(savedLocations)) return;

            for (LatLng savedCoordinates : savedLocations) {
                DisplayLocation displayLocation = convertLocation(savedCoordinates);
                if (!displayLocations.contains(displayLocation)) {
                    displayLocations.add(displayLocation);
                    displayLocationsInString.add(displayLocation.getDisplayName());
                }
            }

            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
        }

    }

    private void buildListView() {

        ListView listView = findViewById(R.id.placesList);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displayLocationsInString);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToMap(i);
            }
        });

    }

    @NonNull
    private DisplayLocation convertLocation(@NonNull LatLng location) {
        DisplayLocation displayLocation = new DisplayLocation();
        displayLocation.setCoordinates(location);

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1);

            if (CollectionUtils.isEmpty( addressList )) {
                displayLocation.setDisplayName(new Date().toString());
                return displayLocation;
            }

            if (addressList.get(0).getAddressLine(0) != null) {
                    displayLocation.setDisplayName(addressList.get(0).getAddressLine(0));
            } else {
                    displayLocation.setDisplayName(new Date().toString());
            }


        } catch (Exception e) {
            Log.e("Error", e.getMessage(), e);
        }

        return displayLocation;

    }

    private List<String> toListedSavedLocations() {

        if (CollectionUtils.isEmpty(displayLocations)) return new ArrayList<>();

        return displayLocations.stream()
                .map(displayLocation -> displayLocation.getDisplayName())
                .collect(Collectors.toList());

    }


    public void addNewPlace(View view) {
        goToMap(null);
    }

    public void goToMap(int placeIndex) {
        goToMap(displayLocations.get(placeIndex).getCoordinates());
    }

    public void goToMap(@Nullable LatLng latLng) {
        Intent intent = new Intent(getApplicationContext(), PlacesMapActivity.class);
        intent.putExtra(PlacesMapActivity.LOCATION_KEY, latLng);
        startActivityForResult(intent, 1);
    }
}