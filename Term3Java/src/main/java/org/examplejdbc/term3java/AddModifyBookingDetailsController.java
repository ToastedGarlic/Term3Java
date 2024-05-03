/**
 * Controller for the Add/Modify Booking Details tab
 * Built for workshop 6 in the Term 3 Threaded Project
 * Author: Brannon Howlett
 * Date: 05/02/2024
 */

package org.examplejdbc.term3java;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class AddModifyBookingDetailsController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;
    @FXML
    private ComboBox<String> cmbBookingNo;
    @FXML
    private ComboBox<String> cmbClass;
    @FXML
    private ComboBox<String> cmbFee;
    @FXML
    private ComboBox<String> cmbRegion;
    @FXML
    private ComboBox<String> cmbSupplier;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private DatePicker dateStart;
    @FXML
    private Pane frmAddModify;
    @FXML
    private Label lblTitle;
    @FXML
    private TextField txtBasePrice;
    @FXML
    private TextField txtBookingDetailId;
    @FXML
    private TextField txtCommission;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtDestination;
    @FXML
    private TextField txtItinerary;


    private String mode = ""; // Determines function of the form
    private BookingDetails details; // BookingDetails to be added or modified
    private BookingDetailsController parentController; // Parent controller that launched the form

    // Variables necessary for database connection
    private Properties prop = new Properties(); // Declare it at the class level
    String url;
    String user;
    String password;

    // Lists to populate comboboxes, initialize with default value of "None"
    ObservableList<String> regions = FXCollections.observableArrayList("None");
    ObservableList<String> fees = FXCollections.observableArrayList("None");
    ObservableList<String> classes = FXCollections.observableArrayList("None");
    ObservableList<String> bookings = FXCollections.observableArrayList("None");
    ObservableList<String> productSuppliers = FXCollections.observableArrayList("None");

    @FXML
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert btnSubmit != null : "fx:id=\"btnSubmit\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert cmbBookingNo != null : "fx:id=\"cmbBookingNo\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert cmbClass != null : "fx:id=\"cmbClass\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert cmbFee != null : "fx:id=\"cmbFee\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert cmbRegion != null : "fx:id=\"cmbRegion\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert cmbSupplier != null : "fx:id=\"cmbSupplier\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert dateEnd != null : "fx:id=\"dateEnd\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert dateStart != null : "fx:id=\"dateStart\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert frmAddModify != null : "fx:id=\"frmAddModify\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert lblTitle != null : "fx:id=\"lblTitle\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert txtBasePrice != null : "fx:id=\"txtBasePrice\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert txtBookingDetailId != null : "fx:id=\"txtBookingDetailId\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert txtCommission != null : "fx:id=\"txtCommission\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert txtDescription != null : "fx:id=\"txtDescription\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert txtDestination != null : "fx:id=\"txtDestination\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";
        assert txtItinerary != null : "fx:id=\"txtItinerary\" was not injected: check your FXML file 'AddModifyBookingDetails.fxml'.";

        // Disable fields that shouldn't be set by user
        txtBookingDetailId.setDisable(true);

        // Establish connection to the database
        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

        } catch (IOException e) {
            e.printStackTrace();
            showError("Error", "Cannot load Booking Details information.");
        }
    }

    // Initial setup performed by the parent controller. Runs after Initialize(), assigns the parent controller
    // and gets values from the parent controller that are required for this controller to function
    public void initialSetup(BookingDetailsController parent, String mode, BookingDetails details){
        this.parentController = parent;
        this.mode = mode;

        // Populate comboboxes and their associated lists so that the default selection can be chosen
        // in edit mode
        populateComboBoxes();

        if(mode == "Add"){
            // Instantiate the details to be added, all attributes are null at this point
            this.details = new BookingDetails();
            lblTitle.setText("Add new Booking Details");

            // Set all combo boxes to initial value of "None"
            cmbBookingNo.getSelectionModel().selectFirst();
            cmbRegion.getSelectionModel().selectFirst();
            cmbFee.getSelectionModel().selectFirst();
            cmbClass.getSelectionModel().selectFirst();
            cmbSupplier.getSelectionModel().selectFirst();
        }
        else{ // Mode is edit
            this.details = details; // Set details to the selected details from previous form
            lblTitle.setText("Edit Booking Details");
        }
        // Fill all attribute fields with relevant data.
        setAttributeFields();
    }

    // Set initial display values of attribute fields based on the form's mode
    void setAttributeFields(){
        if(mode.equals("Add")){ // user is adding a new entry
            txtBookingDetailId.setPromptText("Generated upon creation");
        }
        else { // User is editing an existing entry
            txtBookingDetailId.setText("" + details.getBookingDetailId());
            dateStart.setValue(LocalDate.parse(details.getTripStart()));
            dateEnd.setValue(LocalDate.parse(details.getTripEnd()));
            txtItinerary.setText("" + details.getItineraryNo());
            txtDescription.setText(details.getDescription());
            txtDestination.setText(details.getDestination());
            txtBasePrice.setText("" + details.getBasePrice());
            txtCommission.setText("" + details.getAgencyCommission());

            // Set the combobox values to match those of the selected entry
            matchComboBoxValues();
        }

    }

    // Matches the initial selected value of the combo boxes to the corresponding value
    // of the entry being edited. Put into a separate method to avoid code bloat in setAttributeFields()
    void matchComboBoxValues(){
        int listIndex = 0;
        for(String s: regions){
            if(s.equals(details.getRegionName())){
                cmbRegion.getSelectionModel().select(listIndex);
            }
            listIndex++;
        }

        listIndex = 0;
        for(String s: classes){
            if(s.equals(details.getClassName())){
                cmbClass.getSelectionModel().select(listIndex);
            }
            listIndex++;
        }

        listIndex = 0;
        for(String s: fees){
            if(s.equals(details.getFeeName())){
                cmbFee.getSelectionModel().select(listIndex);
            }
            listIndex++;
        }

        listIndex = 0;
        for(String s: bookings){
            if(s.equals(details.getBookingNo())){
                cmbBookingNo.getSelectionModel().select(listIndex);
            }
            listIndex++;
        }
        listIndex = 0;
        for(String s: productSuppliers){
            // The second value in this array will be the supplier name
            if(s.equals(details.getProductSupplier())){
                cmbSupplier.getSelectionModel().select(listIndex);
            }
            listIndex++;
        }
    }

    // Fill the combo boxes with the names for each entry in their respective tables
    void populateComboBoxes(){
        // Establish connection to the database for filling the comboboxes
        try {
            // Establish the connection
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            // Fill region combobox
            ResultSet rs = stmt.executeQuery("SELECT RegionId, RegionName FROM `regions`");
            while (rs.next()) {
                regions.add(rs.getString("RegionName"));
            }
            cmbRegion.setItems(regions);

            // Fill fee combobox
            rs = stmt.executeQuery("SELECT FeeId, FeeName FROM `fees`");
            while(rs.next()){
               fees.add(rs.getString("FeeName"));
            }
            cmbFee.setItems(fees);

            // Fill class combobox
            rs = stmt.executeQuery("SELECT ClassId, ClassName FROM `classes`");
            while(rs.next()){
                classes.add(rs.getString("ClassName"));
            }
            cmbClass.setItems(classes);

            // Fill Booking combobox
            rs = stmt.executeQuery("SELECT BookingId, BookingNo FROM `bookings`");
            while(rs.next()){
                bookings.add(rs.getString("BookingNo"));
            }
            cmbBookingNo.setItems(bookings);

            // Fill product, supplier combobox
            rs = stmt.executeQuery("SELECT ps.ProductSupplierId, p.ProdName, s.SupName " +
                                        "FROM `products_suppliers` ps " +
                                        "LEFT JOIN `products` p ON p.ProductId = ps.ProductId " +
                                        "LEFT JOIN `suppliers` s ON s.SupplierId = ps.SupplierId " +
                                        "ORDER BY p.ProdName");
            while(rs.next()){
                String displayText = rs.getString("ProdName") + ", "
                                   + rs.getString("SupName");
                productSuppliers.add(displayText);
            }
            cmbSupplier.setItems(productSuppliers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // When user selects a booking number, update the BookingId of the details object
    //
    // This would cause problems if a duplicate BookingNo were to be added to the database
    // One solution would be to store the associated id for each select item in a list as the comboboxes are being
    // populated, but since there are no duplicate values in the related fields, this isn't needed for this project
    @FXML
    void onBookingNoSelect(ActionEvent event) {
        // Establish the connection
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String selected = cmbBookingNo.getSelectionModel().getSelectedItem();
            ResultSet rs = stmt.executeQuery("SELECT BookingId FROM `bookings` WHERE BookingNo ='"+selected+"'");
            if (rs.next()) details.setBookingId(rs.getInt("BookingId"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // When a class type is selected, update the ClassId of the details object
    @FXML
    void onClassSelect(ActionEvent event) {
        // Establish the connection
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String selected = cmbClass.getSelectionModel().getSelectedItem();
            ResultSet rs = stmt.executeQuery("SELECT ClassId FROM `classes` WHERE ClassName ='"+selected+"'");
            if(rs.next()) details.setClassId(rs.getString("ClassId"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // When a fee type is selected, update the FeeId of the details object
    @FXML
    void onFeeSelect(ActionEvent event) {
        // Establish the connection
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String selected = cmbFee.getSelectionModel().getSelectedItem();
            ResultSet rs = stmt.executeQuery("SELECT FeeId FROM `fees` WHERE FeeName = '"+selected+"'");
            if(rs.next()) details.setFeeId(rs.getString("FeeId"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // When a product and supplier is selected, update the ProductSupplierId of the details object
    @FXML
    void onProductSupplierSelect(ActionEvent event) {
        // Don't attempt this code if user selects "None", causes an error with the prodSupplier string array
        if(cmbSupplier.getSelectionModel().getSelectedIndex() != 0) {
            // Establish the connection
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                String selected = cmbSupplier.getSelectionModel().getSelectedItem();
                // Index 0 is product name, 1 is supplier name
                String[] prodSupplier = selected.split(", ");
                // Get the ProductSupplierId that has the same product and supplier names as the selected value
                ResultSet rs = stmt.executeQuery("SELECT ps.ProductSupplierId FROM `products_suppliers` ps " +
                        "LEFT JOIN `suppliers` s ON s.SupplierId = ps.SupplierId " +
                        "LEFT JOIN `products` p ON p.ProductId = ps.ProductId " +
                        "WHERE p.ProdName = '" + prodSupplier[0] + "' " +
                        "AND s.SupName = '" + prodSupplier[1] + "'");
                if (rs.next()) details.setProductSupplierId(rs.getInt("ProductSupplierId"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // When a region is selected, update the RegionId of the details object
    @FXML
    void onRegionSelect(ActionEvent event) {
        // Establish the connection
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            String selected = cmbRegion.getSelectionModel().getSelectedItem();
            ResultSet rs = stmt.executeQuery("SELECT RegionId FROM `regions` WHERE RegionName ='"+selected+"'");
            if(rs.next())details.setRegionId(rs.getString("RegionId"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Determine if provided string can be parsed into a double
    // Returns true if possible, false if not
    public boolean isTextDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    // Determine if all inputted data is valid for the database
    // Returns true if all data is valid, displays and error message and returns false otherwise
    public boolean validateData(){
        boolean isValid = true;
        String errorMessage = "";

        // Checking for valid combo box selections
        if(cmbBookingNo.getSelectionModel().getSelectedIndex() == 0){
            errorMessage += "You must select a booking number \n";
            isValid = false;
        }
        if(cmbRegion.getSelectionModel().getSelectedIndex() == 0){
            errorMessage += "You must select a region \n";
            isValid = false;
        }
        if(cmbClass.getSelectionModel().getSelectedIndex() == 0){
            errorMessage += "You must select a class \n";
            isValid = false;
        }
        if(cmbFee.getSelectionModel().getSelectedIndex() == 0){
            errorMessage += "You must select a fee type \n";
            isValid = false;
        }
        if(cmbSupplier.getSelectionModel().getSelectedIndex() == 0){
            errorMessage += "You must select a product and supplier \n";
            isValid = false;
        }

        // Testing number fields
        if(!isTextDouble(txtCommission.getText())){
           errorMessage += "Commission must be a number \n";
           isValid = false;
        }
        if(!isTextDouble(txtBasePrice.getText())){
            errorMessage += "Base Price must be a number \n";
            isValid = false;
        }
        if(!isTextDouble(txtItinerary.getText())){
            errorMessage += "Itinerary No must be a number \n";
            isValid = false;
        }

        // Validating dates
        // Dates can't be compared if one doesn't exist
        if(dateStart.getValue() != null && dateEnd.getValue() != null) {
            if (dateStart.getValue().isAfter(dateEnd.getValue())) {
                errorMessage += "Start Date must be before End Date \n";
                isValid = false;
            }
        }
        if(dateStart.getValue() == null){
            errorMessage += "You must select a start date \n";
            isValid=false;
        }
        if(dateEnd.getValue() == null){
            errorMessage += "You must select an end date \n";
            isValid=false;
        }

        // Display the error message if one exists, then return the validity state of the data
        if(!errorMessage.equals("")){
            showError(mode+" Error",errorMessage);
        }
        return isValid;
    }

    // When submit button is pressed, attempt to submit the addition or modification to the database
    // If any of the data is invalid, neither operation will work and an error message will display
    @FXML
    void onSubmitClick(ActionEvent event) {
        if(validateData()){ // Data is valid
           if(mode.equals("Add")){
               addEntry();
           }
           else{
               updateEntry();
           }
           // Close the window if operation was successful
           Stage stage = (Stage) btnSubmit.getScene().getWindow();
           stage.close();
        }
    }

    // Add a new BookingDetails entry to the database using provided data
    void addEntry(){
        try {
            // Open connection for update action
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO `bookingdetails`(`BookingDetailId`, `ItineraryNo`, `TripStart`, `TripEnd`, " +
                                      "`Description`, `Destination`, `BasePrice`, `AgencyCommission`, `BookingId`, " +
                                      "`RegionId`, `ClassId`, `FeeId`, `ProductSupplierId`) " +
                         "VALUES ('0', " +
                            "'"+Integer.parseInt(txtItinerary.getText())+"'," +
                            "'"+dateStart.getValue().toString()+"'," +
                            "'"+dateEnd.getValue().toString()+"'," +
                            "'"+txtDescription.getText()+"'," +
                            "'"+txtDestination.getText()+"'," +
                            "'"+Double.parseDouble(txtBasePrice.getText())+"'," +
                            "'"+Double.parseDouble(txtCommission.getText())+"'," +
                            "'"+details.getBookingId()+"'," +
                            "'"+details.getRegionId()+"'," +
                            "'"+details.getClassId()+"'," +
                            "'"+details.getFeeId()+"'," +
                            "'"+details.getProductSupplierId()+"')";

            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected > 0){
                showAlert("Insert Complete!","New entry successfully inserted");
            }
            else{
                showError("Update Failed!","New entry failed to insert");            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update a selected entry in the database using provided data.
    void updateEntry(){
        try {
            // Open connection for update action
            Connection conn = DriverManager.getConnection(url,user,password);
            Statement stmt = conn.createStatement();
            String sql = "UPDATE `bookingdetails` SET " +
                    "`ItineraryNo`=?, `TripStart`=?, `TripEnd`=?, `Description`=?," +
                    " `Destination`=?, `BasePrice`=?, `AgencyCommission`=?, `BookingId`=?," +
                    "`RegionId`=?,`ClassId`=?,`FeeId`=?,`ProductSupplierId`=? " +
                    "WHERE BookingDetailId=?";

            // Prepare the statement and set parameters for the update
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setDouble(1,Double.parseDouble(txtItinerary.getText()));
            prep.setString(2,dateStart.getValue().toString());
            prep.setString(3,dateEnd.getValue().toString());
            prep.setString(4,txtDescription.getText());
            prep.setString(5,txtDestination.getText());
            prep.setDouble(6,Double.parseDouble(txtBasePrice.getText()));
            prep.setDouble(7,Double.parseDouble(txtCommission.getText()));
            prep.setInt(8,details.getBookingId());
            prep.setString(9,details.getRegionId());
            prep.setString(10,details.getClassId());
            prep.setString(11,details.getFeeId());
            prep.setInt(12,details.getProductSupplierId());
            prep.setInt(13,Integer.parseInt(txtBookingDetailId.getText()));

            // Inform user of the result of update, then close connection
            int rowsAffected = prep.executeUpdate();
            if (rowsAffected > 0){
                showAlert("Update Complete!","Changes successfully saved");
            }
            else{
                showError("Update Failed!","Changes failed to save");            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Close the window and return to the main form if the user presses the cancel button
    @FXML
    void onCancelClick(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    // Display an message with provided header and content
    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.NONE, content, ButtonType.OK);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
    // Display an error message with provided header and content
    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content, ButtonType.OK);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
} // END OF CLASS