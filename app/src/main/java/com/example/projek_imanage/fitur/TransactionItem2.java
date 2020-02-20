package com.example.projek_imanage.fitur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projek_imanage.R;
import com.example.projek_imanage.btm_navigation;
import com.example.projek_imanage.model.Item;
import com.example.projek_imanage.model.Kategori;
import com.example.projek_imanage.model.Transaction;
import com.example.projek_imanage.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionItem2 extends AppCompatActivity {
    public String id_item ,nama_Barang, kategori ,deskripsi, tanggal, gambar_Barang, mkey, id_scan;
    public Integer jumlah, harga;
    public TextView mNama_det, mKategori_det, mHarga_det, mDeskripsi_det, mTanggal_transaksi,mJumlah_ada;
    public CircleImageView mImg_det;
    public Button btn_yes;
    private Item items;
    private Transaction transaction;
    private EditText mJumlah_det;

    private DatabaseReference dbr, dbr_item;
    private StorageReference SR;
    private FirebaseAuth mAuth;

    private String id_user, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_item2);
        id_item = getIntent().getStringExtra("id_barang");
        nama_Barang = getIntent().getStringExtra("nama_barang");
        kategori = getIntent().getStringExtra("kategori_barang");
        deskripsi = getIntent().getStringExtra("detail_barang");
        jumlah = getIntent().getIntExtra("jumlah_barang", 0);
        harga = getIntent().getIntExtra("harga_barang", 0);
        tanggal = getIntent().getStringExtra("tanggal_barang");
        gambar_Barang = getIntent().getStringExtra("gambar_Barang");
        id_scan = getIntent().getStringExtra("id_qrCode");

        transaction = new Transaction();

        mImg_det = findViewById(R.id.trans_gambar);
        mNama_det = findViewById(R.id.trans_nama);
        mHarga_det = findViewById(R.id.trans_harga);
        mKategori_det = findViewById(R.id.trans_kategori);
//        mDeskripsi_det = findViewById(R.id.trans_detail);
        mJumlah_det = findViewById(R.id.trans_jumlah);
        mTanggal_transaksi = findViewById(R.id.tgl_transaksi);
//        mJumlah_ada = findViewById(R.id.trans_jumlah_ada);
        btn_yes = findViewById(R.id.btn_yes);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        id_user = currentUser.getUid();
//        SR = FirebaseStorage.getInstance().getReference("Img_Barang");
        dbr = FirebaseDatabase.getInstance().getReference("Transaction").child(id_user);
        dbr_item = FirebaseDatabase.getInstance().getReference("Item").child(id_user);



        Locale localeID = new Locale("in", "ID");
        NumberFormat formatrupiah = NumberFormat.getCurrencyInstance(localeID);

        Glide.with(getApplicationContext()).load(gambar_Barang).into(mImg_det);
        mNama_det.setText(nama_Barang);
        mHarga_det.setText(formatrupiah.format(harga));
        mKategori_det.setText(kategori);
//        mDeskripsi_det.setText(deskripsi);
//        mJumlah_ada.setText(jumlah);



        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
        String str1 = dateFormat.format(c1.getTime());

        mTanggal_transaksi.setText(str1);

        kurang();

    }

    private void kurang(){
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Nama_item = transaction.setNama_Barang_transaksi(mNama_det.getText().toString().trim());
//                Kategori kategoriResult = (Kategori) kategori.getSelectedItem();
                final String Kategori = transaction.setKategori_transaksi(mKategori_det.getText().toString().trim());
                final String Jumlah = mJumlah_det.getText().toString();
                final String Harga = mHarga_det.getText().toString();
                final Integer Jumlah_keluaran = transaction.setJumlah_transaksi(Integer.parseInt(Jumlah));
                final Integer Harga_trans = transaction.setHarga_transaksi(harga);
                final String Tanggal_transaksi = transaction.setTanggal_transaksi(mTanggal_transaksi.getText().toString().trim());
                final String Deskripsi = transaction.setDeskripsi_transaksi(deskripsi.trim());

                id = dbr.push().getKey();
                Transaction transaksi = new
                        Transaction(id,id_item,Nama_item,Kategori,Deskripsi, Jumlah_keluaran ,Harga_trans ,Tanggal_transaksi);
                dbr.child(id).setValue(transaksi).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dbr_item.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                items = dataSnapshot.getValue(Item.class);
//                                Integer ada = items.getJumlah();
                                Integer lal = Jumlah_keluaran;
                                Integer hasil = jumlah - Jumlah_keluaran;
                                id = id_item;
                                Item item = new Item(id, Nama_item, Kategori,Deskripsi, hasil, harga, tanggal, gambar_Barang,id_scan);
                                dbr_item.child(id).setValue(item);
//                                mJumlah_ada.setText(String.valueOf(jumlah));
//                                 mJumlah_ada.setText(items.getJumlah());
                                Toast.makeText(TransactionItem2.this, "berhasil", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        startActivity(new Intent(TransactionItem2.this, btm_navigation.class));
                    }
                });


            }
        });
    }
}
