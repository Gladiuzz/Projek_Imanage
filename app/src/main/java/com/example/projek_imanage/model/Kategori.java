package com.example.projek_imanage.model;

import androidx.annotation.NonNull;

public class Kategori {
    public String id_kategori, nama_kategori, mkey;

    public Kategori(){

    }

    public Kategori(String id_kategori, String nama_kategori) {
        this.id_kategori = id_kategori;
        this.nama_kategori = nama_kategori;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public String setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
        return nama_kategori;
    }

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    @NonNull
    @Override
    public String toString() {
        return nama_kategori;
    }
}
