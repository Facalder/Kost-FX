package com.kost.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginPresenter {

    @FXML
    private View login;

    @FXML
    private ResourceBundle resources;
    
    public void initialize() {
        login.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppManager appManager = AppManager.getInstance();
                AppBar appBar = appManager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        appManager.getDrawer().open()));
                appBar.setTitleText("Login");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> 
                        System.out.println("Search")));
                appBar.setVisible(false);
            }
        });
    }

    @FXML
    private void handleLogin() {
        AppViewManager.DASHBOARD_VIEW.switchView();
    }

    @FXML
    private void navigateToRegisterView() {
        try {
            AppViewManager.REGISTER_VIEW.switchView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
