package com.kost;

import com.kost.db.DBConnection;
import com.kost.views.AppViewManager;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;

public class MainApplication extends Application {

    private final AppManager appManager = AppManager.initialize(this::postInit);
    private final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

    @Override
    public void init() {
        AppViewManager.registerViewsAndDrawer();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        double getScreenWidth = screenBounds.getWidth();
        double getScreenHeight = screenBounds.getHeight();

        primaryStage.setMinWidth(getScreenWidth * 0.7);

        primaryStage.setMaxHeight(getScreenHeight);
        primaryStage.setMinHeight(getScreenHeight);

        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setTitle("Kost Management");

        appManager.start(primaryStage);
    }

    private void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(MainApplication.class.getResourceAsStream("/icon.png")));
    }

    public static void main(String args[]) {
        try (var connection = DBConnection.connect()){
            System.out.println("Connected to database");
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

        launch(args);
    }
}
