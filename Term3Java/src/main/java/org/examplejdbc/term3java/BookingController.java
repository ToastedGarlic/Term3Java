package org.examplejdbc.term3java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;


//BookingController.java
//author: Michael Chessall
public class BookingController {

    @FXML private TableView<Booking> tvBookings;
    @FXML private TableColumn<Booking, Integer> colBookingId;
    @FXML private TableColumn<Booking, String> colBookingDate;
    @FXML private TableColumn<Booking, String> colBookingNo;
    @FXML private TableColumn<Booking, Integer> colTravelerCount;
    @FXML private TableColumn<Booking, String> colCustomer;
    @FXML private TableColumn<Booking, String> colPackage;
    @FXML private TableColumn<Booking, String> colTripType;

    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();
    private Properties prop = new Properties(); // Declare it at the class level

    public BookingController() {
    }

    @FXML
    public void initialize() {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("BookingId"));
        colBookingDate.setCellValueFactory(new PropertyValueFactory<>("BookingDate"));
        colBookingNo.setCellValueFactory(new PropertyValueFactory<>("BookingNo"));
        colTravelerCount.setCellValueFactory(new PropertyValueFactory<>("TravelerCount"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        colPackage.setCellValueFactory(new PropertyValueFactory<>("Package"));
        colTripType.setCellValueFactory(new PropertyValueFactory<>("TripType"));
        tvBookings.setItems(bookingList);
        getBookingInfo();
    }

    private void getBookingInfo() {
        // Load connection properties

        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            // Connect to the database and fetch Bookings
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT b.BookingId, b.BookingDate, b.BookingNo, b.TravelerCount, c.CustFirstName, c.CustLastName, p.PkgName, t.TTName " +
                                                        "FROM `bookings` b LEFT JOIN customers c  ON c.CustomerId = b.CustomerId " +
                                                        "LEFT JOIN triptypes t ON t.TripTypeId = b.TripTypeId " +
                                                        "LEFT JOIN packages p ON p.PackageId = b.PackageId"))
                {
                    bookingList.clear();
                    while (rs.next()) {
                        String fullname = rs.getString("CustFirstName") + " " + rs.getString("CustLastName");
                        Booking travelBooking = new Booking(rs.getInt("BookingId"), rs.getString("BookingDate").substring(0,10), rs.getString("BookingNo"),
                                                            rs.getInt("TravelerCount"), fullname, rs.getString("PkgName"), rs.getString("TTName"));
                        bookingList.add(travelBooking);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Consider more user-friendly error handling
            showAlert("Error", "Cannot load Booking information.");
        }
    }

    // This method is linked to the 'Add Booking' button
    @FXML
    private void onAddBooking(ActionEvent event) {
        showAddModifyBookingDialog(null);
    }

    // This method is linked to the 'Modify Booking' button
    @FXML
    private void onModifyBooking(ActionEvent event) {
        Booking selectedBooking = tvBookings.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            showAddModifyBookingDialog(selectedBooking);
        } else {
            showAlert("No Selection", "Please select a Booking to modify.");
        }
    }

    // This method is linked to the 'Delete Booking' button
    @FXML
    private void onDeleteBooking(ActionEvent event) {
        Booking selectedBooking = tvBookings.getSelectionModel().getSelectedItem();
        if (selectedBooking != null && showConfirmation("Delete Booking", "Are you sure you want to delete this Booking?")) {
            // Delete logic here
            try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
                 Statement stmt = conn.createStatement()) {
                String sql = "DELETE FROM Bookings WHERE BookingId = " + selectedBooking.getBookingId();
                int rows = stmt.executeUpdate(sql);
                if (rows > 0) {
                    bookingList.remove(selectedBooking);
                }

            }catch (SQLIntegrityConstraintViolationException e){
                e.printStackTrace();
                showAlert("Error", "Cannot delete a booking referenced in a BookingDetails entry");
            }
            catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to delete the Booking from the database.");
            }

        }
    }

    // Shows the add/modify Booking dialog
    private void showAddModifyBookingDialog(Booking BookingToModify) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyBooking.fxml")); // Make sure this path is correct
            Parent root = loader.load();

            AddModifyBookingController controller = loader.getController();
            controller.setParentController(this);
            controller.setMode(BookingToModify);
            controller.getCustomerInfo();

            Stage dialogStage = new Stage();
            dialogStage.setTitle((BookingToModify == null) ? "Add Booking" : "Modify Booking");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tvBookings.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            getBookingInfo();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Cannot load the Add/Modify Booking window.");
        }
    }

    // Displays an alert dialog
    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content, ButtonType.OK);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    // Displays a confirmation dialog
    private boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    // Adds a new Booking to the database
    public void addBooking(Booking existingBooking, int custid, int packid, String tripid) {

        try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
             Statement stmt = conn.createStatement()) {
            String sql = "INSERT INTO bookings (BookingDate, BookingNo, TravelerCount, CustomerId, PackageId, TripTypeId) VALUES( ";
            sql += "'" + existingBooking.getBookingDate() + "',";
            sql += "'" + existingBooking.getBookingNo() + "',";
            sql += existingBooking.getTravelerCount() + ",";
            if(custid == -1){
                sql += "NULL,";
            }
            else {
                sql += custid + ",";
            }
            if(packid == -1){
                sql += "NULL,";
            }
            else {
                sql += packid + ",";
            }
            if(Objects.equals(tripid, "NULL")){
                sql += "NULL)";
            }
            else{
                sql += "'"+tripid+"')";
            }
            int rows = stmt.executeUpdate(sql);
            if (rows > 0) {
                getBookingInfo();
                showAlert("Success", "Booking successfully added to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete the Booking from the database.");
        }

    }

    // Updates an existing Booking in the database
    public void updateBooking(Booking existingBooking, int custid, int packid, String tripid) {

        try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
             Statement stmt = conn.createStatement()) {
            String sql = "UPDATE bookings SET ";
            sql += "BookingDate = '" + java.sql.Date.valueOf(existingBooking.getBookingDate()) + "', ";
            sql += "BookingNo = '" + existingBooking.getBookingNo() + "', ";
            if(custid == -1){
                sql += "CustomerId = NULL, ";
            }
            else {
                sql += "CustomerId = " + custid + ", ";
            }
            if(packid == -1){
                sql += "PackageId = NULL, ";
            }
            else {
                sql += "PackageId = " + packid + ",";
            }
            if(Objects.equals(tripid, "NULL")){
                sql += "TripTypeId = NULL ";
            }
            else{
                sql += "TripTypeId = '" + tripid + "' ";
            }
            sql += "WHERE BookingId = " + existingBooking.getBookingId();
            System.out.println(sql);
            int rows = stmt.executeUpdate(sql);
            if (rows > 0) {
                getBookingInfo();
                showAlert("Success", "Booking " + existingBooking.getBookingId() + " successfully updated in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete the Booking from the database.");
        }

    }
}