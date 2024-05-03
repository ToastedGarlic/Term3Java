/**
 * Class representing a BookingDetails entry in the travelexperts database
 * Created for workshop 6 of the term 3 threaded project
 * Author: Brannon Howlett
 * Date: 05/01/2024
 */

package org.examplejdbc.term3java;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookingDetails {
    // Properties that must be passed to the database during insertion/updates
    private SimpleIntegerProperty BookingDetailId;
    private SimpleIntegerProperty ItineraryNo;
    private SimpleStringProperty TripStart;
    private SimpleStringProperty TripEnd;
    private SimpleStringProperty Description;
    private SimpleStringProperty Destination;
    private SimpleIntegerProperty BasePrice;
    private SimpleIntegerProperty AgencyCommission;
    private SimpleIntegerProperty BookingId;
    private SimpleStringProperty RegionId;
    private SimpleStringProperty ClassId;
    private SimpleStringProperty FeeId;
    private SimpleIntegerProperty ProductSupplierId;

    // Relational properties for display. Use their related IDs for maintaining data.
    // These do not get inserted into the database
    private SimpleStringProperty BookingNo;
    private SimpleStringProperty RegionName;
    private SimpleStringProperty ClassName;
    private SimpleStringProperty FeeName;
    private SimpleStringProperty ProductSupplier;

    // Constructor containing all properties
    public BookingDetails(int bookingDetailId, int itineraryNo, String tripStart, String tripEnd, String description,
                          String destination, int basePrice, int agencyCommission, int bookingId, String regionId,
                          String classId, String feeId, int productSupplierId, String bookingNo, String regionName,
                          String className, String feeName, String productSupplier) {
        BookingDetailId = new SimpleIntegerProperty(bookingDetailId);
        ItineraryNo = new SimpleIntegerProperty(itineraryNo);
        TripStart = new SimpleStringProperty(tripStart);
        TripEnd = new SimpleStringProperty(tripEnd);
        Description = new SimpleStringProperty(description);
        Destination = new SimpleStringProperty(destination);
        BasePrice = new SimpleIntegerProperty(basePrice);
        AgencyCommission = new SimpleIntegerProperty(agencyCommission);
        BookingId = new SimpleIntegerProperty(bookingId);
        RegionId = new SimpleStringProperty(regionId);
        ClassId = new SimpleStringProperty(classId);
        FeeId = new SimpleStringProperty(feeId);
        ProductSupplierId = new SimpleIntegerProperty(productSupplierId);
        BookingNo = new SimpleStringProperty(bookingNo);
        RegionName = new SimpleStringProperty(regionName);
        ClassName = new SimpleStringProperty(className);
        FeeName = new SimpleStringProperty(feeName);
        ProductSupplier = new SimpleStringProperty(productSupplier);
    }

    // Default constructor. Instantiates all simpleProperties without values,
    // if this isn't done, they can't be assigned later
    public BookingDetails(){
        BookingDetailId = new SimpleIntegerProperty();
        ItineraryNo = new SimpleIntegerProperty();
        TripStart = new SimpleStringProperty();
        TripEnd = new SimpleStringProperty();
        Description = new SimpleStringProperty();
        Destination = new SimpleStringProperty();
        BasePrice = new SimpleIntegerProperty();
        AgencyCommission = new SimpleIntegerProperty();
        BookingId = new SimpleIntegerProperty();
        RegionId = new SimpleStringProperty();
        ClassId = new SimpleStringProperty();
        FeeId = new SimpleStringProperty();
        ProductSupplierId = new SimpleIntegerProperty();
        BookingNo = new SimpleStringProperty();
        RegionName = new SimpleStringProperty();
        ClassName = new SimpleStringProperty();
        FeeName = new SimpleStringProperty();
        ProductSupplier = new SimpleStringProperty();
    }

    // GETTERS AND SETTERS
    public int getBookingDetailId() {return BookingDetailId.get();}
    public SimpleIntegerProperty bookingDetailIdProperty() {return BookingDetailId;}
    public void setBookingDetailId(int bookingDetailId) {this.BookingDetailId.set(bookingDetailId);}

    public int getItineraryNo() {return ItineraryNo.get();}
    public SimpleIntegerProperty itineraryNoProperty() {return ItineraryNo;}
    public void setItineraryNo(int itineraryNo) {this.ItineraryNo.set(itineraryNo);}

    public String getTripStart() {return TripStart.get();}
    public SimpleStringProperty tripStartProperty() {return TripStart;}
    public void setTripStart(String tripStart) {this.TripStart.set(tripStart);}

    public String getTripEnd() {return TripEnd.get();}
    public SimpleStringProperty tripEndProperty() {return TripEnd;}
    public void setTripEnd(String tripEnd) {this.TripEnd.set(tripEnd);}

    public String getDescription() {return Description.get();}
    public SimpleStringProperty descriptionProperty() {return Description;}
    public void setDescription(String description) {this.Description.set(description);}

    public String getDestination() {return Destination.get();}
    public SimpleStringProperty destinationProperty() {return Destination;}
    public void setDestination(String destination) {this.Destination.set(destination);}

    public int getBasePrice() {return BasePrice.get();}
    public SimpleIntegerProperty basePriceProperty() {return BasePrice;}
    public void setBasePrice(int basePrice) {this.BasePrice.set(basePrice);}

    public int getAgencyCommission() {return AgencyCommission.get();}
    public SimpleIntegerProperty agencyCommissionProperty() {return AgencyCommission;}
    public void setAgencyCommission(int agencyCommission) {this.AgencyCommission.set(agencyCommission);}

    public int getBookingId() {return BookingId.get();}
    public SimpleIntegerProperty bookingIdProperty() {return BookingId;}
    public void setBookingId(int bookingId) {this.BookingId.set(bookingId);}

    public String getRegionId() {return RegionId.get();}
    public SimpleStringProperty regionIdProperty() {return RegionId;}
    public void setRegionId(String regionId) {this.RegionId.set(regionId);}

    public String getClassId() {return ClassId.get();}
    public SimpleStringProperty classIdProperty() {return ClassId;}
    public void setClassId(String classId) {this.ClassId.set(classId);}

    public String getFeeId() {return FeeId.get();}
    public SimpleStringProperty feeIdProperty() {return FeeId;}
    public void setFeeId(String feeId) {this.FeeId.set(feeId);}

    public int getProductSupplierId() {return ProductSupplierId.get();}
    public SimpleIntegerProperty productSupplierIdProperty() {return ProductSupplierId;}
    public void setProductSupplierId(int productSupplierId) {this.ProductSupplierId.set(productSupplierId);}

    public String getRegionName() {return RegionName.get();}
    public SimpleStringProperty regionNameProperty() {return RegionName;}
    public void setRegionName(String regionName) {this.RegionName.set(regionName);}

    public String getBookingNo() {return BookingNo.get();}
    public SimpleStringProperty bookingNoProperty() {return BookingNo;}
    public void setBookingNo(String bookingNo) {this.BookingNo.set(bookingNo);}

    public String getClassName() {return ClassName.get();}
    public SimpleStringProperty classNameProperty() {return ClassName;}
    public void setClassName(String className) {this.ClassName.set(className);}

    public String getFeeName() {return FeeName.get();}
    public SimpleStringProperty feeNameProperty() {return FeeName;}
    public void setFeeName(String feeName) {this.FeeName.set(feeName);}

    public String getProductSupplier() {return ProductSupplier.get();}
    public SimpleStringProperty productSupplierProperty() {return ProductSupplier;}
    public void setProductSupplier(String productSupplier) {this.ProductSupplier.set(productSupplier);}
}