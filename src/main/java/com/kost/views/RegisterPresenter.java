package com.kost.views;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.kost.iServices.IAkunServices;
import com.kost.models.Akun;
import com.kost.services.AkunServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterPresenter {

    @FXML
    private View register;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> roleChoiceField;

    @FXML
    private ChoiceBox<String> questionChoiceField;

    @FXML
    private TextField answerField;

    private String roles[] = {"user", "admin"};

    private String questions[] = {
            "Siapa nama hewan peliharaan pertama Anda?",
            "Di kota manakah Anda lahir?",
            "Siapa nama guru favorit Anda?",
            "Apa makanan favorit Anda?",
            "Siapa nama sahabat masa kecil Anda?"
    };

    public void initialize() {
        ObservableList<String> rl = FXCollections.observableArrayList(roles);
        roleChoiceField.setItems(rl);
        roleChoiceField.setValue(roles[0]);

        ObservableList<String> qs = FXCollections.observableArrayList(questions);
        questionChoiceField.setItems(qs);
        questionChoiceField.setValue(questions[0]);

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
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleChoiceField.getValue();
        String question = questionChoiceField.getValue();
        String answer = answerField.getText();

        try {
            IAkunServices akunServices = new AkunServices();
            Akun akun = new Akun(username, password, role, question, answer);

            akunServices.register(akun);
            if (akunServices.getRegisteredStatus()) resetInputs();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void navigateToLoginView() {
        try {
            AppViewManager.LOGIN_VIEW.switchView();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void resetInputs() {
        usernameField.clear();
        passwordField.clear();
        roleChoiceField.getSelectionModel().clearSelection();
        questionChoiceField.getSelectionModel().clearSelection();
    }
}
