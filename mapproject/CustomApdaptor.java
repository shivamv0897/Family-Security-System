package com.example.shivam.mapproject;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class CustomApdaptor extends ArrayAdapter<Person> implements View.OnClickListener {

    LayoutInflater inflater;
    TextView tv1,tv2,tv3;
    View v;
    int g;
    ImageView iv;
    Context context;


    ArrayList<Person> details=new ArrayList<Person>();
    public CustomApdaptor(Context context, int resource) {
        super(context, resource);
        this.context=context;
    }

    public CustomApdaptor( Context context,  int resource,  ArrayList<Person> objects) {
        super(context, resource, objects);
        details=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        v=convertView;
        g=position;
        if(v==null)
        {   //Toast.makeText(v.getContext(),"view null aaya",Toast.LENGTH_SHORT).show();
            LayoutInflater inflater;
            inflater=LayoutInflater.from(getContext());
            v=inflater.inflate(R.layout.list_layout,null);
        }
        Person d= (Person) getItem(position);
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        if(d!=null)
        {    //Toast.makeText(v.getContext(),"view null nai aaya",Toast.LENGTH_SHORT).show();
            tv1=(TextView)v.findViewById(R.id.textView2);
            tv2=(TextView)v.findViewById(R.id.battery);
            tv3=(TextView)v.findViewById(R.id.textView4);
            iv=(ImageView)v.findViewById(R.id.imageView2);
            if(tv1!=null) {
                tv1.setText(d.getPname());
                tv1.setTypeface(boldTypeface);
                tv1.setTextColor(Color.parseColor("#000000"));
            }
            if(tv2!=null) {
                int i=d.getBattery();
                String b=Integer.toString(i);
                if(i<=20)
                tv2.setTextColor(Color.parseColor("#800000"));
                else if(i>20 && i<=60)
                    tv2.setTextColor(Color.parseColor("#999900"));
                else
                    tv2.setTextColor(Color.parseColor("#228B22"));
                tv2.setTypeface(boldTypeface);
                tv2.setText(b+"%");
            }
            if(tv3!=null)
                tv3.setText(d.getLocation());
            if(iv!=null)
              {   //if(d.getProfile()<0)
                  iv.setBackgroundResource(R.drawable.two);
//                  else
//                      iv.setImageResource(R.drawable.two);
              }

              if(d.getStatus().equalsIgnoreCase("true"))
              {

                  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination="+d.getLat()+","+d.getLng()));
                  PendingIntent pendingIntent=PendingIntent.getActivity(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                  NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                          .setSmallIcon(android.R.drawable.ic_dialog_alert)
                          .setContentTitle(d.getPname()+" is in EMERGENCY!")
                          .setContentText("Tap to reach out")
                          .setColor(Color.RED)
                          .setContentIntent(pendingIntent)
                          .setAutoCancel(true);
                  builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS|Notification.DEFAULT_VIBRATE);
                  NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
                  notificationManagerCompat.notify(1,builder.build());
              }

        }
        return v;
    }



    @Override
    public void onClick(View v) {

    }
}
