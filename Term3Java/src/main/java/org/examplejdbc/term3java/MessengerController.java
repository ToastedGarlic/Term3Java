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

public class MessengerController {

    @FXML
    private TableView<Conversation> tvConversations;
    @FXML
    private TableColumn<Conversation, String> colCustomerM;
    @FXML
    private TableColumn<Conversation, String> colAgentM;
    @FXML
    private TableColumn<Booking, Integer> colMessegeCount;
    @FXML
    private TableColumn<Booking, String> colActionRequired;

    private ObservableList<Conversation> convoList = FXCollections.observableArrayList();
    private Properties prop = new Properties(); // Declare it at the class level

    public MessengerController() {
    }

    @FXML
    public void initialize() {
        colCustomerM.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colAgentM.setCellValueFactory(new PropertyValueFactory<>("AgentName"));
        colMessegeCount.setCellValueFactory(new PropertyValueFactory<>("MsgCount"));
        colActionRequired.setCellValueFactory(new PropertyValueFactory<>("ActionRequired"));
        tvConversations.setItems(convoList);
        getConvoInfo();
    }

    private void getConvoInfo() {
        // Load connection properties

        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            // Connect to the database and fetch Bookings
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT b.CustomerId, b.CustFirstName, b.CustLastName, c.AgentId, c.AgtFirstName, c.AgtLastName, COUNT(t.MessageId),  SUM(t.MsgFlagged)" +
                         "FROM `customers` b LEFT JOIN agents c  ON c.AgentId = b.AgentId " +
                         "LEFT JOIN `messages` t ON b.CustomerId = t.CustomerId " +
                         "GROUP BY b.CustomerId")) {
                convoList.clear();
                while (rs.next()) {
                    String custfullname = rs.getString("CustFirstName") + " " + rs.getString("CustLastName");
                    String agentfullname = rs.getString("AgtFirstName") + " " + rs.getString("AgtLastName");
                    boolean flagged = rs.getInt(8) > 0;
                    Conversation convo = new Conversation(rs.getInt(1), custfullname, rs.getInt(4), agentfullname, rs.getInt(7), flagged);
                    convoList.add(convo);

                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Consider more user-friendly error handling
            showAlert("Error", "Cannot load Conversation information.");
        }
        tvConversations.setItems(convoList);
    }

    // This method is linked to the 'Modify Booking' button
    @FXML
    private void onConvoSelect(ActionEvent event) {
        Conversation convo = tvConversations.getSelectionModel().getSelectedItem();
        if (convo != null) {
            showMessengerDialog(convo);
        } else {
            showAlert("No Selection", "Please select a conversation to view.");
        }
    }

    // Shows the add/modify Booking dialog
    private void showMessengerDialog(Conversation convo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SupportMessenger.fxml")); // Make sure this path is correct
            Parent root = loader.load();

            SupportMessengerController controller = loader.getController();
            controller.setParentController(this);
            controller.setMode(convo);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Support Messenger");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tvConversations.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            getConvoInfo();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Cannot load the Messenger window.");
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
}