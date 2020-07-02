package com.graphics.claudia.multiactivity.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DisplayLocation)) return false;
        DisplayLocation that = (DisplayLocation) o;
        return displayName.equals(that.displayName) &&
                coordinates.equals(that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayName, coordinates);
    }
}
