package com.example.shivam.mapproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements LoginStatus {
    EditText user,group,first,last,pass;
    Button register;
    String email,fname,lname,gpass,gname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user=(EditText)findViewById(R.id.user1);
        group=(EditText)findViewById(R.id.group1);
        first=(EditText)findViewById(R.id.fname);
        last=(EditText)findViewById(R.id.lname);
        pass=(EditText)findViewById(R.id.password1);

    }

    public void done(View v)
    {
        email=user.getText().toString();
        gname=group.getText().toString();
        fname=first.getText().toString();
        lname=last.getText().toString();
        gpass=pass.getText().toString();
        RegisterHelper helper=new RegisterHelper(email,gname,gpass,this,fname,lname);
        String url="http://172.20.10.3/PhpProject3/newUser.php";
        helper.delegate=this;
        helper.execute(url);
    }


    @Override
    public void loginStatus(int status) {
        if(status==2)
        {
            Toast.makeText(this,"Group already exist!",Toast.LENGTH_SHORT).show();
            group.setText("");
            pass.setText("");
        }
        if(status==3)
        {
            SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putString("email",email );
            Ed.putString("gname",gname);
            Ed.putString("gpass",gpass);
            Ed.commit();
            Toast.makeText(this,"Welcome to "+gname,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }
        if(status==4)
        {
            Toast.makeText(this,"User already exist!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(status==1)
        {   SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putString("email",email );
            Ed.putString("gname",gname);
            Ed.putString("gpass",gpass);
            Ed.commit();
            Toast.makeText(this,gname+" created!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }
        else
        {
            Toast.makeText(this,"Something went wrong!",Toast.LENGTH_SHORT).show();
        }
    }
}
