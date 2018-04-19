package com.example.shivam.mapproject;

import java.io.Serializable;

/**
 * Created by shivam on 3/20/2018.
 */

public class Person implements Serializable {
    public String pname;
    public String location;
    public int battery;
    public int profile;
    double lat;
    double lng;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Person()
    {

    }

    public Person(String pname, String location, int battery, int profile,String status) {
        this.pname = pname;
        this.location = location;
        this.battery = battery;
        this.profile = profile;
        this.status=status;
    }

    public String getPname() {

        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }
}
