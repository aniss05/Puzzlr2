package com.example.aniss.inse6140;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.view.View;
import android.widget.Button;



public class MainActivity extends Activity implements View.OnClickListener{

    private static Button login, register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.etLogin);
        register = (Button) findViewById(R.id.etRegister);



        login.setOnClickListener(this);
        register.setOnClickListener(this);




    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.etLogin:

                v.setEnabled(false);

                startActivity(new Intent(this, Login.class));


                break;

            case R.id.etRegister:

                v.setEnabled(false);

                startActivity(new Intent(this, Register.class));



                break;
        }
    }
}
