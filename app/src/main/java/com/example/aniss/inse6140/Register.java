package com.example.aniss.inse6140;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import userblockchain.UserBlockchain;


public class Register extends Activity implements View.OnClickListener{


    private static Button etRegister;
    private static EditText  etUsername, etPassword, etConfirmPassword;

    private static TextView mErrorField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);





        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        mErrorField =(TextView) findViewById(R.id.error_messages);
        etRegister = (Button) findViewById(R.id.etRegister);



        etRegister.setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /*@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/


    @Override
    public void onClick(final View v) {
        switch (v.getId()){



            case R.id.etRegister:
                v.setEnabled(false);


                if(
                        !etUsername.getText().toString().equals("")
                        && !etPassword.getText().toString().equals("")
                        && !etConfirmPassword.getText().toString().equals("")){
                    if(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){


                        UserBlockchain userBlockchain = new UserBlockchain("132.205.23.211", 3000);
                        Boolean registered = userBlockchain.registerUser(etUsername.getText().toString(), etPassword.getText().toString());

                        if(registered){
                            Toast.makeText(getApplicationContext(), "You have registered successfully", Toast.LENGTH_LONG).show();
                        }







                    }

                }





                break;
        }
    }
}
