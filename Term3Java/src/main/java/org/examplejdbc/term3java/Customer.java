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
                    String custCity, String custProv, String custPostal, String custCountry, String custHomePhone,
                    String custBusPhone, String custEmail, Integer agentId) {
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

    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getCustFirstName() {
        return custFirstName.get();
    }

    public SimpleStringProperty custFirstNameProperty() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName.set(custFirstName);
    }

    public String getCustLastName() {
        return custLastName.get();
    }

    public SimpleStringProperty custLastNameProperty() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName.set(custLastName);
    }

    public String getCustAddress() {
        return custAddress.get();
    }

    public SimpleStringProperty custAddressProperty() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress.set(custAddress);
    }

    public String getCustCity() {
        return custCity.get();
    }

    public SimpleStringProperty custCityProperty() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity.set(custCity);
    }
    public String getCustProv() {
        return custProv.get();
    }

    public SimpleStringProperty custProvProperty() {
        return custProv;
    }

    public void setCustProv(String custProv) {
        this.custProv.set(custProv);
    }

    public String getCustPostal() {
        return custPostal.get();
    }

    public SimpleStringProperty custPostalProperty() {
        return custPostal;
    }

    public void setCustPostal(String custPostal) {
        this.custPostal.set(custPostal);
    }

    public String getCustCountry() {
        return custCountry.get();
    }

    public SimpleStringProperty custCountryProperty() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry.set(custCountry);
    }

    public String getCustHomePhone() {
        return custHomePhone.get();
    }

    public SimpleStringProperty custHomePhoneProperty() {
        return custHomePhone;
    }

    public void setCustHomePhone(String custHomePhone) {
        this.custHomePhone.set(custHomePhone);
    }

    public String getCustBusPhone() {
        return custBusPhone.get();
    }

    public SimpleStringProperty custBusPhoneProperty() {
        return custBusPhone;
    }

    public void setCustBusPhone(String custBusPhone) {
        this.custBusPhone.set(custBusPhone);
    }

    public String getCustEmail() {
        return custEmail.get();
    }

    public SimpleStringProperty custEmailProperty() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail.set(custEmail);
    }

    public int getAgentId() {
        return agentId.get();
    }

    public SimpleIntegerProperty agentIdProperty() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId.set(agentId);
    }
}
