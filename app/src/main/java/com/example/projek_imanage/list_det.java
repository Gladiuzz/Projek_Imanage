package com.example.projek_imanage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

public class list_det extends AppCompatActivity {
    public String id_item ,nama_Barang, kategori ,deskripsi, tanggal, gambar_Barang, mkey;
    public Integer jumlah, harga;
    public ImageView mImg_det;
    public TextView mNama_det, mKategori_det, mHarga_det, mDeskripsi_det, mJumlah_det;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_det);

        id_item = getIntent().getStringExtra("id_barang");
        nama_Barang = getIntent().getStringExtra("nama_barang");
        kategori = getIntent().getStringExtra("kategori_barang");
        deskripsi = getIntent().getStringExtra("detail_barang");
        jumlah = getIntent().getIntExtra("jumlah_barang", 0);
        harga = getIntent().getIntExtra("harga_barang", 0);
        tanggal = getIntent().getStringExtra("tanggal_barang");
        gambar_Barang = getIntent().getStringExtra("gambar_Barang");

        mImg_det = findViewById(R.id.img_det);
        mNama_det = findViewById(R.id.nama_det);
        mHarga_det = findViewById(R.id.harga_det);
        mKategori_det = findViewById(R.id.kategori_det);
        mDeskripsi_det = findViewById(R.id.item_det);
        mJumlah_det = findViewById(R.id.jumlah_barang);


        Locale localeID = new Locale("in", "ID");
        NumberFormat formatrupiah = NumberFormat.getCurrencyInstance(localeID);

        Glide.with(getApplicationContext()).load(gambar_Barang).into(mImg_det);
        mNama_det.setText(nama_Barang);
        mHarga_det.setText(formatrupiah.format(harga));
        mKategori_det.setText(kategori);
        mDeskripsi_det.setText(deskripsi);
        mJumlah_det.setText(String.valueOf(jumlah));



    }
}
