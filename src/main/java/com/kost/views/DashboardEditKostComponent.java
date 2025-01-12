package com.kost.views;

import com.gluonhq.charm.glisten.mvc.View;
import com.kost.iServices.IKostServices;
import com.kost.models.Kost;
import com.kost.services.KostServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
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

    private Stage dialogStage;

    private Kost kost;

    private int kost_id;

    public DashboardEditKostComponent() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void resetInputs() {
        addressField.clear();
        facilitiesField.clear();
        priceField.clear();
        totalRoomField.clear();
    }

    public void setTextField(String address, String facilities, double price, int totalRoom) {
        addressField.setText(address);
        facilitiesField.setText(facilities);
        priceField.setText(String.valueOf(price));
        totalRoomField.setText(String.valueOf(totalRoom));
    }

    public void setKost_id(int kost_id) {
        this.kost_id = kost_id;
    }

    @FXML
    public void handleCloseAction() {
        dialogStage.close();
    }

    @FXML
    public void handleEditKostAction() {
        String address = addressField.getText();
        String facilities = facilitiesField.getText();
        double price = Double.parseDouble(priceField.getText());
        int totalRoom = Integer.parseInt(totalRoomField.getText());

        try {
            IKostServices kostServices = new KostServices();
            Kost kost = new Kost(address, facilities, price, totalRoom);

            kostServices.updateKost(kost, kost_id);
            kostServices.showAllKosts().setAll(kost);
            resetInputs();
            dialogStage.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
