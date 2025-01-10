package com.kost.views;

import com.gluonhq.charm.glisten.afterburner.AppView;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.kost.iServices.IKostServices;
import com.kost.models.Kost;
import com.kost.services.KostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

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
    private void handleAddNewKost() {
        String address = addressField.getText();
        String facilities = facilitiesField.getText();
        int price = Integer.parseInt(priceField.getText());
        int totalRoom = Integer.parseInt(totalRoomField.getText());

        try {
            IKostServices kostServices = new KostServices();
            Kost kost = new Kost(address, facilities, price, totalRoom);

            kostServices.addNewKost(kost);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void resetInputs() {
        addressField.clear();
        facilitiesField.clear();
        priceField.clear();
        totalRoomField.clear();
    }
}
