package org.examplejdbc.term3java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class MainController {

    @FXML
    private Tab tpCustomer;
    @FXML
    private Tab tpPackages;

    @FXML
    private Tab tpAgents;

    @FXML
    public void initialize() {
        setupTabContent();
    }

    public void setupTabContent() {
        tpCustomer.setContent(loadTabContent("CustomersTab.fxml"));
        tpPackages.setContent(loadTabContent("PackagesTab.fxml"));
        tpAgents.setContent(loadTabContent("agentstab.fxml"));
    }

    private AnchorPane loadTabContent(String fxmlFile) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void handleClose(ActionEvent actionEvent) {

    }
}