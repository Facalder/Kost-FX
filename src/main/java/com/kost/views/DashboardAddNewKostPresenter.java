package com.kost.views;

import com.gluonhq.charm.glisten.afterburner.AppView;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.kost.iServices.IKostServices;
import com.kost.models.Kost;
import com.kost.services.KostServices;
import com.kost.utils.ImageUtils;
import com.kost.utils.OthersUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

public class DashboardAddNewKostPresenter {
    @FXML
    private View dashboardAddNewKost;

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

    public void initialize() {
        dashboardAddNewKost.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppManager appManager = AppManager.getInstance();
                AppBar appBar = appManager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.ARROW_BACK.button(e ->
                        AppViewManager.DASHBOARD_VIEW.switchView()
                ));
                appBar.getStyleClass().add("background-blue");
                appBar.setStyle("-fx-effect: none;");
                appBar.setTitleText("Add New Kost");
            }
        });
    }

    @FXML
    private void onChoiceImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedFotoKost = fileChooser.showOpenDialog(null);

        try {
            if (selectedFotoKost != null) {
                long maxFileSize = 1024 * 1024 * 3;

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
    private void handleAddNewKost() {
        String address = addressField.getText();
        String facilities = facilitiesField.getText();
        int price = Integer.parseInt(priceField.getText());
        int totalRoom = Integer.parseInt(totalRoomField.getText());

        try {
            if (selectedFotoKost == null) {
                OthersUtils.showAlert(Alert.AlertType.ERROR, "ERROR", "Please choose image first", "");
                return;
            }

            IKostServices kostServices = new KostServices();

            ImageUtils.compressImage(selectedFotoKost, 0.8f);
            Blob fotoBlob = ImageUtils.compressImage(selectedFotoKost, 0.8f);

            Kost kost = new Kost(address, facilities, price, totalRoom, fotoBlob);

            kostServices.addNewKost(kost);
            kostServices.showAllKosts().add(kost);
            resetInputs();

            AppViewManager.DASHBOARD_VIEW.switchView();
        } catch (Exception e) {
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
