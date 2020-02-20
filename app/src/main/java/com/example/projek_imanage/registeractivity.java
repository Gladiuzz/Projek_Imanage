package com.example.projek_imanage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projek_imanage.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class registeractivity extends AppCompatActivity {
    private EditText input_username, input_email, input_password;
    private FirebaseAuth mAuth;
    private Button msignup;
    private StorageReference SR;
    private Uri mGambarUri;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //initialize Firebase Auth / Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();
        SR = FirebaseStorage.getInstance().getReference().child("img_avatar");


        msignup = (Button) findViewById(R.id.sign_up_btn);
        input_username = (EditText) findViewById(R.id.name_sgnup);
        input_email = (EditText) findViewById(R.id.email_sgnup);
        input_password = (EditText) findViewById(R.id.password_sgnup);



        SignUp();



    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            //handle the already login user
        }
    }

    private void SignUp(){
        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String username = input_username.getText().toString().trim();
                final String user_email = input_email.getText().toString().trim();
                final String user_password = input_password.getText().toString().trim();
                final String user_avatar = "14045";

                // Pesan kalau email kosong
                if (TextUtils.isEmpty(user_email)){
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Pesan kalau username kosong
                else if(TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(), "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Pesan kalau username lebih kecil dari 5
                else if (username.length() < 5){
                    Toast.makeText(getApplicationContext(), "Username minimum 5", Toast.LENGTH_SHORT).show();
                }
                // Pesan kalau password kosong
                else if (TextUtils.isEmpty(user_password)){
                    Toast.makeText(getApplicationContext(), "Enter your password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Pesan kalau password lebih kecil dari 4
                else if (user_password.length() < 4){
                    Toast.makeText(getApplicationContext(), "Password is too short, enter password minimum 4", Toast.LENGTH_SHORT).show();
                }

                mAuth.createUserWithEmailAndPassword( user_email, user_password)
                        .addOnCompleteListener(registeractivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    // Message Sign Up gagal
                                    Toast.makeText(registeractivity.this, "Sign Up failed " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    // membuat table User ke Firebase real time db
                                    id = mAuth.getUid();


                                    User user = new User(id,username,user_email,user_password,user_avatar);


                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                //display message sign up berhasil

                                                Toast.makeText(registeractivity.this, "Sign Up success", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                //display message gagal
                                            }
                                        }
                                    });
                                    startActivity(new Intent(registeractivity.this, loginactivity.class));
                                }
                            }
                        });
            }
        });
    }
}
