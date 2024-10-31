// This package contains the launcher class to run the application
package launcher;

import services.BookingService;
import services.HotelService;
import swingdatabase.BookingGUI;

import javax.swing.*;

// Main class to start the application
public class Main {
    // Main method, entry point of the program
    public static void main(String[] args) {
        // Using SwingUtilities.invokeLater to ensure Swing components are initialized on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Creating an instance of BookingGUI (the main graphical user interface)
            BookingGUI hotelRegistrationGUI = new BookingGUI();
            // Making the GUI visible
            hotelRegistrationGUI.setVisible(true);

            // Creating instances of HotelService and BookingService for handling hotel and booking-related operations
            HotelService hotelService = new HotelService();
            BookingService bookingService = new BookingService();

            // Reading hotel information from a CSV file and populating the hotel service
            hotelService.readHotelsFromCSV("data/hotel.csv");

            // Reading booking information from a CSV file and populating the booking service
            bookingService.readBookingsFromCSV("data/booking.csv");

            // Displaying hotels with high rating and lowest price
            hotelService.displayHighRatingLowestPriceHotels();

            // Displaying names of paid customers in ascending order
            bookingService.displayPaidCustomerNamesInAscendingOrder();
        });
    }
}
