package org.examplejdbc.term3java;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty customerId;
    private SimpleStringProperty custFirstName;
    private SimpleStringProperty custLastName;
    private SimpleStringProperty custAddress;
    private SimpleStringProperty custCity;
    private SimpleStringProperty custProv;
    private SimpleStringProperty custPostal;
    private SimpleStringProperty custCountry;
    private SimpleStringProperty custHomePhone;
    private SimpleStringProperty custBusPhone;
    private SimpleStringProperty custEmail;
    private SimpleIntegerProperty agentId;

    public Customer(int customerId, String custFirstName, String custLastName, String custAddress,
                    String custCity, String custProv, String custPostal, String custCountry,
                    String custHomePhone, String custBusPhone, String custEmail, int agentId) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.custFirstName = new SimpleStringProperty(custFirstName);
        this.custLastName = new SimpleStringProperty(custLastName);
        this.custAddress = new SimpleStringProperty(custAddress);
        this.custCity = new SimpleStringProperty(custCity);
        this.custProv = new SimpleStringProperty(custProv);
        this.custPostal = new SimpleStringProperty(custPostal);
        this.custCountry = new SimpleStringProperty(custCountry);
        this.custHomePhone = new SimpleStringProperty(custHomePhone);
        this.custBusPhone = new SimpleStringProperty(custBusPhone);
        this.custEmail = new SimpleStringProperty(custEmail);
        this.agentId = new SimpleIntegerProperty(agentId);
    }

    // Getters and setters
    public int getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getCustFirstName() {
        return custFirstName.get();
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName.set(custFirstName);
    }

    public String getCustLastName() {
        return custLastName.get();
    }

    public void setCustLastName(String custLastName) {
        this.custLastName.set(custLastName);
    }

    public String getCustAddress() {
        return custAddress.get();
    }

    public void setCustAddress(String custAddress) {
        this.custAddress.set(custAddress);
    }

    public String getCustCity() {
        return custCity.get();
    }

    public void setCustCity(String custCity) {
        this.custCity.set(custCity);
    }

    public String getCustProv() {
        return custProv.get();
    }

    public void setCustProv(String custProv) {
        this.custProv.set(custProv);
    }

    public String getCustPostal() {
        return custPostal.get();
    }

    public void setCustPostal(String custPostal) {
        this.custPostal.set(custPostal);
    }

    public String getCustCountry() {
        return custCountry.get();
    }

    public void setCustCountry(String custCountry) {
        this.custCountry.set(custCountry);
    }

    public String getCustHomePhone() {
        return custHomePhone.get();
    }

    public void setCustHomePhone(String custHomePhone) {
        this.custHomePhone.set(custHomePhone);
    }

    public String getCustBusPhone() {
        return custBusPhone.get();
    }

    public void setCustBusPhone(String custBusPhone) {
        this.custBusPhone.set(custBusPhone);
    }

    public String getCustEmail() {
        return custEmail.get();
    }

    public void setCustEmail(String custEmail) {
        this.custEmail.set(custEmail);
    }

    public int getAgentId() {
        return agentId.get();
    }

    public void setAgentId(int agentId) {
        this.agentId.set(agentId);
    }

    // Property getters
    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public SimpleStringProperty custFirstNameProperty() {
        return custFirstName;
    }

    public SimpleStringProperty custLastNameProperty() {
        return custLastName;
    }

    public SimpleStringProperty custAddressProperty() {
        return custAddress;
    }

    public SimpleStringProperty custCityProperty() {
        return custCity;
    }

    public SimpleStringProperty custProvProperty() {
        return custProv;
    }

    public SimpleStringProperty custPostalProperty() {
        return custPostal;
    }

    public SimpleStringProperty custCountryProperty() {
        return custCountry;
    }

    public SimpleStringProperty custHomePhoneProperty() {
        return custHomePhone;
    }

    public SimpleStringProperty custBusPhoneProperty() {
        return custBusPhone;
    }

    public SimpleStringProperty custEmailProperty() {
        return custEmail;
    }

    public SimpleIntegerProperty agentIdProperty() {
        return agentId;
    }
}
