package com.example.projek_imanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projek_imanage.fitur.Listitem;
import com.example.projek_imanage.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class detail_list extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    public String id_item ,nama_Barang, kategori ,deskripsi, tanggal, gambar_Barang, mkey,test;
    public Integer jumlah, harga;
    private Item items;
    private ImageView gambarBarang;
    private EditText edtnama, edtharga, edtkategori, edtdetail, edtjumlah, edttgl;
    private DatabaseReference dbr;
    private StorageReference SR;
    private FirebaseAuth mAuth;
    private Button btn_edit;
    private ProgressBar mProgressBar;
    private RelativeLayout mAddGambar;

    private Uri mGambarUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        id_item = getIntent().getStringExtra("id_barang");
        nama_Barang = getIntent().getStringExtra("nama_barang");
        kategori = getIntent().getStringExtra("kategori_barang");
        deskripsi = getIntent().getStringExtra("detail_barang");
        jumlah = getIntent().getIntExtra("jumlah_barang", 0);
        harga = getIntent().getIntExtra("harga_barang", 0);
        tanggal = getIntent().getStringExtra("tanggal_barang");
        gambar_Barang = getIntent().getStringExtra("gambar_Barang");

        items = new Item();

        component();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
        SR = FirebaseStorage.getInstance().getReference("Img_Barang");
        dbr = FirebaseDatabase.getInstance().getReference("Item").child(id);



        edtnama.setText(nama_Barang);
        edtkategori.setText(kategori);
        edtdetail.setText(deskripsi);
        edtjumlah.setText(String.valueOf(jumlah));
        edtharga.setText(String.valueOf(harga));
        edttgl.setText(tanggal);
        Glide.with(getApplicationContext()).load(gambar_Barang).into(gambarBarang);


        editData();

        // Buat memasukan gambar
        mAddGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });


    }

    public void editData(){
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Nama_item = items.setNama_Barang(edtnama.getText().toString().trim());
                final String Kategori = items.setKategori(edtkategori.getText().toString().trim());
                final Integer Jumlah = items.setJumlah(Integer.parseInt(edtjumlah.getText().toString()));
                final Integer Harga = items.setHarga(Integer.parseInt(edtharga.getText().toString()));
                final String Tanggal = items.setTanggal(edttgl.getText().toString().trim());
                final String Deskripsi = items.setDeskripsi(edtdetail.getText().toString().trim());
//                final String gambar_edt = Glide.with(getApplicationContext()).load(items.getGambar_Barang()).into(gambarBarang);

                if (mGambarUri != null){
                    final StorageReference fileReference = SR.child(System.currentTimeMillis() + "."+ getFileExtension(mGambarUri));
                    fileReference.putFile(mGambarUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mProgressBar.setProgress(0);
                                        }
                                    }, 4000);



                                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String gambar = uri.toString();

                                                String id = id_item;
                                                Item item = new Item(id, Nama_item, Kategori,Deskripsi, Jumlah, Harga, Tanggal, gambar);
                                                dbr.child(id).setValue(item);

                                        }
                                    });
                                    startActivity(new Intent(detail_list.this, btm_navigation.class));
                                }
                            })
                            //----------------------------------------------------------------------------------

                            // kalau input gagal
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            //----------------------------------------------------------------------------------

                            // Input sedang dalam tahap proses
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    mProgressBar.setProgress((int) progress);
                                }
                            });
                }
//
//
            }
        });
    }

    private void component(){
        edtnama = findViewById(R.id.sbtnama_barang);
        edtjumlah = findViewById(R.id.sbtquantity);
        edtkategori = findViewById(R.id.sbtkategori_barang);
        edtdetail = findViewById(R.id.sbtdetail);
        edtharga = findViewById(R.id.sbtharga);
        edttgl = findViewById(R.id.sbttanggal);
        mAddGambar = findViewById(R.id.layout_addGambar);
        gambarBarang = findViewById(R.id.img_barang);
        mProgressBar = findViewById(R.id.progress_bar);
        btn_edit = findViewById(R.id.btn_edt);
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    // Buat ngambil gambar
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mGambarUri = data.getData();
            gambarBarang.setImageURI(mGambarUri);
        }
    }

    // extension buat gambar
    private String getFileExtension(Uri uri){
        ContentResolver cR = getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
