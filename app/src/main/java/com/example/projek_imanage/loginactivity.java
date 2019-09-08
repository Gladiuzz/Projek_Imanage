package com.example.projek_imanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class loginactivity extends AppCompatActivity {
    Button mlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mlogin = (Button) findViewById(R.id.login);
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentsignup = new Intent(loginactivity.this, btm_navigation.class);
                startActivity(intentsignup);
            }
        });

    }


}
