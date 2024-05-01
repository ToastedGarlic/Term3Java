package org.example.jdbc.term3java;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Customer, Integer> colAgentId;
    @FXML
    private TableColumn<Customer, String> colCustAddress;
    @FXML
    private TableColumn<Customer, String> colCustBusPhone;
    @FXML
    private TableColumn<Customer, String> colCustCity;
    @FXML
    private TableColumn<Customer, String> colCustCountry;
    @FXML
    private TableColumn<Customer, String> colCustEmail;
    @FXML
    private TableColumn<Customer, String> colCustFirstName;
    @FXML
    private TableColumn<Customer, String> colCustHomePhone;
    @FXML
    private TableColumn<Customer, String> colCustLastName;
    @FXML
    private TableColumn<Customer, String> colCustPostal;
    @FXML
    private TableColumn<Customer, Integer> colCustomerId;
    @FXML
    private TableColumn<Customer, String> colCustProv;

    @FXML
    private TableView<Customer> tvCustomer;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
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

        tvCustomer.setItems(customerList);

        getCustomerInfo();
    }

    private void getCustomerInfo() {
        Properties prop = new Properties();
        String url = "";
        String user = "";
        String password = "";

        try (FileInputStream fis = new FileInputStream("c:\\connection.properties")) {
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