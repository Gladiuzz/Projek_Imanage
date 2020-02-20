package com.example.projek_imanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projek_imanage.model.Kategori;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class add_Categories extends AppCompatActivity {
    private EditText nama_kategori;
    private Button sbt_btn;
    private Kategori kategori;
    private FirebaseAuth mAuth;
    private DatabaseReference dbr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__categories);
        nama_kategori = findViewById(R.id.nama_kategori);
        sbt_btn = findViewById(R.id.submit_btn);

        kategori = new Kategori();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
//        SR = FirebaseStorage.getInstance().getReference("Img_Barang");
        dbr = FirebaseDatabase.getInstance().getReference("Kategori").child(id);

        sbt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_categories();
            }
        });
    }

    public void add_categories(){
        String id = dbr.push().getKey();
        String kategori_item = kategori.setNama_kategori(nama_kategori.getText().toString().trim());
//        String Deskripsi = items.setDeskripsi(desc.getText().toString().trim());

        Kategori kategori = new Kategori(id,kategori_item);
        dbr.child(id).setValue(kategori).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(add_Categories.this, "Berhasil menambah", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
