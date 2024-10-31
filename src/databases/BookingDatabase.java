// Import necessary classes and packages
package databases;

import datamodel.Booking;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

// Class responsible for interacting with the Booking database
public class BookingDatabase {

    // Database connection details (consider using environment variables or a config file)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/java";
    private static final String USER = System.getenv("DB_USER"); // Use environment variable
    private static final String PASSWORD = System.getenv("DB_PASSWORD"); // Use environment variable

    // Establish a connection to the database
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // Create the Booking table if it does not exist
    public static void createBookingTable() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            // SQL query to create the Booking table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Booking ("
                    + "name VARCHAR(255) PRIMARY KEY,"
                    + "hotel_name VARCHAR(255),"
                    + "room VARCHAR(255),"
                    + "booking_date DATE,"
                    + "duration INT,"
                    + "paid BOOLEAN)";
            // Execute the query
            statement.executeUpdate(createTableSQL);
            System.out.println("Booking table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert a booking into the database or update if already exists
    public static void insertBooking(Booking booking) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Booking (name, hotel_name, room, booking_date, duration, paid) VALUES (?, ?, ?, ?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE hotel_name = VALUES(hotel_name), room = VALUES(room), " +
                             "booking_date = VALUES(booking_date), duration = VALUES(duration), paid = VALUES(paid)")) {
            // Set parameters for the prepared statement
            preparedStatement.setString(1, booking.getName());
            preparedStatement.setString(2, booking.getHotelName());
            preparedStatement.setString(3, booking.getRoom());
            preparedStatement.setString(4, booking.getBookingDate());
            preparedStatement.setInt(5, booking.getDuration());
            preparedStatement.setBoolean(6, booking.isPaid());

            // Execute the update or insert query
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected by INSERT: " + rowsAffected);
            System.out.println("Booking inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update the booking date for a specific customer
    public static void updateBookingDate(String customerName, String newDate) {
        try {
            // Validate the date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(newDate);

            // If the date format is valid, proceed with the update
            try (Connection connection = getConnection();
                 PreparedStatement checkStatement = connection.prepareStatement(
                         "SELECT * FROM Booking WHERE name = ?");
                 PreparedStatement updateStatement = connection.prepareStatement(
                         "UPDATE Booking SET booking_date = ? WHERE name = ?")) {

                // Check if the customer name exists in the database
                checkStatement.setString(1, customerName);
                if (!checkStatement.executeQuery().next()) {
                    System.out.println("Customer with name " + customerName + " not found in the database.");
                    return;
                }

                // Set parameters for the update statement
                updateStatement.setString(1, newDate);
                updateStatement.setString(2, customerName);

                // Execute the update query
                int rowsAffected = updateStatement.executeUpdate();
                System.out.println("Rows affected by UPDATE: " + rowsAffected);

                System.out.println("Booking date updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
        }
    }

    // Read bookings from a CSV file and insert them into the database
    public static void readBookingsFromCSV(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            // Skip the header line
            br.readLine();

            String line;
            // Iterate through each line in the CSV file
            while ((line = br.readLine()) != null) {
                // Split the line into individual data elements
                String[] data = line.split(",");
                String customerName = data[0].trim();
                String hotelName = data[1].trim();
                String room = data[2].trim();
                String date = data[3].trim();
                int duration = Integer.parseInt(data[4].trim());
                boolean paid = Boolean.parseBoolean(data[5].trim());

                // Create a Booking object from the CSV data
                Booking booking = new Booking(
                        customerName,
                        hotelName,
                        room,
                        date,
                        duration,
                        paid
                );
                // Insert the booking into the database
                insertBooking(booking);
            }
            System.out.println("Bookings read from CSV and inserted into the database successfully.");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Main method to demonstrate the functionality
    public static void main(String[] args) {
        // Create the Booking table
        createBookingTable();

        // Read bookings from a CSV file and insert into the database
        readBookingsFromCSV("data/booking.csv");

        // Scanner for reading user input
        Scanner scanner = new Scanner(System.in);

        // Example: Update booking date for a specific customer
        System.out.print("Enter customer name to update booking date: ");
        String customerNameToUpdate = scanner.nextLine();

        System.out.print("Enter new booking date (YYYY-MM-DD): ");
        String newDate = scanner.nextLine();

        // Update the booking date in the database
        updateBookingDate(customerNameToUpdate, newDate);

        // Close the scanner
        scanner.close();
    }
}
