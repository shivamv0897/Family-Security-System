package com.example.shivam.mapproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.CONTEXT_IGNORE_SECURITY;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by shivam on 4/12/2018.
 */

public class RegisterEmergency extends AsyncTask<String,Void,Void> {

    Context context;
    RegisterEmergency(Context context)
    {
        this.context=context;
    }

    @Override
    protected Void doInBackground(String... params) {

        SharedPreferences sp1=context.getSharedPreferences("Login", MODE_PRIVATE);
        String email=sp1.getString("email", null);
        String gpass = sp1.getString("gpass", null);
        String gname=sp1.getString("gname",null);

        SharedPreferences sp2=context.getSharedPreferences("Location", MODE_PRIVATE);
        String lat=sp2.getString("lat", null);
        String lng = sp2.getString("lng", null);
        String bat=sp2.getString("bat",null);

       String para="?gname="+gname+"&gpass="+gpass+"&email="+email+"&lat="+lat+"&lng="+lng+"&battery="+bat+"&status=true";

        URL url = null;
        try {
            String eUrl=params[0]+para;
            url = new URL(eUrl);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            InputStream in=new BufferedInputStream(connection.getInputStream());
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            String Line=br.readLine();
            while(Line!=null)
            {
                if(Line.equals("1"))
                { Log.e("RegisterEmergency:","Submitted");
                    //Toast.makeText(context,"Notification Send",Toast.LENGTH_SHORT).show();
                }
                else
                {Log.e("LoginData:",Line);
                    //Toast.makeText(context,"Something went wrong 1",Toast.LENGTH_SHORT).show();
                }
                Line=br.readLine();
            }
        }
        catch (Exception e)
        {
            //Toast.makeText(context,"Something went wrong 2",Toast.LENGTH_SHORT).show();
        }

        return null;
    }

}
