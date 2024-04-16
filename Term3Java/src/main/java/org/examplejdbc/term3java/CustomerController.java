package org.examplejdbc.term3java;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddCustomer"
    private Button btnAddCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDeleteCustomer"
    private Button btnDeleteCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="btnModifyCustomer"
    private Button btnModifyCustomer; // Value injected by FXMLLoader

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

    @FXML // fx:id="colCustProv"
    private TableColumn<Customer, String> colCustProv; // Value injected by FXMLLoader

    @FXML // fx:id="colCustomerId"
    private TableColumn<Customer, Integer> colCustomerId; // Value injected by FXMLLoader

    @FXML // fx:id="tvCustomer"
    private TableView<Customer> tvCustomer; // Value injected by FXMLLoader

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        assert btnAddCustomer != null : "fx:id=\"btnAddCustomer\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert btnDeleteCustomer != null : "fx:id=\"btnDeleteCustomer\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert btnModifyCustomer != null : "fx:id=\"btnModifyCustomer\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colAgentId != null : "fx:id=\"colAgentId\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustAddress != null : "fx:id=\"colCustAddress\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustBusPhone != null : "fx:id=\"colCustBusPhone\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustCity != null : "fx:id=\"colCustCity\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustCountry != null : "fx:id=\"colCustCountry\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustEmail != null : "fx:id=\"colCustEmail\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustFirstName != null : "fx:id=\"colCustFirstName\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustHomePhone != null : "fx:id=\"colCustHomePhone\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustLastName != null : "fx:id=\"colCustLastName\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustPostal != null : "fx:id=\"colCustPostal\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustProv != null : "fx:id=\"colCustProv\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert colCustomerId != null : "fx:id=\"colCustomerId\" was not injected: check your FXML file 'customersTab.fxml'.";
        assert tvCustomer != null : "fx:id=\"tvCustomer\" was not injected: check your FXML file 'customersTab.fxml'.";


        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustFirstName.setCellValueFactory(new PropertyValueFactory<>("custFirstName"));
        colCustLastName.setCellValueFactory(new PropertyValueFactory<>("custLastName"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        colCustCity.setCellValueFactory(new PropertyValueFactory<>("custCity"));
        colCustProv.setCellValueFactory(new PropertyValueFactory<>("custProv"));
        colCustPostal.setCellValueFactory(new PropertyValueFactory<>("custPostal"));
        colCustCountry.setCellValueFactory(new PropertyValueFactory<>("custCountry"));
        colCustHomePhone.setCellValueFactory(new PropertyValueFactory<>("custHomePhone"));
        colCustBusPhone.setCellValueFactory(new PropertyValueFactory<>("custBusPhone"));
        colCustEmail.setCellValueFactory(new PropertyValueFactory<>("custEmail"));
        colAgentId.setCellValueFactory(new PropertyValueFactory<>("agentId"));

        getCustomerInfo();
        tvCustomer.setItems(customerList);

    }

    private void getCustomerInfo() {
        Properties prop = new Properties();
        String url = "";
        String user = "";
        String password = "";

        try (FileInputStream fis = new FileInputStream("d:\\connection.properties")) {
            prop.load(fis);
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

                while (rs.next()) {
                    Customer customer = new Customer(
                            rs.getInt("CustomerId"),
                            rs.getString("CustFirstName"),
                            rs.getString("CustLastName"),
                            rs.getString("CustAddress"),
                            rs.getString("CustCity"),
                            rs.getString("CustProv"),
                            rs.getString("CustPostal"),
                            rs.getString("CustCountry"),
                            rs.getString("CustHomePhone"),
                            rs.getString("CustBusPhone"),
                            rs.getString("CustEmail"),
                            rs.getInt("AgentId")
                    );
                    customerList.add(customer);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}