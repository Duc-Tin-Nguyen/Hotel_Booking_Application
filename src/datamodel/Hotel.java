// This package contains the data model for hotel-related classes
package datamodel;

import java.util.ArrayList;
import java.util.List;

// This class represents a Hotel object
public class Hotel {
    // Removed id field as it's not present in the CSV
    private String name;             // Hotel's name
    private String available_room;   // Available room information
    private String available_date;   // Available date information
    private double rating;           // Rating of the hotel
    private double pricePerDay;      // Price per day for a room

    // Static list to store all hotels (simulating a database)
    private static List<Hotel> allHotels = new ArrayList<>();

    // Constructor to initialize the Hotel object with provided values
    public Hotel(String name, String available_room, String available_date, double rating, double pricePerDay) {
        this.name = name;
        this.available_room = available_room;
        this.available_date = available_date;
        this.rating = rating;
        this.pricePerDay = pricePerDay;

        // Add each hotel to the static list
        allHotels.add(this);
    }

    // Getter methods

    // Getter method to retrieve the hotel's name
    public String getName() {
        return name;
    }

    // Getter method to retrieve available room information
    public String getAvailableRoom() {
        return available_room;
    }

    // Getter method to retrieve available date information
    public String getAvailableDate() {
        return available_date;
    }

    // Getter method to retrieve the hotel's rating
    public double getRating() {
        return rating;
    }

    // Getter method to retrieve the price per day for a room
    public double getPricePerDay() {
        return pricePerDay;
    }

    // Getter method to retrieve all hotels
    public static List<Hotel> getAllHotels() {
        return allHotels;
    }

    // Getter method to retrieve a hotel by name
    public static Hotel getHotelByName(String name) {
        for (Hotel hotel : allHotels) {
            if (hotel.getName().equals(name)) {
                return hotel;
            }
        }
        return null;
    }

    // Getter method to retrieve a hotel ID by name
    public static int getHotelId(String name) {
        for (Hotel hotel : allHotels) {
            if (hotel.getName().equals(name)) {
                // Assuming an ID is the index in the list (0-based)
                return allHotels.indexOf(hotel);
            }
        }
        return -1;
    }

    // Override toString method for better string representation of the object
    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", available_room='" + available_room + '\'' +
                ", available_date='" + available_date + '\'' +
                ", rating=" + rating +
                ", pricePerDay=" + pricePerDay +
                '}';
    }

    // Setter method for updating the rating if needed
    public void setRating(double newRating) {
        this.rating = newRating;
    }
}
