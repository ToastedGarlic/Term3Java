/**
 * Sample Skeleton for 'AddModifyCustomer.fxml' Controller Class
 */

package org.examplejdbc.term3java;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddModifyCustomerController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddModify"
    private Button btnAddModify; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="lblTitle"
    private Label lblTitle; // Value injected by FXMLLoader

    @FXML // fx:id="tfAgentId"
    private TextField tfAgentId; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustAddress"
    private TextField tfCustAddress; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustBusPhone"
    private TextField tfCustBusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustCity"
    private TextField tfCustCity; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustCountry"
    private TextField tfCustCountry; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustEmail"
    private TextField tfCustEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustFirstName"
    private TextField tfCustFirstName; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustHomePhone"
    private TextField tfCustHomePhone; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustLastName"
    private TextField tfCustLastName; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustPostal"
    private TextField tfCustPostal; // Value injected by FXMLLoader

    @FXML // fx:id="tfCustProv"
    private TextField tfCustProv; // Value injected by FXMLLoader

    private String windowStatus = null;
    private String mode;

    private Integer custId;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAddModify != null : "fx:id=\"btnAddModify\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert lblTitle != null : "fx:id=\"lblTitle\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfAgentId != null : "fx:id=\"tfAgentId\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfCustAddress != null : "fx:id=\"tfCustAddress\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfCustBusPhone != null : "fx:id=\"tfCustBusPhone\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfCustCity != null : "fx:id=\"tfCustCity\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfCustCountry != null : "fx:id=\"tfCustCountry\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfCustEmail != null : "fx:id=\"tfCustEmail\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfCustFirstName != null : "fx:id=\"tfCustFirstName\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfCustHomePhone != null : "fx:id=\"tfCustHomePhone\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfCustLastName != null : "fx:id=\"tfCustLastName\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfCustPostal != null : "fx:id=\"tfCustPostal\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";
        assert tfCustProv != null : "fx:id=\"tfCustProv\" was not injected: check your FXML file 'AddModifyCustomer.fxml'.";

        btnAddModify.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnAddModifyClick(mouseEvent);
            }
        });
        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnCancelClick(mouseEvent);
            }
        });
    }

    public void passMode(String mode) {
        this.mode = mode;


        if (mode.equals("edit"))
        {
            btnAddModify.setText("Update Customer");
        }
        else
        {
            btnAddModify.setText("Add Customer");
        }
    }

    public void loadCustomer(Customer t1) {
        custId = t1.getCustomerId();
        tfCustFirstName.setText(t1.getCustFirstName());
        tfCustLastName.setText(t1.getCustLastName());
        tfCustAddress.setText(t1.getCustAddress());
        tfCustCity.setText(t1.getCustCity());
        tfCustProv.setText(t1.getCustProv());
        tfCustPostal.setText(t1.getCustPostal());
        tfCustCountry.setText(t1.getCustCountry());
        tfCustHomePhone.setText(t1.getCustHomePhone());
        tfCustBusPhone.setText(t1.getCustBusPhone());
        tfCustEmail.setText(t1.getCustEmail());
        tfAgentId.setText(t1.getAgentId() + "");
    }

    private void btnAddModifyClick(MouseEvent mouseEvent) {

        String url = "";
        String user = "";
        String password = "";

        try {
            FileInputStream fis = new FileInputStream("D:\\connection.properties");
            Properties prop = new Properties();
            prop.load(fis);
            url = (String) prop.get("url");
            user = (String) prop.get("user");
            password = (String) prop.get("password");
            Connection conn = DriverManager.getConnection(url, user, password);
            String sql = "";

            if (mode.equals("edit"))
            {
                sql = "UPDATE `customers` SET `CustFirstName`=?,`CustLastName`=?,`CustAddress`=?," +
                        "`CustCity`=?,`CustProv`=?,`CustPostal`=?,`CustCountry`=?,`CustHomePhone`=?," +
                        "`CustBusPhone`=?,`CustEmail`=?,`AgentId`=? WHERE `CustomerId`=?";
            }
            else
            {
                sql = "INSERT INTO `customers`(`CustFirstName`, `CustLastName`, `CustAddress`, " +
                        "`CustCity`, `CustProv`, `CustPostal`, `CustCountry`, `CustHomePhone`, " +
                        "`CustBusPhone`, `CustEmail`, `AgentId`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            }
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tfCustFirstName.getText());
            stmt.setString(2, tfCustLastName.getText());
            stmt.setString(3, tfCustAddress.getText());
            stmt.setString(4, tfCustCity.getText());
            stmt.setString(5, tfCustProv.getText());
            stmt.setString(6, tfCustPostal.getText());
            stmt.setString(7, tfCustCountry.getText());
            stmt.setString(8, tfCustHomePhone.getText());
            stmt.setString(9, tfCustBusPhone.getText());
            stmt.setString(10, tfCustEmail.getText());
            stmt.setInt(11, Integer.parseInt(tfAgentId.getText()));

            if(mode.equals("edit"))
            {
                stmt.setInt(12, custId);
            }

            stmt.executeUpdate();
            conn.close();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void btnCancelClick(MouseEvent mouseEvent) {
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }





}
