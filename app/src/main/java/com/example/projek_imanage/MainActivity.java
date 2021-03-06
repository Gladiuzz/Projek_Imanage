package com.example.projek_imanage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button mmasuk, mgoogle;
    TextView mlogin;
    FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mlogin = (TextView) findViewById(R.id.txt_login);
        mmasuk = (Button) findViewById(R.id.masuk);

        mlogin.setOnClickListener(this);
        mmasuk.setOnClickListener(this);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(MainActivity.this, btm_navigation.class));
                }
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(authStateListener);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_login:
                Intent lgn_intent = new Intent(MainActivity.this, loginactivity.class);
                startActivity(lgn_intent);
                break;
            case R.id.masuk:
                Intent lgn_masuk = new Intent(MainActivity.this, registeractivity.class);
                startActivity(lgn_masuk);
                break;
        }
    }


}
