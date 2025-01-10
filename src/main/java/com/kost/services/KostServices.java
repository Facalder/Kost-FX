package com.kost.services;

import com.kost.db.DBConnection;
import com.kost.iServices.IAkunServices;
import com.kost.iServices.IKostServices;
import com.kost.models.Kost;
import com.kost.session.AkunSessionManager;
import com.kost.utils.OthersUtils;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KostServices implements IKostServices {
    private final IAkunServices akunServices = new AkunServices();

    private ResultSet result;

    @Override
    public void addNewKost(Kost kost) {
        String query = "INSERT INTO kost (id, nama_pengguna, alamat, fasilitas, harga, total_kamar, kamar_tersedia, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String queryOwner = "SELECT id FROM akun WHERE nama_pengguna = ?";

        try (var con = DBConnection.connect()) {
            if (con != null) {
                if (kost.getAlamat().isEmpty() || kost.getFasilitas().isEmpty() || Double.isNaN(kost.getHarga()) || kost.getTotal_kamar() == null || kost.getKamar_tersedia() == null) {
                    OthersUtils.showAlert(
                            Alert.AlertType.ERROR,
                            "ERROR",
                            "Please fill all this form field",
                            ""
                    );
                } else {
                    PreparedStatement stmt = con.prepareStatement(queryOwner);
                    stmt.setString(1, AkunSessionManager.getInstance().getCurrentAkun().getNama_pengguna());
                    result = stmt.executeQuery();

                    if (result.next()) {
                        int ownerId = result.getInt("id");

                        PreparedStatement stmtInsert = con.prepareStatement(query);
                        stmtInsert.setInt(1, ownerId);
                        stmtInsert.setInt(2, ownerId);
                        stmtInsert.setString(3, kost.getAlamat());
                        stmtInsert.setString(4, kost.getFasilitas());
                        stmtInsert.setDouble(5, kost.getHarga());
                        stmtInsert.setInt(6, kost.getTotal_kamar());
                        stmtInsert.setInt(7, kost.getKamar_tersedia());
                        stmtInsert.setString(8, "");

                        int rowsAffected = stmtInsert.executeUpdate();

                        if (rowsAffected > 0) {
                            OthersUtils.showAlert(
                                    Alert.AlertType.CONFIRMATION,
                                    "SUCCESS",
                                    "Successfully Added New Kost",
                                    ""
                            );
                        } else {
                            OthersUtils.showAlert(
                                    Alert.AlertType.ERROR,
                                    "ERROR",
                                    "Error to Add New Kost",
                                    ""
                            );
                        }
                    } else {
                        OthersUtils.showAlert(
                                Alert.AlertType.ERROR,
                                "ERROR",
                                "User doesn't exist",
                                ""
                        );
                    }

                    result.close();
                }
            }
        } catch (SQLException e) {
            System.err.println("Terdapat Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateKost(Kost kost) {

    }

    @Override
    public void deleteKost(Kost kost) {

    }

    @Override
    public void showAllKosts() {

    }
}
