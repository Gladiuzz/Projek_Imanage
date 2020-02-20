package com.example.projek_imanage.model;

public class Transaction {
    public String id_transaksi,id_item ,nama_Barang_transaksi, kategori_transaksi ,deskripsi_transaksi, tanggal_transaksi, mkey, id_qrCode;
    public Integer jumlah_transaksi, harga_transaksi;

    public Transaction(){

    }

    public Transaction(String id_transaksi,String id_item, String nama_Barang, String kategori,String deskripsi, Integer jumlah_keluar, Integer harga, String tanggal_transaksi) {
        this.id_transaksi = id_transaksi;
        this.id_item = id_item;
        this.nama_Barang_transaksi = nama_Barang;
        this.kategori_transaksi = kategori;
        this.deskripsi_transaksi = deskripsi;
        this.jumlah_transaksi = jumlah_keluar;
        this.harga_transaksi = harga;
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public String getNama_Barang_transaksi() {
        return nama_Barang_transaksi;
    }

    public String setNama_Barang_transaksi(String nama_Barang_transaksi) {
        this.nama_Barang_transaksi = nama_Barang_transaksi;
        return nama_Barang_transaksi;
    }

    public String getKategori_transaksi() {
        return kategori_transaksi;
    }

    public String setKategori_transaksi(String kategori_transaksi) {
        this.kategori_transaksi = kategori_transaksi;
        return kategori_transaksi;
    }

    public String getDeskripsi_transaksi() {
        return deskripsi_transaksi;
    }

    public String setDeskripsi_transaksi(String deskripsi_transaksi) {
        this.deskripsi_transaksi = deskripsi_transaksi;
        return deskripsi_transaksi;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public String setTanggal_transaksi(String tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
        return tanggal_transaksi;
    }

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public String getId_qrCode() {
        return id_qrCode;
    }

    public void setId_qrCode(String id_qrCode) {
        this.id_qrCode = id_qrCode;
    }

    public Integer getJumlah_transaksi() {
        return jumlah_transaksi;
    }

    public Integer setJumlah_transaksi(Integer jumlah_transaksi) {
        this.jumlah_transaksi = jumlah_transaksi;
        return jumlah_transaksi;
    }

    public Integer getHarga_transaksi() {
        return harga_transaksi;
    }

    public Integer setHarga_transaksi(Integer harga_transaksi) {
        this.harga_transaksi = harga_transaksi;
        return harga_transaksi;
    }
}
