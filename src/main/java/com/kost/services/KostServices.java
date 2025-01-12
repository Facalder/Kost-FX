package com.kost.services;

import com.kost.db.DBConnection;
import com.kost.iServices.IAkunServices;
import com.kost.iServices.IKostServices;
import com.kost.models.Kost;
import com.kost.session.AkunSessionManager;
import com.kost.utils.OthersUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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
    public void updateKost(Kost kost, int kost_id) {
        String queryOwner = "SELECT id FROM akun WHERE nama_pengguna = ?";
        String queryCheck = "SELECT id FROM kost WHERE kost_id = ? AND id = ?";
        String queryUpdate = "UPDATE kost SET alamat = ?, fasilitas = ?, harga = ?, total_kamar = ? WHERE kost_id = ?";

        try (var con = DBConnection.connect()) {
            if (con != null) {
                PreparedStatement stmtOwner = con.prepareStatement(queryOwner);
                stmtOwner.setString(1, AkunSessionManager.getInstance().getCurrentAkun().getNama_pengguna());
                ResultSet resultOwner = stmtOwner.executeQuery();

                if (resultOwner.next()) {
                    int ownerId = resultOwner.getInt("id");

                    System.out.println(kost.getKost_id());

                    PreparedStatement stmtCheck = con.prepareStatement(queryCheck);
                    stmtCheck.setInt(1, kost_id);
                    stmtCheck.setInt(2, ownerId);
                    ResultSet resultCheck = stmtCheck.executeQuery();

                    if (resultCheck.next()) {
                        PreparedStatement stmtUpdate = con.prepareStatement(queryUpdate);
                        stmtUpdate.setString(1, kost.getAlamat());
                        stmtUpdate.setString(2, kost.getFasilitas());
                        stmtUpdate.setDouble(3, kost.getHarga());
                        stmtUpdate.setInt(4, kost.getTotal_kamar());
                        stmtUpdate.setInt(5, kost_id);
                        int rowsAffected = stmtUpdate.executeUpdate();

                        if (rowsAffected > 0) {
                            OthersUtils.showAlert(
                                    Alert.AlertType.INFORMATION,
                                    "INFORMASI",
                                    "Kost berhasil diperbarui.",
                                    ""
                            );
                        } else {
                            OthersUtils.showAlert(
                                    Alert.AlertType.ERROR,
                                    "ERROR",
                                    "Terjadi kesalahan saat memperbarui data kost.",
                                    ""
                            );
                        }
                    }else {
                        OthersUtils.showAlert(
                                Alert.AlertType.WARNING,
                                "PERINGATAN",
                                "Kost ini tidak dapat diedit karena bukan milik Anda.",
                                ""
                        );
                    }
                }else {
                    OthersUtils.showAlert(
                            Alert.AlertType.ERROR,
                            "ERROR",
                            "Pengguna tidak ditemukan.",
                            ""
                    );
                }
            }
        }catch (Exception e) {
            System.err.println("Terdapat error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteKost(int kost_id) {
        String queryOwner = "SELECT id FROM akun WHERE nama_pengguna = ?";
        String queryCheck = "SELECT id FROM kost WHERE kost_id = ? AND id = ?";
        String queryDelete = "DELETE FROM kost WHERE kost_id = ?";

        try (var con = DBConnection.connect()) {
            if (con != null) {
                PreparedStatement stmtOwner = con.prepareStatement(queryOwner);
                stmtOwner.setString(1, AkunSessionManager.getInstance().getCurrentAkun().getNama_pengguna());
                ResultSet resultOwner = stmtOwner.executeQuery();

                if (resultOwner.next()) {
                    int ownerId = resultOwner.getInt("id");

                    PreparedStatement stmtCheck = con.prepareStatement(queryCheck);
                    stmtCheck.setInt(1, kost_id);
                    stmtCheck.setInt(2, ownerId);
                    ResultSet resultCheck = stmtCheck.executeQuery();

                    if (resultCheck.next()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Konfirmasi");
                        alert.setHeaderText("Hapus Kost");
                        alert.setContentText("Apakah Anda yakin ingin menghapus kost ini?");
                        var result = alert.showAndWait();

                        if (result.isPresent()) {
                            PreparedStatement stmtDelete = con.prepareStatement(queryDelete);
                            stmtDelete.setInt(1, kost_id);
                            int rowsAffected = stmtDelete.executeUpdate();

                            if (rowsAffected > 0) {
                                OthersUtils.showAlert(
                                        Alert.AlertType.INFORMATION,
                                        "INFORMASI",
                                        "Kost berhasil dihapus.",
                                        ""
                                );
                            } else {
                                OthersUtils.showAlert(
                                        Alert.AlertType.ERROR,
                                        "ERROR",
                                        "Terjadi kesalahan saat menghapus kost.",
                                        ""
                                );
                            }
                        }
                    } else {
                        OthersUtils.showAlert(
                                Alert.AlertType.WARNING,
                                "PERINGATAN",
                                "Kost ini tidak dapat dihapus karena bukan milik Anda.",
                                ""
                        );
                    }
                } else {
                    OthersUtils.showAlert(
                            Alert.AlertType.ERROR,
                            "ERROR",
                            "Pengguna tidak ditemukan.",
                            ""
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Terdapat error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public ObservableList<Kost> showAllKosts() {
        String query = "SELECT * FROM kost WHERE id = ?";
        String queryOwner = "SELECT id FROM akun WHERE nama_pengguna = ?";

        ObservableList<Kost> kostList = FXCollections.observableArrayList();

        try (var con = DBConnection.connect()) {
            if (con != null) {
                PreparedStatement stmt = con.prepareStatement(queryOwner);
                stmt.setString(1, AkunSessionManager.getInstance().getCurrentAkun().getNama_pengguna());
                result = stmt.executeQuery();

                if (result.next()) {
                    int ownerId = result.getInt("id");

                    PreparedStatement stmtSelect = con.prepareStatement(query);
                    stmtSelect.setInt(1, ownerId);
                    ResultSet kostResult = stmtSelect.executeQuery();

                    while (kostResult.next()) {
                        int id = kostResult.getInt("id");

                        if (id == ownerId) {
                            String nama_pengguna = kostResult.getString("nama_pengguna");
                            String alamat = kostResult.getString("alamat");
                            String fasilitas = kostResult.getString("fasilitas");
                            double harga = kostResult.getDouble("harga");
                            double promoPercentage = kostResult.getDouble("promo_percentage");
                            Date promoExpiry = kostResult.getDate("promo_expiry");
                            int kost_id = kostResult.getInt("kost_id");
                            int total_kamar = kostResult.getInt("total_kamar");
                            int kamar_tersedia = kostResult.getInt("kamar_tersedia");

                            System.out.println(kost_id);

                            Kost kost = new Kost(id, nama_pengguna, alamat, fasilitas, harga, promoPercentage, promoExpiry, kost_id, total_kamar, kamar_tersedia, "");
                            kostList.add(kost);
                        }
                    }

                    kostResult.close();
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
        } catch (SQLException e) {
            System.err.println("Terdapat Error: " + e.getMessage());
            e.printStackTrace();
        }

        return kostList;
    }
}
