package com.example.projek_imanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class registeractivity extends AppCompatActivity {
    Button msignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        msignup = (Button) findViewById(R.id.sign_up_btn);
        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentsignup = new Intent(registeractivity.this, loginactivity.class);
                startActivity(intentsignup);
            }
        });
    }
}
