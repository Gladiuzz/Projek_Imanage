package com.example.projek_imanage;

import android.content.Intent;
import android.os.Bundle;

import com.example.projek_imanage.Adapter.CategoriesAdapter;
import com.example.projek_imanage.Adapter.ItemAdapter;
import com.example.projek_imanage.fitur.Homeitem;
import com.example.projek_imanage.fitur.Listitem;
import com.example.projek_imanage.model.Item;
import com.example.projek_imanage.model.Kategori;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class categories extends AppCompatActivity implements CategoriesAdapter.OnItemClickListener  {
    private CategoriesAdapter mAdapter;
    private RecyclerView cat_RV;
    private Kategori kategori;
    private List<Kategori> mkategori;
    private DatabaseReference dbr;
    private FirebaseAuth mAuth;
    private ValueEventListener mDBListener;
    private ProgressBar mprogressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cat_RV = findViewById(R.id.categories_RV);
        mprogressCircle = findViewById(R.id.progress_circle_kategori);
        cat_RV.setHasFixedSize(true);
        cat_RV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        kategori = new Kategori();

        mkategori = new ArrayList<>();

        mAdapter = new CategoriesAdapter(getApplicationContext(), mkategori);

        cat_RV.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(categories.this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();

        dbr = FirebaseDatabase.getInstance().getReference("Kategori").child(id);

        FloatingActionButton fab = findViewById(R.id.add_categories);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), add_Categories.class));
//                Snackbar.make(view, "Test", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        mDBListener = dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Toast.makeText(categories.this, , Toast.LENGTH_SHORT).show();
                mkategori.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Kategori kategori = itemSnapshot.getValue(Kategori.class);
                    kategori.setMkey(itemSnapshot.getKey());
                    mkategori.add(kategori);
                }
                mAdapter.notifyDataSetChanged();

                mprogressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mprogressCircle.setVisibility(View.INVISIBLE);
            }


        });

    }
    @Override
    public void onItemClick(Kategori kategori) {

    }

    @Override
    public void onDeleteClick(int position) {

    }

    @Override
    public void onEditClick(Kategori kategori) {

    }
}
