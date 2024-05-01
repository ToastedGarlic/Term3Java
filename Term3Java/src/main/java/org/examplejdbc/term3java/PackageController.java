package org.example.jdbc.term3java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class PackageController {

    @FXML private TableView<Package> tvPackages;
    @FXML private TableColumn<Package, Integer> colPackageId;
    @FXML private TableColumn<Package, String> colPkgName;
    @FXML private TableColumn<Package, String> colPkgStartDate;
    @FXML private TableColumn<Package, String> colPkgEndDate;
    @FXML private TableColumn<Package, String> colPkgDesc;
    @FXML private TableColumn<Package, Double> colPkgBasePrice;
    @FXML private TableColumn<Package, Double> colPkgAgencyCommission;

    private ObservableList<Package> packageList = FXCollections.observableArrayList();
    private Properties prop = new Properties(); // Declare it at the class level

    public PackageController() {
    }

    @FXML
    public void initialize() {
        colPackageId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        colPkgName.setCellValueFactory(new PropertyValueFactory<>("pkgName"));
        colPkgStartDate.setCellValueFactory(new PropertyValueFactory<>("pkgStartDate"));
        colPkgEndDate.setCellValueFactory(new PropertyValueFactory<>("pkgEndDate"));
        colPkgDesc.setCellValueFactory(new PropertyValueFactory<>("pkgDesc"));
        colPkgBasePrice.setCellValueFactory(new PropertyValueFactory<>("pkgBasePrice"));
        colPkgAgencyCommission.setCellValueFactory(new PropertyValueFactory<>("pkgAgencyCommission"));

        tvPackages.setItems(packageList);
        getPackageInfo();
    }

    private void getPackageInfo() {
        // Load connection properties
        try (FileInputStream fis = new FileInputStream("c:\\connection.properties")) {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            // Connect to the database and fetch packages
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM packages")) {
                packageList.clear();
                while (rs.next()) {
                    Package travelPackage = new Package(
                            rs.getInt("PackageId"),
                            rs.getString("PkgName"),
                            rs.getString("PkgStartDate"),
                            rs.getString("PkgEndDate"),
                            rs.getString("PkgDesc"),
                            rs.getDouble("PkgBasePrice"),
                            rs.getDouble("PkgAgencyCommission")
                    );
                    packageList.add(travelPackage);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Consider more user-friendly error handling
            showAlert("Error", "Cannot load package information.");
        }
    }

    // This method is linked to the 'Add Package' button
    @FXML
    private void onAddPackage(ActionEvent event) {
        showAddModifyPackageDialog(null);
    }

    // This method is linked to the 'Modify Package' button
    @FXML
    private void onModifyPackage(ActionEvent event) {
        Package selectedPackage = tvPackages.getSelectionModel().getSelectedItem();
        if (selectedPackage != null) {
            showAddModifyPackageDialog(selectedPackage);
        } else {
            showAlert("No Selection", "Please select a package to modify.");
        }
    }

    // This method is linked to the 'Delete Package' button
    @FXML
    private void onDeletePackage(ActionEvent event) {
        Package selectedPackage = tvPackages.getSelectionModel().getSelectedItem();
        if (selectedPackage != null && showConfirmation("Delete Package", "Are you sure you want to delete this package?")) {
            // Delete logic here
            try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
                 Statement stmt = conn.createStatement()) {
                String sql = "DELETE FROM packages WHERE PackageId = " + selectedPackage.getPackageId();
                int rows = stmt.executeUpdate(sql);
                if (rows > 0) {
                    packageList.remove(selectedPackage);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to delete the package from the database.");
            }
        }
    }

    // Shows the add/modify package dialog
    private void showAddModifyPackageDialog(Package packageToModify) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyPackage.fxml")); // Make sure this path is correct
            Parent root = loader.load();

            AddModifyPackageController controller = loader.getController();
            controller.setParentController(this);
            controller.setMode(packageToModify);

            Stage dialogStage = new Stage();
            dialogStage.setTitle((packageToModify == null) ? "Add Package" : "Modify Package");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tvPackages.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            getPackageInfo();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Cannot load the Add/Modify Package window.");
        }
    }

    // Displays an alert dialog
    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content, ButtonType.OK);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    // Displays a confirmation dialog
    private boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    // Adds a new package to the database
    public void addPackage(Package newPackage) {
    }

    // Updates an existing package in the database
    public void updatePackage(Package existingPackage) {
    }
}