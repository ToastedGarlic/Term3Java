package org.examplejdbc.term3java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class AddModifyBookingController {

    @FXML private TextField txtBookingId;
    @FXML private TextField txtBookingNo;
    @FXML private TextField txtTravelerCount;
    @FXML private DatePicker dateBookingDate;
    @FXML private ComboBox<String> cmbCustomer;
    @FXML private ComboBox<String> cmbTravelType;
    @FXML private ComboBox<String> cmbPackage;
    int[] customers;
    String[] formattedcustomers;
    private Properties prop = new Properties(); // Declare it at the class level
    private Booking existingBooking;
    private BookingController parentController;

    // Set the mode of the form based on a provided Booking
    public void setMode(Booking BookingToModify) {
        if (BookingToModify != null) {
            this.existingBooking = BookingToModify;
            txtBookingId.setText(String.valueOf(existingBooking.getBookingId()));
            txtBookingNo.setText(existingBooking.getBookingNo());
            txtTravelerCount.setText(String.valueOf(existingBooking.getTravelerCount()));
            dateBookingDate.setValue(LocalDate.parse(existingBooking.getBookingDate().substring(0, 10)));
            cmbCustomer.setValue(String.valueOf(existingBooking.getCustomer()));
        }
    }

    public void setupPackages(ComboBox<String> cmbPackage){
        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            // Connect to the database and fetch customers
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {
                ResultSetMetaData rsmd = rs.getMetaData();
                customers = new int[rsmd.getColumnCount()];
                formattedcustomers = new String[rsmd.getColumnCount()];
                int i = 0;
                while (rs.next()) {
                    int custid = rs.getInt(1);
                    String fullname = rs.getString("CustFirstName") + " " + rs.getString("CustLastName");
                    customers[i++] = custid;
                    formattedcustomers[i++] = "(" + String.valueOf(custid) + ") " + fullname;
                }
                cmbCustomer.setItems(FXCollections.observableArrayList(formattedcustomers));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Consider more user-friendly error handling
            showAlert("Error", "Cannot load Booking information.");
        }

    }

    public void setupCustomers(ComboBox<String> cmbCustomer){
        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            // Connect to the database and fetch customers
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {
                ResultSetMetaData rsmd = rs.getMetaData();
                customers = new int[rsmd.getColumnCount()];
                formattedcustomers = new String[rsmd.getColumnCount()];
                int i = 0;
                while (rs.next()) {
                    int custid = rs.getInt(1);
                    String fullname = rs.getString("CustFirstName") + rs.getString("CustLastName");
                    customers[i++] = custid;
                    formattedcustomers[i++] = "(" + String.valueOf(custid) + ") " + fullname;
                }
                cmbCustomer.setItems(FXCollections.observableArrayList(formattedcustomers));
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

            parentController.updateBooking(existingBooking);
        } else {
            // Create new Booking
      //      Booking newBooking = new Booking(
      //      );

      //      parentController.addBooking(newBooking);
        }
        closeStage(event);
    }

    // Close the dialog window
    public void closeStage(ActionEvent event) {
        Stage stage = (Stage) txtBookingId.getScene().getWindow();
        stage.close();
    }

    // Method to set the parent controller
    public void setParentController(BookingController parent) {
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