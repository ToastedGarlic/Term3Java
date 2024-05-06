// Created by Mohsen Novin Pour as well as the inital template for this app,
// Application has a class, controller and an xml that is used in the main-view.xml that launches
// the secondary addmodify functionality, each team member created their tab for their own portion that is then used
// in the maincontroller and mainview that navigate the user from one functionality to another and back.
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
    private Tab tpBookings;
    @FXML
    private Tab tpMessenger;
    @FXML
    private Tab tpAgents;
    @FXML
    private Tab tpBookingDetails;

    @FXML
    public void initialize() {
        setupTabContent();
    }

    public void setupTabContent() {
        tpCustomer.setContent(loadTabContent("CustomersTab.fxml"));
        tpPackages.setContent(loadTabContent("PackagesTab.fxml"));
        tpAgents.setContent(loadTabContent("agents-view.fxml"));
        tpBookings.setContent(loadTabContent("bookingsTab.fxml"));
        tpMessenger.setContent(loadTabContent("messengerTab.fxml"));
        tpBookingDetails.setContent(loadTabContent("bookingdetailsTab.fxml"));

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