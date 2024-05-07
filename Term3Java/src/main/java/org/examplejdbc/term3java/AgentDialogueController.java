/**
 * Sample Skeleton for 'agentdialogue-view.fxml' Controller Class
 */

package org.examplejdbc.term3java;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AgentDialogueController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tfAgentId"
    private TextField tfAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="lblMode"
    private Label lblmode; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtFirstName"
    private TextField tfAgtFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtMiddleInitial"
    private TextField tfAgtMiddleInitial; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtLastName"
    private TextField tfAgtLastName; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtBusPhone"
    private TextField tfAgtBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtEmail"
    private TextField tfAgtEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgtPosition"
    private TextField tfAgtPosition; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgencyId"
    private TextField tfAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader


    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader


    private Agent existingAgent;
    private AgentController parentController;


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tfAgentId != null : "fx:id=\"tfAgentId\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert tfAgtFirstName != null : "fx:id=\"tfAgtFirstName\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert tfAgtMiddleInitial != null : "fx:id=\"tfAgtMiddleInitial\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert lblmode != null : "fx:id=\"lblmode\" was not injected: check your FXML file 'dialog-view.fxml'.";
        assert tfAgtLastName != null : "fx:id=\"tfAgtLastName\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert tfAgtBusPhone != null : "fx:id=\"tfAgtBusPhone\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert tfAgtEmail != null : "fx:id=\"tfAgtEmail\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert tfAgtPosition != null : "fx:id=\"tfAgtPosition\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert tfAgencyId != null : "fx:id=\"tfAgencyId\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";


        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node node = (Node) mouseEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void setMode(Agent agentToModify) {
        if (agentToModify != null) {
            tfAgentId.setDisable(true);
            this.existingAgent = agentToModify;
            tfAgentId.setText(String.valueOf(existingAgent.getAgentId()));
            tfAgtFirstName.setText(existingAgent.getAgtFirstName());
            tfAgtMiddleInitial.setText(existingAgent.getAgtMiddleInitial());
            tfAgtLastName.setText(existingAgent.getAgtLastName());
            tfAgtBusPhone.setText(existingAgent.getAgtBusPhone());
            tfAgtEmail.setText(existingAgent.getAgtEmail());
            tfAgtPosition.setText(existingAgent.getAgtPosition());
            tfAgencyId.setText(String.valueOf(existingAgent.getAgencyId()));
        }
    }
    @FXML
    private void onSave(ActionEvent event) {
        Properties p = getProperties();
        try {
            Connection connection = DriverManager.getConnection(p.getProperty("url"), p);
            String sql = "";
            if (existingAgent != null) {
                //build update sql
                sql = "UPDATE `agents` SET `AgtFirstName`=?," +
                        "`AgtMiddleInitial`=?,`AgtLastName`=?,`AgtBusPhone`=?," +
                        "`AgtEmail`=?,`AgtPosition`=?,`AgencyId`=? WHERE AgentId=?";
            } else {
                //build insert sql
                sql = "INSERT INTO `agents`(`AgtFirstName`, `AgtMiddleInitial`, " +
                        "`AgtLastName`, `AgtBusPhone`, `AgtEmail`, `AgtPosition`," +
                        " `AgencyId`) VALUES (?,?,?,?,?,?,?)";
            }
            //updating the database with the agent info
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, tfAgtFirstName.getText());
            stmt.setString(2, tfAgtMiddleInitial.getText());
            stmt.setString(3, tfAgtLastName.getText());
            stmt.setString(4, tfAgtBusPhone.getText());
            stmt.setString(5, tfAgtEmail.getText());
            stmt.setString(6, tfAgtPosition.getText());
            stmt.setInt(7, Integer.parseInt(tfAgencyId.getText()));
            if (existingAgent != null) {
                stmt.setInt(8, Integer.parseInt(tfAgentId.getText()));
            }
            int numRows = stmt.executeUpdate();
            if (numRows == 0) {
                System.out.println("update failed");
            } else {
                System.out.println("update successful");
            }
            connection.close();
            closeStage(event);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) tfAgentId.getScene().getWindow();
        stage.close();
    }

    private Properties getProperties() {
        try {
            FileInputStream fis = new FileInputStream("c:\\connection.properties");
            Properties p = new Properties();
            p.load(fis);
            return p;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setParentController(AgentController parent) {
        this.parentController = parent;
    }
}
