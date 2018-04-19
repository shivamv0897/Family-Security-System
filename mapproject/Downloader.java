package com.example.shivam.mapproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by shivam on 3/21/2018.
 */

public class Downloader extends AsyncTask<String,Void,Void> {

    ProgressDialog pd;
    ArrayList<Person> persons=new ArrayList<Person>();
    String s="";
    String[] c;
    public DataDilivery delegate;
    Context context;
    int index=0;
    String user;
    String gname;
    String gpass;

    public Downloader(Context context) {
        this.context = context;
    }

    public Downloader(Context context, String user, String gname, String gpass) {
        this.context = context;
        this.user = user;
        this.gname = gname;
        this.gpass = gpass;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            index=3;
            Geocoder geocoder;
            List<Address> addresses=null;
            geocoder = new Geocoder(context, Locale.getDefault());
            //String eUrl=params[0]+"?gname=verma&gpass=123&email=sv0897@gmail.com";
            String eUrl=params[0]+"?gname="+gname+"&gpass="+gpass+"&email="+user;
            URL url = new URL(eUrl);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            InputStream in=new BufferedInputStream(connection.getInputStream());
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            String Line=br.readLine();
            while(Line!=null)
            {  c=Line.split(":");
                if(!c.equals("")||!c.equals(" "))
                {
                for(int i=0;i<c.length;i++)
                {   String f[]=c[i].split(",");
                    Person d=new Person();
                    if(f.length>=2){
                        String name=f[0]+" "+f[1];
                        String lat=f[2];
                        String lng=f[3];
                        String batt=f[4];
                        String status=f[5];
                       addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lng), 1);
                      String address = addresses.get(0).getAddressLine(0);
                       String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String knownName = addresses.get(0).getFeatureName();
                        d.setPname(name);
                        if(knownName!=null)
                        d.setLocation(knownName+", "+city);
                        else
                            d.setLocation(city+", "+state);
                         d.setBattery(Integer.parseInt(batt));
                        d.setLat(Double.parseDouble(lat));
                        d.setLng(Double.parseDouble(lng));
                        d.setStatus(status);
                        s=s+f[0]+f[1]+f[2]+f[3]+f[4];}
                    index=1;
                    //Toast.makeText(context,"line aayi",Toast.LENGTH_LONG).show();
                    Log.e("exception hua ",s);
                    persons.add(d);}}
               else
                    {//Toast.makeText(context,"no line",Toast.LENGTH_LONG).show();
                        Log.e("exception hua ","no line");
                    index=2;}
                Line=br.readLine();
            }
        }
        catch (Exception e)
        {   e.printStackTrace();
            s=s+e.toString();
            //Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            Log.e("exception hua ",e.getMessage());

        }
        //Toast.makeText(context,"line hi null ho gayi",Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context,Integer.toString(persons.size()),Toast.LENGTH_SHORT).show();
        delegate.processFinish(persons);
    }
}
