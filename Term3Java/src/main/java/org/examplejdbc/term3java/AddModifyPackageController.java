package org.examplejdbc.term3java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class AddModifyPackageController {

    @FXML private TextField txtPkgName;
    @FXML private DatePicker dpPkgStartDate;
    @FXML private DatePicker dpPkgEndDate;
    @FXML private TextField txtPkgDesc;
    @FXML private TextField txtPkgBasePrice;
    @FXML private TextField txtPkgAgencyCommission;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private Package existingPackage;
    private PackageController parentController;

    // Set the mode of the form based on a provided Package
    public void setMode(Package packageToModify) {
        this.existingPackage = packageToModify;
        if (existingPackage != null) {
            txtPkgName.setText(existingPackage.getPkgName());
            dpPkgStartDate.setValue(LocalDate.parse(existingPackage.getPkgStartDate().substring(0, 10)));
            dpPkgEndDate.setValue(LocalDate.parse(existingPackage.getPkgEndDate().substring(0, 10)));
            txtPkgDesc.setText(existingPackage.getPkgDesc());
            txtPkgBasePrice.setText(String.valueOf(existingPackage.getPkgBasePrice()));
            txtPkgAgencyCommission.setText(String.valueOf(existingPackage.getPkgAgencyCommission()));
        }
    }

    // Save button action
    @FXML
    private void onSave(ActionEvent event) {
        if (existingPackage != null) {
            // Update existing Package
            existingPackage.setPkgName(txtPkgName.getText());
            existingPackage.setPkgStartDate(dpPkgStartDate.getValue().toString());
            existingPackage.setPkgEndDate(dpPkgEndDate.getValue().toString());
            existingPackage.setPkgDesc(txtPkgDesc.getText());
            existingPackage.setPkgBasePrice(Double.parseDouble(txtPkgBasePrice.getText()));
            existingPackage.setPkgAgencyCommission(Double.parseDouble(txtPkgAgencyCommission.getText()));
            parentController.updatePackage(existingPackage);
        } else {
            // Create new Package
            Package newPackage = new Package(
                    txtPkgName.getText(),
                    dpPkgStartDate.getValue().toString(),
                    dpPkgEndDate.getValue().toString(),
                    txtPkgDesc.getText(),
                    Double.parseDouble(txtPkgBasePrice.getText()),
                    Double.parseDouble(txtPkgAgencyCommission.getText())
            );
            parentController.addPackage(newPackage);
        }
        closeStage(event);
    }

    // Close the dialog window
    public void closeStage(ActionEvent event) {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    // Method to set the parent controller
    public void setParentController(PackageController parent) {
        this.parentController = parent;
    }

    // Displays an alert dialog
    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content, ButtonType.OK);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
