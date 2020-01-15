package com.example.projek_imanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class forgot_pass extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    EditText f_email, confirm_email;
    Button btn_forgot_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        mAuth = FirebaseAuth.getInstance();
        f_email = (EditText) findViewById(R.id.email_forgot);
        btn_forgot_password = (Button) findViewById(R.id.btn_submit);

        Forgot_Password();
    }

    private void Forgot_Password(){
        btn_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String forgot_email = f_email.getText().toString().trim();

                if (TextUtils.isEmpty(forgot_email)){
                    Toast.makeText(getApplicationContext(), "Enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(forgot_email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(forgot_pass.this, "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(forgot_pass.this, "Fail to send reset password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
