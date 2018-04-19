package com.example.shivam.mapproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shivam on 3/25/2018.
 */

public class DownloadUrl {

    public String readUrl(String myUrl) throws IOException {
        String data="";
        InputStream inputStream=null;
        HttpURLConnection connection=null;
        try {
            URL url=new URL(myUrl);
            connection=(HttpURLConnection)url.openConnection();
            connection.connect();
            inputStream=connection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb=new StringBuilder();
            String line="";
            while ((line=br.readLine())!=null)
            {
                sb.append(line);
            }
            data=sb.toString();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            connection.disconnect();
        }
        return data;
    }
}
