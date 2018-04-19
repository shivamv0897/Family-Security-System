package com.example.shivam.mapproject;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by shivam on 3/27/2018.
 */

public class SendData extends AsyncTask<String, Void,Void > {



    public String latitude;
    public String longitude;
    public String uname;
    public String battery;
    public String gname;

    public SendData(String latitude, String longitude,String battery, String uname, String gname, String gpass) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.uname = uname;
        this.battery = battery;
        this.gname = gname;
        this.gpass = gpass;
    }

    public String gpass;

    public SendData(String latitude, String longitude, String battery) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.battery = battery;
    }

    public SendData(String latitude, String longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected Void doInBackground(String... params) {
        URL url = null;
        try {
            String eUrl=params[0]+"?gname="+gname+"&gpass="+gpass+"&email="+uname+"&lat="+latitude+"&lng="+longitude+"&battery="+battery;
            url = new URL(eUrl);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        InputStream in=new BufferedInputStream(connection.getInputStream());
        BufferedReader br= new BufferedReader(new InputStreamReader(in));
        String Line=br.readLine();
        while(Line!=null)
        {
            if(Line.equals("1"))
                Log.e("pushData","data updated sucessfully");
            else
                Log.e("pushData",Line);
            Line=br.readLine();
        }
    }
        catch (Exception e)
    {


    }

        return null;
    }
}
