package com.tokopancing;

public abstract class produk {
    private String idProduk;
    private String nama;
    private String merek;
    private double harga;

    public produk(String idProduk, String nama, String merek, double harga) {
        this.idProduk = idProduk;
        this.nama = nama;
        this.merek = merek;
        this.harga = harga;
    }

    public void tampil() {
        System.out.println("Nama = " + this.nama);
        System.out.println("Merek = " + this.merek);
        System.out.println("Harga = " + this.harga);
    }

    public abstract void saveToDB();

    public String getIdProduk() {
        return idProduk;
    }
    public String getNama() {
        return nama;
    }

    public String getMerek() {
        return merek;
    }

    public double getHarga() {
        return harga;
    }
    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
}
