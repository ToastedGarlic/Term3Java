package org.examplejdbc.term3java;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    private String windowStatus = null;
    private String mode;

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

        /*tvCustomer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observableValue, Customer customer, Customer t1) {
                if (tvCustomer.getSelectionModel().isSelected((tvCustomer.getSelectionModel().getSelectedIndex())))
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mode = "edit";
                            openDialog(t1, mode);
                        }
                    });
                }
            }
        });*/

        btnAddCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                windowStatus = "Add";
                openDialog(null, windowStatus);
            }
        });

        btnModifyCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(tvCustomer.getSelectionModel().getSelectedItem().getCustomerId());
                mode = "edit";
                openDialog(tvCustomer.getSelectionModel().getSelectedItem(), mode);
            }
        });

        btnDeleteCustomer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deleteCustomer();
            }
        });
    }
    // Method for getting customer data from mysql server and putting into list
    private void getCustomerInfo() {
        customerList.clear();
        String url = "";
        String user = "";
        String password = "";

        try {
            InputStream fis = getClass().getResourceAsStream("/config/connection.properties");
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

    private void openDialog(Customer t1, String mode) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddModifyCustomer.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AddModifyCustomerController addModifyCustomerController = fxmlLoader.getController();
        addModifyCustomerController.passMode(mode);
        if (mode.equals("edit"))
        {
            addModifyCustomerController.loadCustomer(t1);
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        getCustomerInfo();
    }

    // method for delete operation of customer
    private void deleteCustomer() {
        String url = "";
        String user = "";
        String password = "";

        Customer selectedCustomer = tvCustomer.getSelectionModel().getSelectedItem();

        try {
            InputStream fis = getClass().getResourceAsStream("/config/connection.properties");
            Properties prop = new Properties();
            prop.load(fis);
            url = (String) prop.get("url");
            user = (String) prop.get("user");
            password = (String) prop.get("password");
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "delete from customers where CustomerId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, selectedCustomer.getCustomerId());

            stmt.executeUpdate();
            conn.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        getCustomerInfo();
        tvCustomer.setItems(customerList);
    }

}