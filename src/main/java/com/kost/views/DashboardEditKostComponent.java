package com.kost.views;

import com.gluonhq.charm.glisten.mvc.View;
import com.kost.iServices.IKostServices;
import com.kost.models.Kost;
import com.kost.services.KostServices;
import com.kost.utils.ImageUtils;
import com.kost.utils.OthersUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardEditKostComponent {
    /*private Stage dialogStage;

    private Kost kost;

    public DashboardEditKostComponent() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleCancelAction() {
        dialogStage.close();
    }*/


    @FXML
    private View editKostModal;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField addressField;

    @FXML
    private TextField facilitiesField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField totalRoomField;

    @FXML
    private Label imagePathLabel;

    @FXML
    private ImageView previewImageView;

    @FXML
    private File selectedFotoKost;

    private Stage dialogStage;

    private Kost kost;

    private int kost_id;

    public DashboardEditKostComponent() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setKost_id(int kost_id) {
        this.kost_id = kost_id;
    }

    @FXML
    public void handleCloseAction() {
        dialogStage.close();
    }

    public void setAllData(Kost kost) {
        this.kost = kost;
        setTextField(kost.getAlamat(), kost.getFasilitas(), kost.getHarga(), kost.getTotal_kamar());

        if (kost.getFoto() != null) {
            try {
                Blob foto = kost.getFoto();
                InputStream fis = foto.getBinaryStream();
                Image image = new Image(fis);

                previewImageView.setImage(image);
                previewImageView.setVisible(true);
                imagePathLabel.setText("Existing image: " + kost.getKost_id());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            previewImageView.setImage(null);
            previewImageView.setVisible(false);
            imagePathLabel.setText("No image available");
        }
    }

    public void setTextField(String address, String facilities, double price, int totalRoom) {
        addressField.setText(address);
        facilitiesField.setText(facilities);
        priceField.setText(String.valueOf(price));
        totalRoomField.setText(String.valueOf(totalRoom));
    }

    @FXML
    private void onChoiceImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedFotoKost = fileChooser.showOpenDialog(null);

        try {
            if (selectedFotoKost != null) {
                long maxFileSize = 1024 * 1024 * 2;

                if (!ImageUtils.validateImageFileSize(selectedFotoKost, maxFileSize)) {
                    OthersUtils.showAlert(Alert.AlertType.ERROR, "ERROR", "File size its to big! Maks 5mb allowed", "");
                    selectedFotoKost = null;
                    imagePathLabel.setText("There's no image selected");
                    previewImageView.setImage(null);
                    previewImageView.setVisible(false);
                    return;
                }else {
                    imagePathLabel.setText(selectedFotoKost.getName());
                    Image image = new Image(selectedFotoKost.toURI().toString());
                    previewImageView.setImage(image);
                    previewImageView.setVisible(true);
                }
            } else {
                imagePathLabel.setText("There's no image selected");
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void onClearImage() {
        selectedFotoKost = null;
        imagePathLabel.setText("There's no image selected");
        previewImageView.setImage(null);
        previewImageView.setVisible(false);
    }

    @FXML
    public void handleEditKostAction() {
        String address = addressField.getText();
        String facilities = facilitiesField.getText();
        double price = Double.parseDouble(priceField.getText());
        int totalRoom = Integer.parseInt(totalRoomField.getText());

        try {
            IKostServices kostServices = new KostServices();

            Blob fotoBlob = null;
            if (selectedFotoKost != null) {
                fotoBlob = ImageUtils.compressImage(selectedFotoKost, 0.8f);
            }else {
                if (kost != null && kost.getFoto() != null) {
                    fotoBlob = kost.getFoto();
                }
            }

            Kost kost = new Kost(address, facilities, price, totalRoom, fotoBlob);

            kostServices.updateKost(kost, kost_id);
            kostServices.showAllKosts().setAll(kost);
            resetInputs();
            dialogStage.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void resetInputs() {
        addressField.clear();
        facilitiesField.clear();
        priceField.clear();
        totalRoomField.clear();
        imagePathLabel.setText("There's no image selected");
        previewImageView.setImage(null);
        previewImageView.setVisible(false);
    }
}
