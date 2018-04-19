package com.example.shivam.mapproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements LoginStatus {

    EditText group,user,pass;
    Button login,signup;
    String email,gname,gpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        group=(EditText)findViewById(R.id.group);
        user=(EditText)findViewById(R.id.user);
        pass=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        signup=(Button)findViewById(R.id.signup);


    }

    public void login(View v)
    {
        email=user.getText().toString();
        gname=group.getText().toString();
        gpass=pass.getText().toString();
        LoginHelper helper=new LoginHelper(email,gname,gpass,this);
        String url="http://172.20.10.3/PhpProject3/login.php";
        helper.delegate=this;
        helper.execute(url);
        //Toast.makeText(this,"details: "+email+" "+gname+" "+gpass,Toast.LENGTH_LONG).show();
    }

    public void signup(View v)
    {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    @Override
    public void loginStatus(int status) {
        if(status==2)
        {
            Toast.makeText(this,"User name or password incorrect",Toast.LENGTH_LONG).show();
        }
        else if(status==1)
        {
            SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor Ed=sp.edit();
            Ed.putString("email",email );
            Ed.putString("gname",gname);
            Ed.putString("gpass",gpass);
            Ed.commit();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
