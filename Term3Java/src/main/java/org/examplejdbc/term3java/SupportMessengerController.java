package org.examplejdbc.term3java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Properties;

public class SupportMessengerController {

    @FXML private TextField txtCustM;
    @FXML private TextField txtAgentM;
    @FXML private TextArea tareaMsg;
    @FXML private RadioButton radioActionRequired;
    @FXML private Button btnSend;
    @FXML private Button btnClose;
    @FXML private TextFlow tflowMessages;
    private Properties prop = new Properties(); // Declare it at the class level
    private Conversation convo;
    private MessengerController parentController;


    // Set the mode of the form based on a provided Booking
    public void setMode(Conversation convo) {
        this.convo = convo;
        txtAgentM.setText(convo.getAgentName());
        txtCustM.setText(convo.getCustomerName());
        radioActionRequired.setSelected(convo.getMsgFlagged());
        getMessageInfo();
    }

    private void getMessageInfo() {
        // Load connection properties
        tflowMessages.getChildren().clear();
        StringBuilder msg = new StringBuilder();
        try (InputStream fis = getClass().getResourceAsStream("/config/connection.properties")) {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            // Connect to the database and fetch Bookings
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT c.MessageId, c.MsgDate, c.MsgContent " +
                         "FROM `customers` b INNER JOIN messages c ON c.CustomerId = b.CustomerId " +
                         "ORDER BY c.MessageId DESC")) {
                while (rs.next()) {
                    if(rs.getString(2) != null && rs.getString(3) != null) {

                        msg.append(rs.getString("MsgDate")).append(" ").append(rs.getString("MsgContent")).append("\n\n");
                    }
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Consider more user-friendly error handling
            showAlert("Error", "Cannot load Conversation information.");
        }
        Text txt = new Text(msg.toString());
        tflowMessages.getChildren().add(txt);
    }

    // Close the dialog window
    public void closeStage(ActionEvent event) {
        Stage stage = (Stage) txtCustM.getScene().getWindow();
        stage.close();
    }

    // Method to set the parent controller
    public void setParentController(MessengerController parent)
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
    private void onSend(ActionEvent event) {
        String msg = convo.getAgentName() + ": " + tareaMsg.textProperty().getValue();
        addMessage(msg);
        tareaMsg.clear();
    }
    public void addMessage(String msg) {

        try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
             Statement stmt = conn.createStatement()) {
            String sql = "INSERT INTO messages (MsgContent, AgentId, CustomerId) VALUES( ";
            sql += "'" + msg + "', ";
            sql += convo.getAgentId() +", ";
            sql += convo.getCustomerId() + ")";
            int rows = stmt.executeUpdate(sql);
            if (rows > 0) {
                getMessageInfo();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to send message.");
        }

    }


}