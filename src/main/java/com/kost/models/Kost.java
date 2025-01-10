package com.kost.models;

public class Kost {
    private String alamat;
    private String fasilitas;
    private double harga;
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
