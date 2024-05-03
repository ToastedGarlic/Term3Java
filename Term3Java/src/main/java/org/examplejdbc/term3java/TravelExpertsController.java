/**
 * Sample Skeleton for 'travelExperts-view.fxml' Controller Class
 */

package org.examplejdbc.term3java;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TravelExpertsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="colAgentId"
    private TableColumn<Customer, Integer> colAgentId; // Value injected by FXMLLoader


    @FXML // fx:id="colCustAddress"
    private TableColumn<Customer, String> colCustAddress; // Value injected by FXMLLoader

    @FXML // fx:id="colCustBusPhone"
    private TableColumn<Customer, String> colCustBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="colCustCity"
    private TableColumn<Customer, String> colCustCity; // Value injected by FXMLLoader

    @FXML // fx:id="colCustCountry"
    private TableColumn<Customer, String> colCustCountry; // Value injected by FXMLLoader

    @FXML // fx:id="colCustEmail"
    private TableColumn<Customer, String> colCustEmail; // Value injected by FXMLLoader

    @FXML // fx:id="colCustFirstName"
    private TableColumn<Customer, String> colCustFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="colCustHomePhone"
    private TableColumn<Customer, String> colCustHomePhone; // Value injected by FXMLLoader

    @FXML // fx:id="colCustLastName"
    private TableColumn<Customer, String> colCustLastName; // Value injected by FXMLLoader

    @FXML // fx:id="colCustPostal"
    private TableColumn<Customer, String> colCustPostal; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerId"
    private TableColumn<Customer, Integer> colCustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="colProv"
    private TableColumn<Customer, String> colCustProv; // Value injected by FXMLLoader

    @FXML // fx:id="tpCustomer"
    private Tab tpCustomer; // Value injected by FXMLLoader
    @FXML // fx:id="tvCustomer"
    private TableView<Customer> tvCustomer; // Value injected by FXMLLoader

    public ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert colAgentId != null : "fx:id=\"colAgentId\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustAddress != null : "fx:id=\"colCustAddress\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustBusPhone != null : "fx:id=\"colCustBusPhone\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustCity != null : "fx:id=\"colCustCity\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustCountry != null : "fx:id=\"colCustCountry\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustEmail != null : "fx:id=\"colCustEmail\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustFirstName != null : "fx:id=\"colCustFirstName\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustHomePhone != null : "fx:id=\"colCustHomePhone\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustLastName != null : "fx:id=\"colCustLastName\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustPostal != null : "fx:id=\"colCustPostal\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustomerId != null : "fx:id=\"colCustomerId\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert colCustProv != null : "fx:id=\"colProvince\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tpCustomer != null : "fx:id=\"tpCustomer\" was not injected: check your FXML file 'travelExperts-view.fxml'.";
        assert tvCustomer != null :  "fx:id=\"tvCustomer\" was not injected: check your FXML file 'travelExperts-view.fxml'.";


        getCustomerInfo();
        colCustomerId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        colCustFirstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("custFirstName"));
        colCustLastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("custLastName"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("custAddress"));
        colCustCity.setCellValueFactory(new PropertyValueFactory<Customer, String>("custCity"));
        colCustProv.setCellValueFactory(new PropertyValueFactory<Customer, String>("custProv"));
        colCustPostal.setCellValueFactory(new PropertyValueFactory<Customer, String>("custPostal"));
        colCustCountry.setCellValueFactory(new PropertyValueFactory<Customer, String>("custCountry"));
        colCustHomePhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("custHomePhone"));
        colCustBusPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("custBusPhone"));
        colCustEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("custEmail"));
        colAgentId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("agentId"));
        tvCustomer.setItems(customerList);
    }

    private void getCustomerInfo() {
        String url = "";
        String user = "";
        String password = "";

        try {
            FileInputStream fis = new FileInputStream("C:\\connection.properties");
            Properties prop = new Properties();
            prop.load(fis);
            url = (String) prop.get("url");
            user = (String) prop.get("user");
            password = (String) prop.get("password");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from customers");
            while (rs.next())
            {
                customerList.add(new Customer(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getInt(12)));
            }
            conn.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
