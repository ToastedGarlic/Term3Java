/**
 * Controller for the BookingDetails tab, which displays all BookingDetails entries in the travelexperts database
 * Created for workshop 6 of the term 3 threaded project
 * Author: Brannon Howlett
 * Date: 5/02/2024
 */

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
import java.text.SimpleDateFormat;
import java.util.Properties;

public class BookingDetailsController {
    // Columns are BookingDetailId, ItineraryNo, TripStart, TripEnd, Description, BasePrice,
    // AgencyCommission, BookingId, RegionId, ClassId, FeeId, and ProductSupplierId
    @FXML private TableView<BookingDetails> tvBookingDetails;
    @FXML private TableColumn<BookingDetails, Integer> colBookingDetailId;
    @FXML private TableColumn<BookingDetails, Integer> colItineraryNo;
    @FXML private TableColumn<BookingDetails, String> colTripStart;
    @FXML private TableColumn<BookingDetails, String> colTripEnd;
    @FXML private TableColumn<BookingDetails, String> colDescription;
    @FXML private TableColumn<BookingDetails, String> colDestination;
    @FXML private TableColumn<BookingDetails, Integer> colBasePrice;
    @FXML private TableColumn<BookingDetails, Integer> colAgencyCommission;
    @FXML private TableColumn<BookingDetails, Integer> colBookingId;
    @FXML private TableColumn<BookingDetails, String> colRegionId;
    @FXML private TableColumn<BookingDetails, String> colClassId;
    @FXML private TableColumn<BookingDetails, String> colFeeId;
    @FXML private TableColumn<BookingDetails, Integer> colProductSupplierId;

    // List containing all BookingDetails objects, each one corresponds to an entry in the database
    private ObservableList<BookingDetails> bookingDetailsList = FXCollections.observableArrayList();

    // Variables for database connection string. These are instantiated upon initial loading of bookingdetails
    private Properties prop = new Properties();
    String user = "";
    String password ="";
    String url = "";

    // Default constructor, not used.
    public BookingDetailsController() {}

