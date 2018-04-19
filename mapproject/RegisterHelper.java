package com.example.shivam.mapproject;

/**
 * Created by shivam on 3/29/2018.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shivam on 3/27/2018.
 */

public class RegisterHelper extends AsyncTask<String, Void,Void > {


    public String uname;
    public String gname;
    public String gpass;
    public LoginStatus delegate;
    public int status=0;
    Context context;
    public String fname;
    public String lname;
    public String para;


    public RegisterHelper(String uname, String gname, String gpass, Context context, String fname, String lname) {
        this.uname = uname;
        this.gname = gname;
        this.gpass = gpass;
        this.context = context;
        this.fname = fname;
        this.lname = lname;
        para="?gname="+gname+"&gpass="+gpass+"&email="+uname+"&firstname="+fname+"&lastname="+lname;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        delegate.loginStatus(status);
        Toast.makeText(context,Integer.toString(status),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(String... params) {
        URL url = null;
        try {
            String eUrl=params[0]+"?gname="+gname+"&gpass="+gpass+"&email="+uname+"&firstname="+fname+"&lastname="+lname;;
            url = new URL(eUrl);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            InputStream in=new BufferedInputStream(connection.getInputStream());
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            String Line=br.readLine();
            while(Line!=null)
            {
                if(Line.equals("1")||Line.equals("2")||Line.equals("3")||Line.equals("4"))
                { Log.e("LoginData:","Verified");
                    status=Integer.parseInt(Line);
                }
                else
                {Log.e("LoginData:",Line);
                    status=5;
                }
                Line=br.readLine();
            }
        }
        catch (Exception e)
        {
            status=6;

        }

        return null;
    }

}



