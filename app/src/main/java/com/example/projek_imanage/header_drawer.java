package com.example.projek_imanage;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class header_drawer extends AppCompatActivity {
    TextView mEmail;
    FirebaseAuth mAuth;
    FirebaseUser CurrentUser;
    DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header);

        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser();

        mEmail = findViewById(R.id.email_header);
        mEmail.setText(CurrentUser.getEmail());

    }
}
