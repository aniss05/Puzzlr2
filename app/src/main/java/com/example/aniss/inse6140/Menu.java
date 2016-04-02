package com.example.aniss.inse6140;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity implements View.OnClickListener{

    private Button selectUsername, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.ThemeOverlay_Material_Dark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if(Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        selectUsername = (Button) findViewById(R.id.btnSelectUsername);
        exit = (Button) findViewById(R.id.btnExit);
        selectUsername.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnExit:
                Toast.makeText(getApplicationContext(), "Exiting application.", Toast.LENGTH_LONG).show();
                System.exit(0);
                break;

            case R.id.btnSelectUsername:
                


        }
    }
}
