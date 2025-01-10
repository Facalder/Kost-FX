package com.kost.services;

import com.kost.db.DBConnection;
import com.kost.iServices.IAkunServices;
import com.kost.models.Akun;
import com.kost.session.AkunSessionManager;
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
    private int loggedInUserId;

    private boolean loggedIn;
    private boolean registered;

    private ResultSet result;

    @Override
    public void login(Akun akun) {
        String query = "SELECT * FROM akun WHERE nama_pengguna = ?";

        try (var con = DBConnection.connect()) {
            if (con != null) {
                if (akun.getNama_pengguna().isEmpty() && akun.getKata_sandi().isEmpty()) {
                    OthersUtils.showAlert(
                            Alert.AlertType.ERROR,
                            "ERROR",
                            "You must fill username and password",
                            ""
                    );
                }else {
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, akun.getNama_pengguna());
                    stmt.executeQuery();
                    result = stmt.getResultSet();

                    if (result.next()) {
                        boolean passwordPassed = PasswordUtils.checkPassword(akun.getKata_sandi(), result.getString("kata_sandi"));

                        if (!passwordPassed) {
                            OthersUtils.showAlert(
                                    Alert.AlertType.ERROR,
                                    "ERROR",
                                    "Password Incorrect",
                                    ""
                            );
                        }else {
                            if (result.getString("role").equals("admin")) {
                                AkunSessionManager session = AkunSessionManager.getInstance();
                                session.setCurrentAkun(akun);
                                System.out.println(session.getCurrentAkun());

                                OthersUtils.showAlert(
                                        Alert.AlertType.CONFIRMATION,
                                        "SUCCESS",
                                        "Login Successful",
                                        ""
                                );

                                AppViewManager.DASHBOARD_VIEW.switchView();
                            }else {
                                OthersUtils.showAlert(
                                        Alert.AlertType.ERROR,
                                        "ERROR",
                                        "Role doesn't exist",
                                        ""
                                );
                            }
                        }
                    }else {
                        OthersUtils.showAlert(
                                Alert.AlertType.ERROR,
                                "ERROR",
                                "User doesn't exist",
                                ""
                        );
                    }
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
                if (akun.getNama_pengguna().isEmpty() || akun.getKata_sandi().isEmpty() || akun.getRole().isEmpty() || akun.getSecurity_question().isEmpty() || akun.getSecurity_answer().isEmpty()) {
                    OthersUtils.showAlert(
                            Alert.AlertType.ERROR,
                            "ERROR",
                            "You must fill this all form field",
                            ""
                    );
                } else {
                    PreparedStatement stmtCheck = con.prepareStatement(query_check);
                    stmtCheck.setString(1, akun.getNama_pengguna());
                    ResultSet result = stmtCheck.executeQuery();

                    if (result.next()) {
                        OthersUtils.showAlert(Alert.AlertType.ERROR, "ERROR", "Nama Pengguna Telah Terdaftar!", "Tolong gunakan username lainnya!");
                    } else {
                        String hashedPassword = PasswordUtils.hashPassword(akun.getKata_sandi());

                        PreparedStatement stmtInsert = con.prepareStatement(query);
                        stmtInsert.setString(1, akun.getNama_pengguna());
                        stmtInsert.setString(2, hashedPassword);
                        stmtInsert.setString(3, akun.getRole());
                        stmtInsert.setString(4, akun.getSecurity_question());
                        stmtInsert.setString(5, akun.getSecurity_answer());

                        int rowsAffected = stmtInsert.executeUpdate();

                        if (rowsAffected > 0) {
                            OthersUtils.showAlert(Alert.AlertType.INFORMATION, "SUCCESS", "Registrasi Berhasil", "Akun Anda berhasil dibuat!");

                            PreparedStatement stmtRoleCheck = con.prepareStatement(query_check);
                            stmtRoleCheck.setString(1, akun.getNama_pengguna());
                            ResultSet roleResult = stmtRoleCheck.executeQuery();

                            if (roleResult.next() && "admin".equals(roleResult.getString("role"))) {
                                setRegistered(true);

                                OthersUtils.showAlert(
                                        Alert.AlertType.CONFIRMATION,
                                        "SUCCESS",
                                        "Register Successful",
                                        ""
                                );

                                AppViewManager.DASHBOARD_VIEW.switchView();
                            }
                        } else {
                            OthersUtils.showAlert(Alert.AlertType.ERROR, "ERROR", "Registrasi Gagal", "Terjadi kesalahan saat membuat akun.");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Terdapat Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void logout() {

    }

    @Override
    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    @Override
    public String getLoggedInPassword() {
        return loggedInPassword;
    }

    @Override
    public int getLoggedInUserId() {
        return loggedInUserId;
    }

    @Override
    public boolean getLoggedInStatus() {
        return loggedIn;
    }

    @Override
    public boolean getRegisteredStatus() {
        return registered;
    }

    private void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    private void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
