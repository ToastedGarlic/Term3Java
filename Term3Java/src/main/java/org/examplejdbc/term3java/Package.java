package org.examplejdbc.term3java;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Package {
    private SimpleIntegerProperty packageId;
    private SimpleStringProperty pkgName;
    private SimpleStringProperty pkgStartDate;
    private SimpleStringProperty pkgEndDate;
    private SimpleStringProperty pkgDesc;
    private SimpleDoubleProperty pkgBasePrice;
    private SimpleDoubleProperty pkgAgencyCommission;

    // Constructor for creating a new package
    public Package(String pkgName, String pkgStartDate, String pkgEndDate,
                   String pkgDesc, double pkgBasePrice, double pkgAgencyCommission) {
        this.pkgName = new SimpleStringProperty(pkgName);
        this.pkgStartDate = new SimpleStringProperty(pkgStartDate);
        this.pkgEndDate = new SimpleStringProperty(pkgEndDate);
        this.pkgDesc = new SimpleStringProperty(pkgDesc);
        this.pkgBasePrice = new SimpleDoubleProperty(pkgBasePrice);
        this.pkgAgencyCommission = new SimpleDoubleProperty(pkgAgencyCommission);
    }

    // Constructor including packageId, for retrieving existing packages
    public Package(int packageId, String pkgName, String pkgStartDate, String pkgEndDate,
                   String pkgDesc, double pkgBasePrice, double pkgAgencyCommission) {
        this.packageId = new SimpleIntegerProperty(packageId);
        this.pkgName = new SimpleStringProperty(pkgName);
        this.pkgStartDate = new SimpleStringProperty(pkgStartDate);
        this.pkgEndDate = new SimpleStringProperty(pkgEndDate);
        this.pkgDesc = new SimpleStringProperty(pkgDesc);
        this.pkgBasePrice = new SimpleDoubleProperty(pkgBasePrice);
        this.pkgAgencyCommission = new SimpleDoubleProperty(pkgAgencyCommission);
    }

    // Getters and setters
    public int getPackageId() {
        return packageId.get();
    }

    public void setPackageId(int packageId) {
        this.packageId.set(packageId);
    }

    public SimpleIntegerProperty packageIdProperty() {
        return packageId;
    }

    public String getPkgName() {
        return pkgName.get();
    }

    public void setPkgName(String value) {
        pkgName.set(value);
    }

    public SimpleStringProperty pkgNameProperty() {
        return pkgName;
    }

    public String getPkgStartDate() {
        return pkgStartDate.get();
    }

    public void setPkgStartDate(String value) {
        pkgStartDate.set(value);
    }

    public SimpleStringProperty pkgStartDateProperty() {
        return pkgStartDate;
    }

    public String getPkgEndDate() {
        return pkgEndDate.get();
    }

    public void setPkgEndDate(String value) {
        pkgEndDate.set(value);
    }

    public SimpleStringProperty pkgEndDateProperty() {
        return pkgEndDate;
    }

    public String getPkgDesc() {
        return pkgDesc.get();
    }

    public void setPkgDesc(String value) {
        pkgDesc.set(value);
    }

    public SimpleStringProperty pkgDescProperty() {
        return pkgDesc;
    }

    public double getPkgBasePrice() {
        return pkgBasePrice.get();
    }

    public void setPkgBasePrice(double value) {
        pkgBasePrice.set(value);
    }

    public SimpleDoubleProperty pkgBasePriceProperty() {
        return pkgBasePrice;
    }

    public double getPkgAgencyCommission() {
        return pkgAgencyCommission.get();
    }

    public void setPkgAgencyCommission(double value) {
        pkgAgencyCommission.set(value);
    }

    public SimpleDoubleProperty pkgAgencyCommissionProperty() {
        return pkgAgencyCommission;
    }

}