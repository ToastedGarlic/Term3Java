/**
 * Sample Skeleton for 'agentsView.fxml' Controller Class
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    private ObservableList<Agent> data = FXCollections.observableArrayList();
    private String mode;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tvAgents != null : "fx:id=\"tvAgents\" was not injected: check your FXML file 'agentsView.fxml'.";
        assert colAgentId != null : "fx:id=\"colAgentId\" was not injected: check your FXML file 'agentsView.fxml'.";
        assert colFName != null : "fx:id=\"colFName\" was not injected: check your FXML file 'agentsView.fxml'.";
        assert colInitial != null : "fx:id=\"colInitial\" was not injected: check your FXML file 'agentsView.fxml'.";
        assert colLName != null : "fx:id=\"colLName\" was not injected: check your FXML file 'agentsView.fxml'.";
        assert colPhone != null : "fx:id=\"colPhone\" was not injected: check your FXML file 'agentsView.fxml'.";
        assert colEmail != null : "fx:id=\"colEmail\" was not injected: check your FXML file 'agentsView.fxml'.";
        assert colPosition != null : "fx:id=\"colPosition\" was not injected: check your FXML file 'agentsView.fxml'.";
        assert colAgencyId != null : "fx:id=\"colAgencyId\" was not injected: check your FXML file 'agentsView.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'agentsView.fxml'.";

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
        tvAgents.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agent>() {
            @Override
            public void changed(ObservableValue<? extends Agent> observableValue, Agent agent, Agent t1) {
                if(tvAgents.getSelectionModel().isSelected(tvAgents.getSelectionModel().getSelectedIndex()))
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mode = "edit";
                            openDialog(t1, mode);
                        }
                    });
                }
            }
        });


    }

    private void openDialog(Agent t1, String mode) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("agentdialog.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AgentDialogueController dialogController = fxmlLoader.getController();
        dialogController.passMode(mode);
        if (mode.equals("edit"))
        {
            dialogController.processAgent(t1);
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        getAgents();


    }

    private void getAgents() {
        data.clear();

        String url = "";
        String user = "";
        String password = "";

        try {
            FileInputStream fis = new FileInputStream("c:\\connection.properties");
            Properties prop = new Properties();
            prop.load(fis);
            url = (String) prop.get("url");
            user = (String) prop.get("user");
            password = (String) prop.get("password");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from agents");
            while (rs.next())
            {
                data.add(new Agent(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8)));
            }
            conn.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mode = "add";
                openDialog(null, mode);
            }
        });


    }

}

