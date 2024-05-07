package org.examplejdbc.term3java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;
//AddModifyBookingController.java
//author: Michael Chessall
public class AddModifyBookingController {

    @FXML private TextField txtBookingId;
    @FXML private TextField txtBookingNo;
    @FXML private TextField txtTravelerCount;
    @FXML private DatePicker dateBookingDate;
    @FXML private ComboBox<String> cmbCustomer;
    @FXML private ComboBox<String> cmbTripType;
    @FXML private ComboBox<String> cmbPackage;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    int[] customers;
    String[] formattedcustomers;
    int[] packages;
    String[] formattedpackages;
    String[] triptypes;
    String[] formattedtriptypes;
    private Properties prop = new Properties(); // Declare it at the class level
    private Booking existingBooking;
    private BookingController parentController;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();


    public void getCustomerInfo() {
        Properties prop = new Properties();
        String url = "";
        String user = "";
        String password = "";

        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
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


    // Set the mode of the form based on a provided Booking
    public void setMode(Booking BookingToModify) {
        if (BookingToModify != null) {
            this.existingBooking = BookingToModify;
            txtBookingId.setText(String.valueOf(existingBooking.getBookingId()));
            txtBookingNo.setText(existingBooking.getBookingNo());
            txtTravelerCount.setText(String.valueOf(existingBooking.getTravelerCount()));
            dateBookingDate.setValue(LocalDate.parse(existingBooking.getBookingDate().substring(0, 10)));
            cmbCustomer.setValue(existingBooking.getCustomer());
            cmbTripType.setValue(existingBooking.getTripType());
            cmbPackage.setValue(existingBooking.getPackage());
        }
        setupCustomers();
        setupPackages();
        setupTripTypes();
        dateBookingDate.getEditor().setDisable(true);

        dateBookingDate.getEditor().setOpacity(1);
    }

    public void setupCustomers(){
        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");


            try (Connection connc = DriverManager.getConnection(url, user, password);
                 Statement stmt = connc.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM customers")) {
                rs.next();
                customers = new int[rs.getInt(1)];
                formattedcustomers = new String[customers.length];
            }
            // Connect to the database and fetch customers
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {
                    int i = 0;
                    while (rs.next()) {
                        int custid = rs.getInt(1);
                        String fullname = rs.getString("CustFirstName") + " " + rs.getString("CustLastName");
                        customers[i] = custid;
                        formattedcustomers[i++] = fullname;
                    }
                    cmbCustomer.setItems(FXCollections.observableArrayList(formattedcustomers));
               }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Consider more user-friendly error handling
            showAlert("Error", "Cannot load Booking information.");
        }

    }

    public void setupPackages(){
        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            try (Connection connc = DriverManager.getConnection(url, user, password);
                 Statement stmt = connc.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM packages")) {
                rs.next();
                packages = new int[rs.getInt(1)];
                formattedpackages = new String[packages.length];
            }

            // Connect to the database and fetch customers
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM packages")) {
                    int i = 0;
                    while (rs.next()) {
                        int packid = rs.getInt(1);
                        String fullname = rs.getString("PkgName");
                        packages[i] = packid;
                        formattedpackages[i++] = fullname;
                }
                cmbPackage.setItems(FXCollections.observableArrayList(formattedpackages));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Consider more user-friendly error handling
            showAlert("Error", "Cannot load Booking information.");
        }

    }

    public void setupTripTypes(){
        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            try (Connection connc = DriverManager.getConnection(url, user, password);
                 Statement stmt = connc.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM triptypes")) {
                rs.next();
                triptypes = new String[rs.getInt(1)];
                formattedtriptypes = new String[triptypes.length];
            }

            // Connect to the database and fetch customers
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM triptypes")) {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int i = 0;
                    while (rs.next()) {
                        String tripid = rs.getString(1);
                        String fullname = rs.getString("TTName");
                        triptypes[i] = tripid;
                        formattedtriptypes[i++] = fullname;
                }
                cmbTripType.setItems(FXCollections.observableArrayList(formattedtriptypes));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Consider more user-friendly error handling
            showAlert("Error", "Cannot load Booking information.");
        }

    }

    // Save button action
    @FXML
    private void onSave(ActionEvent event) {
        if (existingBooking != null) {
            // Update existing Booking
            existingBooking.setBookingNo(txtBookingNo.getText());
            existingBooking.setBookingDate(dateBookingDate.getValue().toString());
            existingBooking.setTravelerCount(Integer.parseInt(txtTravelerCount.getText()));
            int custind = cmbCustomer.getSelectionModel().getSelectedIndex();
            int custid = custind == -1 ? -1 : customers[custind];
            int packind = cmbPackage.getSelectionModel().getSelectedIndex();
            int packid = packind == -1 ? -1 : packages[packind];
            int tripind = cmbTripType.getSelectionModel().getSelectedIndex();
            String tripid = tripind == -1 ? "NULL" : triptypes[tripind];
            parentController.updateBooking(existingBooking, custid, packid, tripid);
        } else {
            // Create new Booking
            int custind = cmbCustomer.getSelectionModel().getSelectedIndex();
            int custid = custind == -1 ? -1 : customers[custind];
            int packind = cmbPackage.getSelectionModel().getSelectedIndex();
            int packid = packind == -1 ? -1 : packages[packind];
            int tripind = cmbTripType.getSelectionModel().getSelectedIndex();
            String tripid = tripind == -1 ? "NULL" : triptypes[tripind];
            Booking newBooking = new Booking(dateBookingDate.getValue().toString(),txtBookingNo.getText(),
                    Integer.parseInt(txtTravelerCount.getText()), "","","");

            parentController.addBooking(newBooking, custid, packid, tripid);
        }
        closeStage(event);
    }

    // Close the dialog window
    public void closeStage(ActionEvent event) {
        Stage stage = (Stage) txtBookingId.getScene().getWindow();
        stage.close();
    }

    // Method to set the parent controller
    public void setParentController(BookingController parent)
    {
        this.parentController = parent;
    }
    // Displays an alert dialog
    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content, ButtonType.OK);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
    @FXML
    private void onCustomerSelect(ActionEvent event) {
    }
    @FXML
    private void onPackageSelect(ActionEvent event) {
    }
    @FXML
    private void onTripTypeSelect(ActionEvent event) {
    }
}