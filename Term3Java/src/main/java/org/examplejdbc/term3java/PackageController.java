//Created by Mohsen Novin Pour, Controller for handling Crud functionality
// for the package portion of the desktop App

package org.examplejdbc.term3java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class PackageController {

    @FXML
    private TableView<Package> tvPackages;
    @FXML
    private TableColumn<Package, Integer> colPackageId;
    @FXML
    private TableColumn<Package, String> colPkgName, colPkgStartDate, colPkgEndDate, colPkgDesc;
    @FXML
    private TableColumn<Package, Double> colPkgBasePrice, colPkgAgencyCommission;

    private ObservableList<Package> packageList = FXCollections.observableArrayList();
    private Properties prop = new Properties();

    public PackageController() {
        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Cannot load database properties.");
        }
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
        loadPackages();
    }
    private boolean validatePackage(Package packageToValidate) {
        LocalDate startDate = LocalDate.parse(packageToValidate.getPkgStartDate());
        LocalDate endDate = LocalDate.parse(packageToValidate.getPkgEndDate());


        if (startDate.isAfter(endDate)) {
            showAlert("Validation Error", "Start date cannot be after end date.");
            return false;
        }

        if (packageToValidate.getPkgBasePrice() < 0 || packageToValidate.getPkgAgencyCommission() < 0) {
            showAlert("Validation Error", "Price and commission cannot be negative.");
            return false;
        }

        return true;
    }

    private void loadPackages() {
        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM packages")) {

            packageList.clear();
            while (rs.next()) {
                Package pack = new Package(
                        rs.getInt("PackageId"),
                        rs.getString("PkgName"),
                        rs.getString("PkgStartDate").substring(0, 10),
                        rs.getString("PkgEndDate").substring(0, 10),
                        rs.getString("PkgDesc"),
                        rs.getDouble("PkgBasePrice"),
                        rs.getDouble("PkgAgencyCommission")
                );
                packageList.add(pack);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load package data.");
        }
    }


    @FXML
    private void onAddPackage(ActionEvent event) {
        showAddModifyPackageDialog(null);
    }

    @FXML
    private void onModifyPackage(ActionEvent event) {
        Package selectedPackage = tvPackages.getSelectionModel().getSelectedItem();
        if (selectedPackage != null) {
            showAddModifyPackageDialog(selectedPackage);
        } else {
            showAlert("No Selection", "Please select a Package to modify.");
        }
    }

    @FXML
    private void onDeletePackage(ActionEvent event) {
        Package selectedPackage = tvPackages.getSelectionModel().getSelectedItem();
        if (selectedPackage != null && showConfirmation("Delete Package", "Are you sure you want to delete this package?")) {
            try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
                 Statement stmt = conn.createStatement()) {
                String sql = "DELETE FROM packages WHERE PackageId = " + selectedPackage.getPackageId();
                int rows = stmt.executeUpdate(sql);
                if (rows > 0) {
                    packageList.remove(selectedPackage);
                    showAlert("Success", "Package deleted successfully.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to delete the package.");
            }
        }
    }

    private void showAddModifyPackageDialog(Package packageToModify) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyPackage.fxml"));
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
            loadPackages(); // Refresh list after modification
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open package dialog.");
        }
    }


    public void addPackage(Package newPackage) {
        if (!validatePackage(newPackage)) return;

        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO packages (PkgName, PkgStartDate, PkgEndDate, PkgDesc, PkgBasePrice, PkgAgencyCommission) VALUES (?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, newPackage.getPkgName());
            pstmt.setDate(2, Date.valueOf(newPackage.getPkgStartDate()));
            pstmt.setDate(3, Date.valueOf(newPackage.getPkgEndDate()));
            pstmt.setString(4, newPackage.getPkgDesc());
            pstmt.setDouble(5, newPackage.getPkgBasePrice());
            pstmt.setDouble(6, newPackage.getPkgAgencyCommission());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                showAlert("Success", "Package added successfully.");
                loadPackages(); // Refresh list after adding
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add the package.");
        }
    }

    public void updatePackage(Package packageToUpdate) {
        if (!validatePackage(packageToUpdate)) return;

        String url = prop.getProperty("url");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE packages SET PkgName=?, PkgStartDate=?, PkgEndDate=?, PkgDesc=?, PkgBasePrice=?, PkgAgencyCommission=? WHERE PackageId=?")) {

            pstmt.setString(1, packageToUpdate.getPkgName());
            pstmt.setDate(2, Date.valueOf(packageToUpdate.getPkgStartDate()));
            pstmt.setDate(3, Date.valueOf(packageToUpdate.getPkgEndDate()));
            pstmt.setString(4, packageToUpdate.getPkgDesc());
            pstmt.setDouble(5, packageToUpdate.getPkgBasePrice());
            pstmt.setDouble(6, packageToUpdate.getPkgAgencyCommission());
            pstmt.setInt(7, packageToUpdate.getPackageId());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                showAlert("Success", "Package updated successfully.");
                loadPackages(); // Refresh list after updating
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update the package.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        return alert.showAndWait().orElse(noButton) == yesButton;
    }
}