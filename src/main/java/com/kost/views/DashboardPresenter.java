package com.kost.views;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;

public class DashboardPresenter {
    @FXML
    private View dashboard;

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
}
