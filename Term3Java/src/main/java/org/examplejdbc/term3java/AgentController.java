/**
 * Sample Skeleton for 'agents-view.fxml' Controller Class
 */

package org.examplejdbc.term3java;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AgentController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tvAgents"
    private TableView<Agent> tvAgents; // Value injected by FXMLLoader

    @FXML // fx:id="colAgentId"
    private TableColumn<Agent, Integer> colAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="colFName"
    private TableColumn<Agent, String> colFName; // Value injected by FXMLLoader

    @FXML // fx:id="colInitial"
    private TableColumn<Agent, String> colInitial; // Value injected by FXMLLoader

    @FXML // fx:id="colLName"
    private TableColumn<Agent, String> colLName; // Value injected by FXMLLoader

    @FXML // fx:id="colPhone"
    private TableColumn<Agent, String> colPhone; // Value injected by FXMLLoader

    @FXML // fx:id="colEmail"
    private TableColumn<Agent, String> colEmail; // Value injected by FXMLLoader

    @FXML // fx:id="colPosition"
    private TableColumn<Agent, String> colPosition; // Value injected by FXMLLoader

    @FXML // fx:id="colAgencyId"
    private TableColumn<Agent, Integer> colAgencyId; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnModifyAgent"
    private Button btnModifyAgent; // Value injected by FXMLLoader

    @FXML // fx:id="btnDeleteAgent"
    private Button btnDeleteAgent; // Value injected by FXMLLoader



    private ObservableList<Agent> data = FXCollections.observableArrayList();
    private Properties prop = new Properties();

    public AgentController(){

    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tvAgents != null : "fx:id=\"tvAgents\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colAgentId != null : "fx:id=\"colAgentId\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colFName != null : "fx:id=\"colFName\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colInitial != null : "fx:id=\"colInitial\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colLName != null : "fx:id=\"colLName\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colPhone != null : "fx:id=\"colPhone\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colEmail != null : "fx:id=\"colEmail\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colPosition != null : "fx:id=\"colPosition\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert colAgencyId != null : "fx:id=\"colAgencyId\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert btnModifyAgent != null : "fx:id=\"btnModifyAgent\" was not injected: check your FXML file 'agents-view.fxml'.";
        assert btnDeleteAgent != null : "fx:id=\"btnDeleteAgent\" was not injected: check your FXML file 'agents-view.fxml'.";


        colAgentId.setCellValueFactory(new PropertyValueFactory<Agent, Integer>("agentId"));
        colFName.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtFirstName"));
        colInitial.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtMiddleInitial"));
        colLName.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtLastName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtBusPhone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtEmail"));
        colPosition.setCellValueFactory(new PropertyValueFactory<Agent, String>("agtPosition"));
        colAgencyId.setCellValueFactory(new PropertyValueFactory<Agent, Integer>("agencyId"));

        tvAgents.setItems(data);
        getAgents();

    }
    private void getAgents() {
        // Load connection properties
        try (FileInputStream fis = new FileInputStream("c:\\connection.properties")) {
            prop.load(fis);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            // Connect to the database and fetch agents
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM agents")) {
                data.clear();
                while (rs.next()) {
                    Agent newAgent = new Agent(
                            rs.getInt("AgentId"),
                            rs.getString("AgtFirstName"),
                            rs.getString("AgtMiddleInitial"),
                            rs.getString("AgtLastName"),
                            rs.getString("AgtBusPhone"),
                            rs.getString("AgtEmail"),
                            rs.getString("AgtPosition"),
                            rs.getInt("AgencyId")

                    );
                    data.add(newAgent);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Consider more user-friendly error handling
            showAlert("Error", "Cannot load agent information.");
        }


    }
    @FXML
    void onAddAgent(ActionEvent event) {
        showAddModifyAgentDialog(null);
    }
    @FXML
    void onModifyAgent(ActionEvent event) {

        Agent selectedAgent = tvAgents.getSelectionModel().getSelectedItem();
        if (selectedAgent != null) {
            showAddModifyAgentDialog(selectedAgent);
        } else {
            showAlert("No Selection", "Please select an agent to modify.");
        }

    }

    @FXML
    void onDeleteAgent(ActionEvent event) {
        Agent selectedAgent = tvAgents.getSelectionModel().getSelectedItem();
        if (selectedAgent != null && showConfirmation("Delete Agent", "Are you sure you want to delete this agent?")) {
            // Delete logic here
            try (Connection conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password"));
                 Statement stmt = conn.createStatement()) {
                String sql = "DELETE FROM agents WHERE AgentId = " + selectedAgent.getAgentId();
                int rows = stmt.executeUpdate(sql);
                if (rows > 0) {
                    data.remove(selectedAgent);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to delete the agent from the database.");
            }
        }
        else
        {
            showAlert("No Selection", "Please select an agent to delete.");
        }

    }
    private void showAddModifyAgentDialog(Agent agentToModify)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agentdialogue-view.fxml"));
            Parent root = loader.load();

            AgentDialogueController controller = loader.getController();
            controller.setParentController(this);
            controller.setMode(agentToModify);

            Stage dialogStage = new Stage();
            dialogStage.setTitle((agentToModify == null) ? "Add Agent" : "Modify Agent");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tvAgents.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            getAgents();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            showAlert("Error", "Cannot load the agent dialogue window.");
        }
    }


    private boolean showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR, content, ButtonType.OK);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

}


