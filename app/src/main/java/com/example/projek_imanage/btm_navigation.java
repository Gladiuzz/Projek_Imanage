package com.example.projek_imanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.projek_imanage.fitur.Homeitem;
import com.example.projek_imanage.fitur.Listitem;
import com.example.projek_imanage.fitur.add_Fragment;
import com.example.projek_imanage.fitur.transactionitem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class btm_navigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static final String USER_ID = "user_id";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";


    FloatingActionButton navigation_add;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btm_navigation);

        Bundle bundle1 = getIntent().getExtras();

        final String user_id = bundle1.getString(loginactivity.USER_ID);


        loadfragment(new Homeitem());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        navigation_add = findViewById(R.id.navigation_add);
        navigation_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundles = new Bundle();
                bundles.putString(USER_ID, user_id);
                add_Fragment add = new add_Fragment();
                add.setArguments(bundles);
                loadfragment(add);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new Homeitem();
                break;
            case R.id.navigation_transaction:
                fragment = new transactionitem();
                break;
            case R.id.navigation_list:
                fragment = new Listitem();
                break;
        }
        return loadfragment(fragment);
    }

    private boolean loadfragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.isi_content, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void logout(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });

    }


}
