package com.example.shivam.mapproject;

import java.util.ArrayList;

/**
 * Created by shivam on 3/21/2018.
 */

public interface DataDilivery {
    void processFinish(ArrayList<Person> output);
}

 interface LoginStatus{
  void loginStatus(int status);
}

interface RegisterStaus{
    void registerStatus(int status);
}
