package com.kost.models;

import java.util.Date;

public class Kost {
    private int id;
    private String nama_pengguna;
    private String alamat;
    private String fasilitas;
    private double harga;
    private double promo_percentage;
    private Date promo_expiry;
    private int kost_id;
    private int total_kamar;
    private int kamar_tersedia;
    private String foto;

    public Kost(String alamat, String fasilitas, double harga, int total_kamar, int kamar_tersedia, String foto) {
        this.alamat = alamat;
        this.fasilitas = fasilitas;
        this.harga = harga;
        this.total_kamar = total_kamar;
        this.kamar_tersedia = kamar_tersedia;
        this.foto = foto;
    }

    public Kost(String alamat, String fasilitas, double harga, int total_kamar) {
        this.alamat = alamat;
        this.fasilitas = fasilitas;
        this.harga = harga;
        this.total_kamar = total_kamar;
    }

    public Kost(int id, String alamat, String fasilitas, double harga, double promoPercentage, Date promoExpiry) {
        this.id = id;
        this.alamat = alamat;
        this.fasilitas = fasilitas;
        this.harga = harga;
        this.promo_percentage = promoPercentage;
        this.promo_expiry = promoExpiry;
    }

    public Kost(int id, String nama_pengguna, String alamat, String fasilitas, double harga, double promo_percentage, Date promo_expiry, int kost_id, int total_kamar, int kamar_tersedia, String foto) {
        this.id = id;
        this.nama_pengguna = nama_pengguna;
        this.alamat = alamat;
        this.fasilitas = fasilitas;
        this.harga = harga;
        this.promo_percentage = promo_percentage;
        this.promo_expiry = promo_expiry;
        this.kost_id = kost_id;
        this.total_kamar = total_kamar;
        this.kamar_tersedia = kamar_tersedia;
        this.foto = foto;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getKost_id() {
        return kost_id;
    }

    public void setKost_id(int kost_id) {
        this.kost_id = kost_id;
    }

    public Integer getTotal_kamar() {
        return total_kamar;
    }

    public void setTotal_kamar(int total_kamar) {
        this.total_kamar = total_kamar;
    }

    public Integer getKamar_tersedia() {
        return kamar_tersedia;
    }

    public void setKamar_tersedia(int kamar_tersedia) {
        this.kamar_tersedia = kamar_tersedia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPromo_percentage() {
        return promo_percentage;
    }

    public void setPromo_percentage(double promo_percentage) {
        this.promo_percentage = promo_percentage;
    }

    public Date getPromo_expiry() {
        return promo_expiry;
    }

    public void setPromo_expiry(Date promo_expiry) {
        this.promo_expiry = promo_expiry;
    }

    @Override
    public String toString() {
        return "Kost{" +
                "alamat='" + alamat + '\'' +
                ", fasilitas='" + fasilitas + '\'' +
                ", harga=" + harga +
                ", total_kamar=" + total_kamar +
                ", kamar_tersedia=" + kamar_tersedia +
                ", foto='" + foto + '\'' +
                '}';
    }
}
