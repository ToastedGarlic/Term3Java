/**
 * Sample Skeleton for 'travelExperts-view.fxml' Controller Class
 */

package org.examplejdbc.term3java;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;

public class TravelExpertsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="CustPostal"
    private TableColumn<?, ?> CustPostal; // Value injected by FXMLLoader

    @FXML // fx:id="tcCustAddress"
    private TableColumn<?, ?> tcCustAddress; // Value injected by FXMLLoader

    @FXML // fx:id="tcCustBusPhone"
    private TableColumn<?, ?> tcCustBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tcCustCity"
    private TableColumn<?, ?> tcCustCity; // Value injected by FXMLLoader

    @FXML // fx:id="tcCustCountry"
    private TableColumn<?, ?> tcCustCountry; // Value injected by FXMLLoader

    @FXML // fx:id="tcCustEmail"
    private TableColumn<?, ?> tcCustEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tcCustFirstName"
    private TableColumn<?, ?> tcCustFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="tcCustHomePhone"
    private TableColumn<?, ?> tcCustHomePhone; // Value injected by FXMLLoader

    @FXML // fx:id="tcCustLastName"
    private TableColumn<?, ?> tcCustLastName; // Value injected by FXMLLoader

    @FXML // fx:id="tcCustPostal"
    private TableColumn<?, ?> tcCustPostal; // Value injected by FXMLLoader

    @FXML // fx:id="tcCustomerId"
    private TableColumn<?, ?> tcCustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="tpCustomer"
    private Tab tpCustomer; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert CustPostal != null : "fx:id=\"CustPostal\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tcCustAddress != null : "fx:id=\"tcCustAddress\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tcCustBusPhone != null : "fx:id=\"tcCustBusPhone\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tcCustCity != null : "fx:id=\"tcCustCity\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tcCustCountry != null : "fx:id=\"tcCustCountry\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tcCustEmail != null : "fx:id=\"tcCustEmail\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tcCustFirstName != null : "fx:id=\"tcCustFirstName\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tcCustHomePhone != null : "fx:id=\"tcCustHomePhone\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tcCustLastName != null : "fx:id=\"tcCustLastName\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tcCustPostal != null : "fx:id=\"tcCustPostal\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tcCustomerId != null : "fx:id=\"tcCustomerId\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tpCustomer != null : "fx:id=\"tpCustomer\" was not injected: check your FXML file 'travelExperts-view.fxml'.";

    }

}
