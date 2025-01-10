package com.kost.views;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import java.util.ResourceBundle;

import com.kost.iServices.IAkunServices;
import com.kost.models.Akun;
import com.kost.services.AkunServices;
import com.kost.utils.OthersUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPresenter {

    @FXML
    private View login;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    
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
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            IAkunServices akunServices = new AkunServices();
            Akun akun = new Akun(username, password);

            akunServices.login(akun);

            if (akunServices.getLoggedInStatus()) resetInputs();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void navigateToRegisterView() {
        try {
            AppViewManager.REGISTER_VIEW.switchView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void resetInputs() {
        usernameField.clear();
        passwordField.clear();
    }
}
