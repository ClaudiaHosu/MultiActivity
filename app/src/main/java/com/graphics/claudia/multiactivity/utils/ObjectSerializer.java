package com.graphics.claudia.multiactivity.utils;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;

public class ObjectSerializer {

    private static Gson gson = new GsonBuilder().create();

    public static String serialize(Object object) throws IOException {
            return gson.toJson(object);
    }

    public static ArrayList<LatLng> deserialize(String serialisedString) throws IOException, ClassNotFoundException {
        return gson.fromJson(serialisedString, new TypeToken<ArrayList<LatLng>>() {}.getType());
    }

}
