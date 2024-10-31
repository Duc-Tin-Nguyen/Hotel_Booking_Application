// This package contains the data model for booking-related classes
package datamodel;

// This class represents a Booking object
public class Booking {
    // Private fields to store information about the booking
    private String name;         // Guest's name
    private String hotelName;    // Name of the hotel
    private String room;         // Room number
    private String bookingDate;  // Date of the booking
    private int duration;        // Duration of the stay in days
    private boolean paid;        // Flag indicating whether the booking is paid

    // Constructor to initialize the Booking object with provided values
    public Booking(String name, String hotelName, String room, String bookingDate, int duration, boolean paid) {
        // Assigning values from parameters to the corresponding fields
        this.name = name;
        this.hotelName = hotelName;
        this.room = room;
        this.bookingDate = bookingDate;
        this.duration = duration;
        this.paid = paid;
    }

    // Getter method to retrieve the guest's name
    public String getName() {
        return name;
    }

    // Getter method to retrieve the hotel name
    public String getHotelName() {
        return hotelName;
    }

    // Getter method to retrieve the room number
    public String getRoom() {
        return room;
    }

    // Getter method to retrieve the booking date
    public String getBookingDate() {
        return bookingDate;
    }

    // Getter method to retrieve the duration of the stay
    public int getDuration() {
        return duration;
    }

    // Getter method to check whether the booking is paid
    public boolean isPaid() {
        return paid;
    }
}
