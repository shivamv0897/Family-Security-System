package com.example.shivam.mapproject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MFragment extends Fragment implements OnMapReadyCallback,DataDilivery {


    GoogleMap map;

    int[] images={R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.two,R.drawable.two};
    String[] names={"Shivam","Himanshu","Anand","Sukrity","Chandi","Shakshi"};
    String[] location={"Dehradun","Lucknow","Gurugram","Patna","Shambhal","Haridwar"};
    int[] battery={100,20,50,70,90,10};
    View v;
    ListView lv;
    ArrayList<Person> persons=new ArrayList<Person>();

    public MFragment() {
        // Required empty public constructor
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_m, container, false);
        lv=(ListView)v.findViewById(R.id.list1);
        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Alert message send to your contacts", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                RegisterEmergency data=new RegisterEmergency(getContext());
                String url="http://172.20.10.3/PhpProject3/pushdata.php";
                data.execute(url);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i=new Intent(getContext(),EmergencyActivity.class);
                        startActivity(i);
                    }
                }, 3000);

            }
        });
        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            if (isNetworkConnected()) {
                SharedPreferences sp1=v.getContext().getSharedPreferences("Login", MODE_PRIVATE);
                String email=sp1.getString("email", null);
                String gpass = sp1.getString("gpass", null);
                String gname=sp1.getString("gname",null);
                Toast.makeText(v.getContext(),"executing if in MFragment",Toast.LENGTH_LONG).show();
                String url="http://172.20.10.3/PhpProject3/showData.php";
                Downloader d=new Downloader(getContext(),email,gname,gpass);
                d.delegate=this;
                d.execute(url);
            } else {
                Toast.makeText(v.getContext(),"executing else",Toast.LENGTH_LONG).show();
                for (int i = 0; i < 6; i++) {
                    Person s = new Person(names[i], location[i], battery[i], images[i],"false");
                    persons.add(s);
                    CustomApdaptor adaptor = new CustomApdaptor(v.getContext(), R.layout.list_layout, persons);
                    lv.setAdapter(adaptor);
                }
            }

        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        for(int i=0;i<persons.size();i++) {
            LatLng pp = new LatLng(persons.get(i).getLat(),persons.get(i).getLng());
            MarkerOptions options = new MarkerOptions();
            options.position(pp).title(persons.get(i).getPname());
            map.addMarker(options);
            map.moveCamera(CameraUpdateFactory.newLatLng(pp));
        }
    }


    @Override
    public void processFinish(ArrayList<Person> output) {
        SupportMapFragment mapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        for(int i=0;i<output.size();i++)
        {
            persons.add(i,output.get(i));
        }
        CustomApdaptor adaptor = new CustomApdaptor(v.getContext(), R.layout.list_layout, output);
        lv.setAdapter(adaptor);

    }
}


