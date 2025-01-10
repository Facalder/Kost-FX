package com.kost.views;

import com.airhacks.afterburner.injection.Injector;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.kost.models.Akun;
import com.kost.session.AkunSessionManager;
import com.kost.utils.OthersUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

public class DashboardPresenter {
    @FXML
    private View dashboard;

    @FXML
    private Label subtitle1;

    @FXML
    private ResourceBundle resources;

    public void initialize() {
        FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.ADD.text,
                e -> AppViewManager.DASHBOARD_ADD_NEW_KOST_VIEW.switchView());
        fab.showOn(dashboard);
        fab.getStyleClass().add("floating-action-button");

        dashboard.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppManager appManager = AppManager.getInstance();
                AppBar appBar = appManager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        appManager.getDrawer().open()));
                appBar.setTitleText("Dashboard - Home");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        System.out.println("Search")));
                appBar.setVisible(false);
            }
        });
    }

    @FXML
    private void handleLogout() {
        try {
            OthersUtils.showAlert(
                    Alert.AlertType.CONFIRMATION,
                    "CONFIRMATION",
                    "Are you sure want to logout?",
                    "You can log in again"
            );

            AkunSessionManager.getInstance().removeCurrentAkun();
            Injector.forgetAll();

            OthersUtils.showAlert(
                    Alert.AlertType.INFORMATION,
                    "INFO",
                    "Logout Successful and Cache Cleared",
                    ""
            );

            AppViewManager.LOGIN_VIEW.switchView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
