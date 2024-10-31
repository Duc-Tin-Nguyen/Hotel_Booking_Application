package databases;

import datamodel.Hotel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HotelDatabase {

    // Database connection details (consider using environment variables or a config file)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/java";
    private static final String USER = System.getenv("DB_USER"); // Use environment variable
    private static final String PASSWORD = System.getenv("DB_PASSWORD"); // Use environment variable

    // List to store Hotel objects
    private List<Hotel> hotelList = new ArrayList<>();

    // Establishes a database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // Creates the Hotel table in the database if it does not exist
    public void createHotelTable() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Hotel ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(255),"
                    + "available_room VARCHAR(255),"
                    + "available_date VARCHAR(255),"
                    + "rating DOUBLE,"
                    + "pricePerDay DOUBLE)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Hotel table created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Inserts a Hotel object into the database
    public void insertHotel(Hotel hotel) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Hotel (name, available_room, available_date, rating, pricePerDay) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, hotel.getName());
            preparedStatement.setString(2, hotel.getAvailableRoom());
            preparedStatement.setString(3, hotel.getAvailableDate());
            preparedStatement.setDouble(4, hotel.getRating());
            preparedStatement.setDouble(5, hotel.getPricePerDay());

            // Execute the insert query
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected by INSERT: " + rowsAffected);

            // Add the inserted hotel to the list
            hotelList.add(hotel);

            System.out.println("Hotel inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Returns the list of hotels
    public List<Hotel> getHotelList() {
        return hotelList;
    }

    // Retrieves a hotel by name from the list
    public Hotel getHotelByName(String name) {
        for (Hotel hotel : hotelList) {
            if (hotel.getName().equals(name)) {
                return hotel;
            }
        }
        return null;
    }

    // Displays destinations with hotel cost more than 500€
    public void displayHighCostDestinations() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT name FROM Hotel WHERE pricePerDay > 500");
            System.out.println("Destinations with hotel cost more than 500€:");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Reads hotel data from a CSV file and inserts into the database
    public void readHotelsFromCSV(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            // Skip the header line
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Hotel hotel = new Hotel(
                        data[0],
                        data[1],
                        data[2],
                        Double.parseDouble(data[3]),
                        Double.parseDouble(data[4])
                );
                // Insert the hotel into the database
                insertHotel(hotel);
            }
            System.out.println("Hotels read from CSV and inserted into the database successfully.");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Create an instance of HotelDatabase
        HotelDatabase hotelDatabase = new HotelDatabase();
        // Create the Hotel table in the database
        hotelDatabase.createHotelTable();
        // Read hotels from CSV and insert into the database
        hotelDatabase.readHotelsFromCSV("data/hotel.csv");

        // Display destinations with high hotel cost
        hotelDatabase.displayHighCostDestinations();
    }
}
