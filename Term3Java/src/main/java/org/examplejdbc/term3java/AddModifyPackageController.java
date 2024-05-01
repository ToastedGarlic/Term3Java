package org.example.jdbc.term3java;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class AddModifyPackageController {

    @FXML private TextField txtPkgName;
    @FXML private TextField txtPkgStartDate;
    @FXML private TextField txtPkgEndDate;
    @FXML private TextField txtPkgDesc;
    @FXML private TextField txtPkgBasePrice;
    @FXML private TextField txtPkgAgencyCommission;

    private Package existingPackage;
    private PackageController parentController;

    // Set the mode of the form based on a provided package
    public void setMode(Package packageToModify) {
        if (packageToModify != null) {
            this.existingPackage = packageToModify;
            txtPkgName.setText(existingPackage.getPkgName());
            txtPkgStartDate.setText(existingPackage.getPkgStartDate());
            txtPkgEndDate.setText(existingPackage.getPkgEndDate());
            txtPkgDesc.setText(existingPackage.getPkgDesc());
            txtPkgBasePrice.setText(String.valueOf(existingPackage.getPkgBasePrice()));
            txtPkgAgencyCommission.setText(String.valueOf(existingPackage.getPkgAgencyCommission()));
        }
    }

    // Save button action
    @FXML
    private void onSave(ActionEvent event) {
        if (existingPackage != null) {
            // Update existing package
            existingPackage.setPkgName(txtPkgName.getText());
            existingPackage.setPkgStartDate(txtPkgStartDate.getText());
            existingPackage.setPkgEndDate(txtPkgEndDate.getText());
            existingPackage.setPkgDesc(txtPkgDesc.getText());
            existingPackage.setPkgBasePrice(Double.parseDouble(txtPkgBasePrice.getText()));
            existingPackage.setPkgAgencyCommission(Double.parseDouble(txtPkgAgencyCommission.getText()));

            parentController.updatePackage(existingPackage);
        } else {
            // Create new package
            Package newPackage = new Package(
                    txtPkgName.getText(),
                    txtPkgStartDate.getText(),
                    txtPkgEndDate.getText(),
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
        Stage stage = (Stage) txtPkgName.getScene().getWindow();
        stage.close();
    }

    // Method to set the parent controller
    public void setParentController(PackageController parent) {
        this.parentController = parent;
    }
}