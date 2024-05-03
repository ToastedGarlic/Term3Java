package org.examplejdbc.term3java;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Booking {
    private SimpleIntegerProperty BookingId;
    private SimpleStringProperty BookingDate;
    private SimpleStringProperty BookingNo;
    private SimpleIntegerProperty TravelerCount;
    private SimpleStringProperty Customer;
    private SimpleStringProperty Package;
    private SimpleStringProperty TripType;

    // Constructor for creating a new booking
    public Booking(String BookingDate, String BookingNo, int TravelerCount, String Customer, String Package, String TripType) {
        this.BookingDate = new SimpleStringProperty(BookingDate);
        this.BookingNo = new SimpleStringProperty(BookingNo);
        this.TravelerCount = new SimpleIntegerProperty(TravelerCount);
        this.Customer = new SimpleStringProperty(Customer);
        this.Package = new SimpleStringProperty(Package);
        this.TripType = new SimpleStringProperty(TripType);
    }

    // Constructor including packageId, for retrieving existing bookings
    public Booking(int BookingId, String BookingDate, String BookingNo, int TravelerCount, String Customer, String Package, String TripType) {
        this.BookingId = new SimpleIntegerProperty(BookingId);
        this.BookingDate = new SimpleStringProperty(BookingDate);
        this.BookingNo = new SimpleStringProperty(BookingNo);
        this.TravelerCount = new SimpleIntegerProperty(TravelerCount);
        this.Customer = new SimpleStringProperty(Customer);
        this.Package = new SimpleStringProperty(Package);
        this.TripType = new SimpleStringProperty(TripType);
    }
    public int getBookingId() {return BookingId.get();}
    public String getBookingDate() {return BookingDate.get();}
    public String getBookingNo() {return BookingNo.get();}
    public int getTravelerCount() {return TravelerCount.get();}
    public String getCustomer() {return Customer.get();}
    public String getPackage() {return Package.get();}
    public String getTripType() {return TripType.get();}
    public void setBookingId(int BookingId) {this.BookingId = new SimpleIntegerProperty(BookingId);}
    public void setBookingDate(String BookingDate) {this.BookingDate = new SimpleStringProperty(BookingDate);}
    public void setBookingNo(String BookingNo) {this.BookingNo = new SimpleStringProperty(BookingNo);}
    public void setCustomer(String Customer) {this.Customer = new SimpleStringProperty(Customer);}
    public void setPackage(String Package) {this.Package = new SimpleStringProperty(Package);}
    public void setTripType(String TripType) {this.TripType = new SimpleStringProperty(TripType);}
    public void setTravelerCount(int TravelerCount) {this.TravelerCount = new SimpleIntegerProperty(TravelerCount);
    }

}