package com.example.projek_imanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.projek_imanage.fitur.Homeitem;
import com.example.projek_imanage.fitur.Listitem;
import com.example.projek_imanage.fitur.add_Fragment;
import com.example.projek_imanage.fitur.profileitem;
import com.example.projek_imanage.fitur.transactionitem;
import com.example.projek_imanage.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class btm_navigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ImageView logout;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference dbr;
    private User users;
    private StorageReference SR;


    private FloatingActionButton navigation_add;
    private DrawerLayout drawer;
    private View headerLayout;
    private TextView getUsername, getEmail;
    private CircleImageView getAvatar;
    private NavigationView nv;
    private ActionBarDrawerToggle t;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btm_navigation);




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_more_vert_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        SR = firebaseStorage.getReference();

        ForDrawer();

        loadfragment(new Homeitem());

        BottomNavigationView navigation = findViewById(R.id.btm_navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        navigation_add = findViewById(R.id.navigation_add);
        navigation_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_Fragment add = new add_Fragment();
                loadfragment(add);
            }
        });

//        show_data();

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
            case R.id.navigation_profile:
                fragment = new profileitem();
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

//    private void show_data(){
//
//
//        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();
//        String id = mAuth.getUid();
//        dbr = FirebaseDatabase.getInstance().getReference("Users").child(id);
//        dbr.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String user_name = dataSnapshot.child("name").getValue(String.class);
//                getUsername.setText(user_name);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }


    public void ForDrawer(){
        nv = (NavigationView) findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.navigation_logout){
                    Toast.makeText(btm_navigation.this, "test 123", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), loginactivity.class));
                    finish();
                }

                return true;
            }

        });


        id = currentUser.getUid();
        String email = currentUser.getEmail();

        headerLayout = nv.getHeaderView(0);
        getEmail = (TextView) headerLayout.findViewById(R.id.email_header);
        getUsername = (TextView) headerLayout.findViewById(R.id.name_header);
        getAvatar = (CircleImageView) headerLayout.findViewById(R.id.avatar);
        getEmail.setText(email);
        dbr = FirebaseDatabase.getInstance().getReference("Users").child(id);
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String user_name = dataSnapshot.child("name").getValue(String.class);
                getUsername.setText(user_name);
                Glide.with(getApplicationContext()).load(dataSnapshot.child("avatar").getValue(String.class)).into(getAvatar);

//                SR.child("img_Avatar").child(id).child("test").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        String gambar = uri.toString();
//
//                        Glide.with(getApplicationContext()).load(gambar).into(getAvatar);
//                    }
//                });

//                if (dataSnapshot.exists()){
//                    countItems = (int) dataSnapshot.getChildrenCount();
//                    username.setText(Integer.toString(countItems) + "Items");
//                }
//                else{
//                    username.setText("0 Items");
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
            return true;
        }

        return true;
    }




}
