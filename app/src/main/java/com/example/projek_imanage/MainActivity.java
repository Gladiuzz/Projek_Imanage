package com.example.projek_imanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button mmasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mmasuk = (Button) findViewById(R.id.masuk);
        mmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentsignup1 = new Intent(MainActivity.this, registeractivity.class);
                startActivity(intentsignup1);
            }
        });
    }
}
