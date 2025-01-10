package com.kost.models;

public class Akun {
    private String nama_pengguna;
    private String kata_sandi;
    private String role;
    private String security_question;
    private String security_answer;
    private boolean session;

    public Akun(String nama_pengguna, String kata_sandi, String role, String security_question, String security_answer) {
        this.nama_pengguna = nama_pengguna;
        this.kata_sandi = kata_sandi;
        this.role = role;
        this.security_question = security_question;
        this.security_answer = security_answer;
    }

    public Akun(String nama_pengguna, String kata_sandi, String role, String security_question, String security_answer, boolean session) {
        this.nama_pengguna = nama_pengguna;
        this.kata_sandi = kata_sandi;
        this.role = role;
        this.security_question = security_question;
        this.security_answer = security_answer;
        this.session = session;
    }

    public Akun(String nama_pengguna, String kata_sandi)  {
        this.nama_pengguna = nama_pengguna;
        this.kata_sandi = kata_sandi;
    }

    public Akun(String nama_pengguna, String kata_sandi, boolean session)  {
        this.nama_pengguna = nama_pengguna;
        this.kata_sandi = kata_sandi;
        this.session = session;
    }

    public Akun() {

    }

    public String getNama_pengguna() {
        return nama_pengguna;
    }

    public void setNama_pengguna(String nama_pengguna) {
        this.nama_pengguna = nama_pengguna;
    }

    public String getKata_sandi() {
        return kata_sandi;
    }

    public void setKata_sandi(String kata_sandi) {
        this.kata_sandi = kata_sandi;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(String security_question) {
        this.security_question = security_question;
    }

    public String getSecurity_answer() {
        return security_answer;
    }

    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }

    public boolean getSession() {
        return session;
    }

    public void setSession(boolean session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Akun{" +
                "nama_pengguna='" + nama_pengguna + '\'' +
                ", kata_sandi='" + kata_sandi + '\'' +
                ", role='" + role + '\'' +
                ", security_question='" + security_question + '\'' +
                ", security_answer='" + security_answer + '\'' +
                '}';
    }
}
