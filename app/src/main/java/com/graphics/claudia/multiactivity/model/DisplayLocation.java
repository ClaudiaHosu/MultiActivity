package com.graphics.claudia.multiactivity.model;

import com.google.android.gms.maps.model.LatLng;

public class DisplayLocation {

    private String displayName;
    private LatLng coordinates;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}
