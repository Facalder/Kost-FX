package com.kost.views;

import com.gluonhq.charm.glisten.afterburner.AppView;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;

public class DashboardAddNewKostPresenter {
    @FXML
    private View dashboardAddNewKost;

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
}
