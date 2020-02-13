package com.example.projek_imanage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projek_imanage.fitur.Homeitem;
import com.example.projek_imanage.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.projek_imanage.fitur.add_Fragment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

public class loginactivity extends AppCompatActivity {

    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signin;
    int RC_SIGN_IN = 0;

    private Button mlogin;
    private EditText lgn_email, lgn_password;
    private FirebaseAuth mAuth;
    private DatabaseReference dbr;
    private TextView forgot_psw;
    private Button btn_google;
    public FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase Auth Instance
        mAuth = FirebaseAuth.getInstance();

        mlogin =  findViewById(R.id.login);
        lgn_email =  findViewById(R.id.email_lgn);
        lgn_password = findViewById(R.id.password_lgn);
        forgot_psw = findViewById(R.id.forgot_password);
        btn_google = findViewById(R.id.image_google);
        signin =findViewById(R.id.sign_in_button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });

        //Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        SignUpUser();
        Forgot_Password();



        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(loginactivity.this, btm_navigation.class));
                }
            }
        };

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(loginactivity.this,Homeitem.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.v("error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void SignUpUser() {
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = lgn_email.getText().toString().trim();
                final String user_email = lgn_email.getText().toString().trim();
                final String user_password = lgn_password.getText().toString().trim();

                if (TextUtils.isEmpty(user_email)){
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(user_password)){
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
                    Toast.makeText(getApplicationContext(), "email not valid", Toast.LENGTH_SHORT).show();
                }
                else if (user_password.length() < 4){
                    Toast.makeText(getApplicationContext(), "Password is too short, enter password minimum 4", Toast.LENGTH_SHORT).show();
                }

                mAuth.signInWithEmailAndPassword(user_email, user_password)
                        .addOnCompleteListener(loginactivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    Toast.makeText(loginactivity.this, "Sign In failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String id = mAuth.getUid();
                                    User user = new User(id,username,user_email,user_password);

                                    //passing data from activity to fragment

                                    Bundle bundle = new Bundle();
                                    bundle.putString(USER_ID, user.getId());

                                    startActivity(new Intent(loginactivity.this, btm_navigation.class).putExtras(bundle));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    private void Forgot_Password() {
        forgot_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginactivity.this, forgot_pass.class));
            }
        });
    }

    private void signInGoogle(){
    }
}