    @FXML
    public void initialize() {
        colBookingDetailId.setCellValueFactory(new PropertyValueFactory<>("BookingDetailId"));
        colItineraryNo.setCellValueFactory(new PropertyValueFactory<>("ItineraryNo"));
        colTripStart.setCellValueFactory(new PropertyValueFactory<>("TripStart"));
        colTripEnd.setCellValueFactory(new PropertyValueFactory<>("TripEnd"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("Destination"));
        colBasePrice.setCellValueFactory(new PropertyValueFactory<>("BasePrice"));
        colAgencyCommission.setCellValueFactory(new PropertyValueFactory<>("AgencyCommission"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("BookingNo"));
        colRegionId.setCellValueFactory(new PropertyValueFactory<>("RegionName"));
        colClassId.setCellValueFactory(new PropertyValueFactory<>("ClassName"));
        colFeeId.setCellValueFactory(new PropertyValueFactory<>("FeeName"));
        colProductSupplierId.setCellValueFactory(new PropertyValueFactory<>("ProductSupplier"));

        // Fill the list of BookingDetails and display them
        tvBookingDetails.setItems(bookingDetailsList);
        getBookingDetails();

        //TODO: Find a way to display $ symbol in the Base Price and Agency Commission cells
        // This could be done by making the cell values into strings rather than integers, then when you need to send
        // a bookingdetails entry to the database, remove the $ and parse the number as an integer.
        // It would be ideal to just change the display text of the cells instead but I cant determine how
    }

    // Retrieve all BookingDetails entries from the database and put them into the ObservableList
    private void getBookingDetails() {
        // Load connection properties
        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

            // Retrieve all BookingDetails entries alongside display properties (BookingNo, RegionName, etc..)
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 // The fields drawn from other tables are only for display, when updating or adding bookingdetails
                 // entries in the database, the related IDs will be used for the submitted values
                 ResultSet rs = stmt.executeQuery("SELECT bd.BookingDetailId, bd.ItineraryNo, bd.TripStart, bd.TripEnd, " +
                                                                "bd.Description, bd.Destination, bd.BasePrice, bd.AgencyCommission, " +
                                                                "bd.BookingId, b.BookingNo, bd.RegionId, r.RegionName, bd.ClassId, " +
                                                                "c.ClassName, bd.FeeId, f.FeeName, bd.ProductSupplierId, s.SupName, p.ProdName " +
                                                       "FROM `bookingdetails` bd " +
                                                       "LEFT JOIN `regions` r ON r.RegionId = bd.RegionId "+
                                                       "LEFT JOIN `bookings` b ON b.BookingId = bd.BookingId "+
                                                       "LEFT JOIN `fees` f ON f.FeeId = bd.FeeId "+
                                                       "LEFT JOIN `classes` c ON c.ClassId = bd.ClassId "+
                                                       "LEFT JOIN `products_suppliers` pd ON pd.ProductSupplierId = bd.ProductSupplierId " +
                                                       "JOIN `suppliers` s ON s.SupplierId = pd.SupplierId "+
                                                       "JOIN `products` p ON p.ProductId = pd.ProductId " +
                                                       "ORDER BY bd.BookingDetailId")){
                bookingDetailsList.clear();
                while (rs.next()) {
                    // Formatted dates and product+supplier details for display
                    String formattedStart = rs.getString("TripStart").substring(0,10);
                    String formattedEnd = rs.getString("TripEnd").substring(0,10);
                    String productSupplier = rs.getString("ProdName") + ", " + rs.getString("SupName");

                    // Create the BookingDetails object and add it to the list
                    BookingDetails details = new BookingDetails(rs.getInt("BookingDetailId"),
                                                                rs.getInt("ItineraryNo"),
                                                                formattedStart,
                                                                formattedEnd,
                                                                rs.getString("Description"),
                                                                rs.getString("Destination"),
                                                                rs.getInt("BasePrice"),
                                                                rs.getInt("AgencyCommission"),
                                                                rs.getInt("BookingId"),
                                                                rs.getString("RegionId"),
                                                                rs.getString("ClassId"),
                                                                rs.getString("FeeId"),
                                                                rs.getInt("ProductSupplierId"),
                                                                rs.getString("BookingNo"),
                                                                rs.getString("RegionName"),
                                                                rs.getString("ClassName"),
                                                                rs.getString("FeeName"),
                                                                productSupplier);
                    bookingDetailsList.add(details);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Cannot load Booking Details information.");
        }
    }

    // When user presses the 'Add Booking Details' button, opens the add/modify form in Add mode
    @FXML
    private void onAdd(ActionEvent event) {
        showAddModifyDialog(null, "Add");
    }

    // When user presses the 'Modify Booking Details' button, opens the add/modify form in Edit mode
    @FXML
    private void onModify(ActionEvent event) {
        BookingDetails selectedDetails = tvBookingDetails.getSelectionModel().getSelectedItem();
        if (selectedDetails != null) {
           showAddModifyDialog(selectedDetails,"Edit");
        } else {
            showAlert("No Selection", "Please select Booking Details to modify.");
        }
    }

    // When user presses the 'Delete Booking Details' button, deletes the selected entry from the database after
    // confirmation. If an entry is not selected, error message will appear and operation will not be processed
    @FXML
    private void onDelete(ActionEvent event) {
        BookingDetails selectedBooking = tvBookingDetails.getSelectionModel().getSelectedItem();
        if (selectedBooking != null && showConfirmation("Delete Entry",
                "Are you sure you want to delete entry: "+ selectedBooking.getBookingDetailId()+"?")) {
            // Establish connection to database after confirmation
            try (Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement()) {
                String sql = "DELETE FROM `bookingdetails` WHERE BookingDetailId = " + selectedBooking.getBookingDetailId();
                int rows = stmt.executeUpdate(sql);

                // If deletion is successful, remove the selected booking from the ObservableList and refresh tableview
                if (rows > 0) {
                    bookingDetailsList.remove(selectedBooking);
                    getBookingDetails();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Deletion Error", "Failed to delete entry");
            }
        }
        else{ // User did not select an entry
            showAlert("Deletion Error", "You must select an entry to delete");
        }
    }

    // Displays the add/modify bookingdetails dialog, passes the selected details and mode over to the new form
    private void showAddModifyDialog(BookingDetails selectedDetails, String mode) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModifyBookingDetails.fxml")); // Make sure this path is correct
            Parent root = loader.load();

            AddModifyBookingDetailsController addModifyController = loader.getController();
            addModifyController.initialSetup(this, mode, selectedDetails);

            // Open the dialog window
            Stage dialogStage = new Stage();
            dialogStage.setTitle((selectedDetails == null) ? "Add Booking Details" : "Modify Booking Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tvBookingDetails.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            // Reload booking details when add/modify form closes
            getBookingDetails();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Cannot load the Add/Modify Booking Details window.");
        }
    }

    // Displays an error message
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
} // END OF CLASS