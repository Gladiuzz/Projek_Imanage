package com.example.projek_imanage.model;

import com.google.firebase.database.Exclude;

public class Item {
    public String id_item ,nama_Barang, kategori ,deskripsi, tanggal, gambar_Barang, mkey;
    public Integer jumlah, harga;

    public Item(){

    }



    public Item(String id_item, String nama_Barang, String kategori,String deskripsi, Integer jumlah, Integer harga, String tanggal, String gambar_Barang) {
        this.id_item = id_item;
        this.nama_Barang = nama_Barang;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.jumlah = jumlah;
        this.harga = harga;
        this.tanggal = tanggal;
        this.gambar_Barang = gambar_Barang;
    }



    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public String getNama_Barang() {
        return nama_Barang;
    }

    public void setNama_Barang(String nama_Barang) {
        this.nama_Barang = nama_Barang;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar_Barang() {
        return gambar_Barang;
    }

    public void setGambar_Barang(String gambar_Barang) {
        this.gambar_Barang = gambar_Barang;
    }

    @Exclude
    public String getkey() {
        return mkey;
    }

    @Exclude
    public void setkey(String mkey) {
        this.mkey = mkey;
    }
}
