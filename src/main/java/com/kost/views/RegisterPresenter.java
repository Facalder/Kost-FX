package com.kost.views;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;

public class RegisterPresenter {

    @FXML
    private View register;

    public void initialize() {
        register.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppManager appManager = AppManager.getInstance();
                AppBar appBar = appManager.getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        appManager.getDrawer().open()));
                appBar.setTitleText("Register");
                appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> 
                        System.out.println("Favorite")));
                appBar.setVisible(false);
            }
        });
    }

    @FXML
    public void navigateToLoginView() {
        try {
            AppViewManager.LOGIN_VIEW.switchView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
