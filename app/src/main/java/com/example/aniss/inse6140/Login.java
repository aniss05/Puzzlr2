package com.example.aniss.inse6140;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity implements View.OnClickListener{

    private static Button login;
    private static EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.etLogin);


        login.setOnClickListener(this);





    }


   /* @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/


    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.etLogin:

                if(!username.getText().toString().equals("") && !password.getText().toString().equals("")){

                }




                break;
        }
    }
}
