/**
 * Sample Skeleton for 'agentdialogue-view.fxml' Controller Class
 */

package org.example.jdbc.term3java;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

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

    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="lblMode"
    private Label lblMode; // Value injected by FXMLLoader

    private String mode;
    private AgentController parentController;
    private Agent currentAgent;

    @FXML // This method is called by the FXMLLoader when initialization is complete
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
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";
        assert lblMode != null : "fx:id=\"lblMode\" was not injected: check your FXML file 'agentdialogue-view.fxml'.";

        tfAgentId.setDisable(true);
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnSaveClicked(mouseEvent);
            }
        });

        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnDeleteClicked(mouseEvent);
            }
        });

        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node node = (Node) mouseEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void btnDeleteClicked(MouseEvent mouseEvent) {
        Properties p = getProperties();
        try {
            Connection connection = DriverManager.getConnection(p.getProperty("url"), p);
            String sql = "delete from agents where AgentId=?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(tfAgentId.getText()));
            int numRows = stmt.executeUpdate();
            if (numRows==0) {
                System.out.println("delete failed");
            } else {
                System.out.println("delete successful");
            }
            connection.close();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete failed");
            alert.setContentText("Agent has customers and cannot be deleted");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private void btnSaveClicked(MouseEvent mouseEvent) {
        Properties p = getProperties();
        try {
            Connection connection = DriverManager.getConnection(p.getProperty("url"), p);
            String sql = "";
            if (mode.equals("edit"))
            {
                //build update sql
                sql = "UPDATE `agents` SET `AgtFirstName`=?," +
                        "`AgtMiddleInitial`=?,`AgtLastName`=?,`AgtBusPhone`=?," +
                        "`AgtEmail`=?,`AgtPosition`=?,`AgencyId`=? WHERE AgentId=?";
            }
            else
            {
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
            if (mode.equals("edit"))
            {
                stmt.setInt(8, Integer.parseInt(tfAgentId.getText()));
            }
            int numRows = stmt.executeUpdate();
            if (numRows==0) {
                System.out.println("update failed");
            } else {
                System.out.println("update successful");
            }
            connection.close();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void processAgent(Agent t1) {

        tfAgentId.setText(t1.getAgentId() + "");
        tfAgtFirstName.setText(t1.getAgtFirstName());
        tfAgtMiddleInitial.setText(t1.getAgtMiddleInitial());
        tfAgtLastName.setText(t1.getAgtLastName());
        tfAgtBusPhone.setText(t1.getAgtBusPhone());
        tfAgtEmail.setText(t1.getAgtEmail());
        tfAgtPosition.setText(t1.getAgtPosition());
        tfAgencyId.setText(t1.getAgencyId() + "");
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

    public void passMode(String mode) {
        this.mode = mode;
        lblmode.setText(mode);

        if (mode.equals("add"))
        {
            btnDelete.setVisible(false);
        }
    }
    public void setMode(Agent editAgent) {
        if (editAgent != null) {
            this.currentAgent = editAgent;
            tfAgtFirstName.setText(currentAgent.getAgtFirstName());
            tfAgtMiddleInitial.setText(currentAgent.getAgtMiddleInitial());
            tfAgtLastName.setText(currentAgent.getAgtLastName());
            tfAgtBusPhone.setText(currentAgent.getAgtBusPhone());
            tfAgtEmail.setText(currentAgent.getAgtEmail());
            tfAgtPosition.setText(currentAgent.getAgtPosition());
            tfAgencyId.setText(String.valueOf(currentAgent.getAgencyId()));
        }
}
    public void setParentController(AgentController parent) {
        this.parentController = parent;
    }


}

