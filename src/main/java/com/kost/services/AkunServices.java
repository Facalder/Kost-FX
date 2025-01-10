package com.kost.services;

import com.kost.db.DBConnection;
import com.kost.iServices.IAkunServices;
import com.kost.models.Akun;
import com.kost.utils.OthersUtils;
import com.kost.utils.PasswordUtils;
import com.kost.views.AppViewManager;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AkunServices implements IAkunServices {
    private String loggedInUsername;
    private String loggedInPassword;
    private String loggedInUserId;

    private ResultSet result;


    @Override
    public void login(Akun akun) {
        String query = "SELECT * FROM akun WHERE nama_pengguna = ?";

        try (var con = DBConnection.connect()) {
            if (con != null) {
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, akun.getNama_pengguna());
                stmt.executeQuery();
                result = stmt.getResultSet();

                if (result.next()) {
                    boolean passwordPassed = PasswordUtils.checkPassword(akun.getKata_sandi(), result.getString("kata_sandi"));

                    if (!passwordPassed) {
                        System.out.println("Password Salah");
                    }else {
                        loggedInUsername = akun.getNama_pengguna();
                        loggedInPassword = akun.getKata_sandi();
                        loggedInUserId = result.getString("id");

                        if (result.getString("role").equals("admin")) {
                            // AppViewManager.DASHBOARD_VIEW.switchView();
                        }else {
                            System.out.println("Role tidak ditemukan!");
                        }
                    }
                }else {
                    System.out.println("Nama pengguna tidak ditemukan!");
                }
            }
        }catch (SQLException e) {
            System.err.println("Terdapat Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void register(Akun akun) {
        String query_check = "SELECT * FROM akun WHERE nama_pengguna = ?";
        String query = "INSERT INTO akun (nama_pengguna, kata_sandi, role, security_question, security_answer) VALUES (?, ?, ?, ?, ?)";

        try (var con = DBConnection.connect()) {
            if (con != null) {
                // Cek apakah username sudah ada
                PreparedStatement stmtCheck = con.prepareStatement(query_check);
                stmtCheck.setString(1, akun.getNama_pengguna());
                ResultSet result = stmtCheck.executeQuery();

                if (result.next()) {
                    // Username sudah terdaftar
                    OthersUtils.showAlert(Alert.AlertType.ERROR, "ERROR", "Nama Pengguna Telah Terdaftar!", "Tolong gunakan username lainnya!");
                } else {
                    // Hash password sebelum disimpan
                    String hashedPassword = PasswordUtils.hashPassword(akun.getKata_sandi());

                    // Masukkan data ke database
                    PreparedStatement stmtInsert = con.prepareStatement(query);
                    stmtInsert.setString(1, akun.getNama_pengguna());
                    stmtInsert.setString(2, hashedPassword);
                    stmtInsert.setString(3, akun.getRole());
                    stmtInsert.setString(4, akun.getSecurity_question());
                    stmtInsert.setString(5, akun.getSecurity_answer());

                    int rowsAffected = stmtInsert.executeUpdate(); // Gunakan executeUpdate untuk perintah INSERT

                    if (rowsAffected > 0) {
                        // Registrasi berhasil
                        loggedInUsername = akun.getNama_pengguna();
                        loggedInPassword = akun.getNama_pengguna();

                        OthersUtils.showAlert(Alert.AlertType.INFORMATION, "SUCCESS", "Registrasi Berhasil", "Akun Anda berhasil dibuat!");

                        // Alihkan ke halaman dashboard jika role adalah admin
                        if ("admin".equals(result.getString("role"))) {
                            // AppViewManager.DASHBOARD_VIEW.switchView();
                        }
                    } else {
                        OthersUtils.showAlert(Alert.AlertType.ERROR, "ERROR", "Registrasi Gagal", "Terjadi kesalahan saat membuat akun.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Terdapat Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getLoggedInUsername() {
        return "";
    }

    @Override
    public String getLoggedInPassword() {
        return "";
    }

    @Override
    public String getLoggedInUserId() {
        return "";
    }
}
