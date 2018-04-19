package com.example.shivam.mapproject;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shivam on 3/25/2018.
 */

public class GetNearByPlacesData extends AsyncTask<Object,String,String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    @Override
    protected String doInBackground(Object... params) {
        mMap=(GoogleMap)params[0];
        url=(String)params[1];

        DownloadUrl downloadUrl=new DownloadUrl();
        try {
            googlePlacesData=downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearByPlaces=null;
        DataParser parser=new DataParser();
        nearByPlaces=parser.parse(s);
        showNearByPlaces(nearByPlaces);
    }

    private void showNearByPlaces(List<HashMap<String,String>> nearByPlaceList)
    {
        for(int i=0;i<nearByPlaceList.size();i++)
        {
            MarkerOptions options=new MarkerOptions();
            HashMap<String,String> googlePlace=nearByPlaceList.get(i);
            String placeName=googlePlace.get("place");
            String vicinty=googlePlace.get("vicinity");
            double lat=Double.parseDouble(googlePlace.get("lat"));
            double lng=Double.parseDouble(googlePlace.get("lng"));
            String reference=googlePlace.get("reference");
            LatLng latLng=new LatLng(lat,lng);
            options.position(latLng);
            options.title(placeName+": "+vicinty);
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(options);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
