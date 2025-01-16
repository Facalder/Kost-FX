package com.kost.views;

import com.airhacks.afterburner.injection.Injector;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.kost.iServices.IKostServices;
import com.kost.models.Kost;
import com.kost.services.KostServices;
import com.kost.session.AkunSessionManager;
import com.kost.utils.OthersUtils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Date;

public class DashboardPresenter {
    @FXML
    private Label subtitle1;

    @FXML
    private View dashboard;

    @FXML
    private TableView<Kost> tableKost;

    @FXML
    private TableColumn<Kost, Integer> noColumn;

    @FXML
    private TableColumn<Kost, Integer> idColumn;

    @FXML
    private TableColumn<Kost, String> alamatColumn;

    @FXML
    private TableColumn<Kost, String> fasilitasColumn;

    @FXML
    private TableColumn<Kost, Double> hargaColumn;

    @FXML
    private TableColumn<Kost, Double> promoPercentageColumn;

    @FXML
    private TableColumn<Kost, Date> promoExpiryColumn;

    @FXML
    private TableColumn<Kost, Integer> totalKamarColumn;

    @FXML
    private TableColumn<Kost, Integer> kamarTersediaColumn;

    @FXML
    private TableColumn<Kost, String> actionColumn;

    @FXML
    private TableColumn<Kost, ImageView> fotoColumn;

    @FXML
    private Button refreshData;

    private final ObservableList<Kost> kostObservableList = FXCollections.observableArrayList();

    private final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

    public void initialize() {
        if (AkunSessionManager.getInstance().getCurrentAkun().getRole().equals("admin")) {
            loadDataKost();
            tableKost.refresh();
        }else {
            kostObservableList.clear();
        }

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

    private void setupColumns() {
        noColumn.setCellValueFactory(cellData -> {
            int index = tableKost.getItems().indexOf(cellData.getValue()) + 1;
            return new SimpleIntegerProperty(index).asObject();
        });
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        alamatColumn.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        fasilitasColumn.setCellValueFactory(new PropertyValueFactory<>("fasilitas"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
        promoPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("promo_percentage"));
        promoExpiryColumn.setCellValueFactory(new PropertyValueFactory<>("promo_expiry"));
        totalKamarColumn.setCellValueFactory(new PropertyValueFactory<>("total_kamar"));
        kamarTersediaColumn.setCellValueFactory(new PropertyValueFactory<>("kamar_tersedia"));
        fotoColumn.setCellValueFactory(cellData -> {
            Kost kost = cellData.getValue();
            Image image = kost.getFotoToImage();

            if (image != null) {
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(600);
                imageView.setPreserveRatio(true);
                return new SimpleObjectProperty<>(imageView);
            }

            return null;
        });
    }

    private void loadDataKost() {
        IKostServices kostServices = new KostServices();
        kostObservableList.setAll(kostServices.showAllKosts());

        setupColumns();

        Callback<TableColumn<Kost, String>, TableCell<Kost, String>> cellFoctory =  (TableColumn<Kost, String> param) -> {
            final TableCell<Kost, String> cell = new TableCell<Kost, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    }else {
                        Button deleteButton = new Button("\uE872 ");
                        deleteButton.autosize();
                        deleteButton.getStyleClass().add("danger-button");
                        deleteButton.setOnAction(actionEvent -> {
                            int kost = getTableView().getItems().get(getIndex()).getKost_id();
                            kostServices.deleteKost(kost);

                            Kost kostToRemove = kostObservableList.stream()
                                    .filter(k -> k.getKost_id() == kost)
                                    .findFirst()
                                    .orElse(null);

                            if (kostToRemove != null) {
                                kostObservableList.remove(kostToRemove);
                            }

                            tableKost.refresh();
                        });

                        Button editButton = new Button();
                        editButton.autosize();
                        editButton.getStyleClass().add("secondary-button");
                        editButton.setOnAction(event -> {
                            Kost kost = getTableView().getItems().get(getIndex());
                            int kost_id = kost.getKost_id();

                            double getScreenWidth = screenBounds.getWidth();
                            double getScreenHeight = screenBounds.getHeight();

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kost/views/dashboardEditKostComponent.fxml"));
                            Stage dialogStage = new Stage();

                            dialogStage.setMinWidth(getScreenWidth * 0.6);
                            dialogStage.setMaxHeight(getScreenHeight);
                            dialogStage.setMinHeight(getScreenHeight * 0.8);

                            dialogStage.centerOnScreen();
                            dialogStage.setTitle("Edit Kost");

                            loader.setControllerFactory(controllerClass -> {
                                DashboardEditKostComponent controller = new DashboardEditKostComponent();
                                controller.setDialogStage(dialogStage);
                                return controller;
                            });

                            try {
                                javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());
                                String cssFile = getClass().getResource("/com/kost/style.css").toExternalForm();
                                scene.getStylesheets().add(cssFile);

                                DashboardEditKostComponent editKostModal = loader.getController();

                                editKostModal.setKost_id(kost_id);
                                editKostModal.setAllData(kost);

                                dialogStage.setScene(scene);
                                dialogStage.showAndWait();
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                        });

                        HBox manageBtn = new HBox(editButton, deleteButton);
                        manageBtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteButton, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editButton, new Insets(2, 2, 0, 3));

                        setGraphic(manageBtn);
                        setText(null);
                    }
                }
            };

            return cell;
        };

        actionColumn.setCellFactory(cellFoctory);
        tableKost.setItems(kostObservableList);
        tableKost.refresh();
    }



    @FXML
    private void refreshData() {
        try {
            kostObservableList.clear();

            IKostServices kostServices = new KostServices();
            kostObservableList.addAll(kostServices.showAllKosts());

            tableKost.refresh();
        }catch (Exception e) {
            System.out.println("Error saat refresh data: " + e.getMessage());
            e.printStackTrace();
        }
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

            kostObservableList.clear();
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
