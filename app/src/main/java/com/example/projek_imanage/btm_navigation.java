package com.example.projek_imanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.projek_imanage.fitur.Homeitem;
import com.example.projek_imanage.fitur.Listitem;
import com.example.projek_imanage.fitur.transactionitem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class btm_navigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton navigation_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btm_navigation);


        loadfragment(new Homeitem());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        navigation_add = findViewById(R.id.navigation_add);
        navigation_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new add_Fragment();
                loadfragment(fragment);
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


}
