# Hotel Booking Application

## Overview
This application allows users to book hotels, view available hotels, and manage bookings. It is built using Java and utilizes a MySQL database for data storage.

## Prerequisites
Before running the application, ensure you have the following installed:
- **Java Development Kit (JDK)**: Version 8 or higher.
- **MySQL Server**: Ensure that MySQL is installed and running on your machine.
- **Maven**: For managing dependencies (optional, if you are using an IDE that handles it).

## Setting Up the Database
1. **Create a Database**:
   - Open your MySQL client (e.g., MySQL Workbench).
   - Create a new database named `java` (or any name you prefer, but ensure to update the connection string in the code accordingly).

   ```sql
   CREATE DATABASE java;
   ```

2. **Create Tables**:
   - The application will automatically create the necessary tables (`Hotel` and `Booking`) when it runs for the first time. However, you can also create them manually using the following SQL commands:

   ```sql
   CREATE TABLE IF NOT EXISTS Hotel (
       id INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(255),
       available_room VARCHAR(255),
       available_date VARCHAR(255),
       rating DOUBLE,
       pricePerDay DOUBLE
   );

   CREATE TABLE IF NOT EXISTS Booking (
       name VARCHAR(255) PRIMARY KEY,
       hotel_name VARCHAR(255),
       room VARCHAR(255),
       booking_date DATE,
       duration INT,
       paid BOOLEAN
   );
   ```

3. **Set Up Environment Variables**:
   - Set the following environment variables to avoid hardcoding sensitive information in the code:
     - `DB_USER`: Your MySQL username (default is usually `root`).
     - `DB_PASSWORD`: Your MySQL password.

## Running the Application
1. **Clone the Repository**:
   - Clone the repository to your local machine using Git:

   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Build the Application**:
   - If you are using Maven, navigate to the project directory and run:

   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   - You can run the application from the command line or your IDE. If using the command line, navigate to the `target` directory and run:

   ```bash
   java -cp target/<your-jar-file>.jar launcher.Main
   ```

   Replace `<your-jar-file>` with the actual name of the generated JAR file.

4. **Using the GUI**:
   - The application will launch a graphical user interface (GUI) for hotel booking.
   - You can interact with the application through the GUI.

## Application Functionalities
### 1. Viewing Available Hotels
- Upon launching the application, the main window will display a table of available hotels.
- The table includes columns for:
  - Hotel Name
  - Available Room
  - Available Date
  - Rating
  - Price Per Day

### 2. Booking a Hotel
- To book a hotel:
  1. Select a hotel from the table.
  2. Enter the customer name in the "Customer Name" field.
  3. Enter the duration of stay in the "Duration (days)" field.
  4. Check the "Paid" checkbox if the booking is paid.
  5. Click the "Book Hotel" button.
- A success message will be displayed upon successful booking.

### 3. Updating Booking Dates
- The application allows you to update the booking date for a specific customer:
  1. Enter the customer name in the console when prompted.
  2. Enter the new booking date in the format `YYYY-MM-DD`.
  3. The application will validate the date and update it in the database.

### 4. Reading Data from CSV
- The application can read hotel and booking data from CSV files:
  - Ensure the CSV files are formatted correctly and located in the `data` directory.
  - The application will read from `data/hotel.csv` for hotel data and `data/booking.csv` for booking data upon startup.

### 5. Displaying High-Cost Destinations
- The application can display destinations with hotel costs greater than 500€.
- This functionality is triggered automatically when the application starts.

### 6. Displaying Paid Customer Names
- The application will display the names of paid customers in ascending order upon startup.

## Troubleshooting
- If you encounter issues, ensure that:
  - The MySQL server is running.
  - The database connection details are correct.
  - The CSV files are correctly formatted and located in the `data` directory.

## Conclusion
This application provides a simple interface for managing hotel bookings. Feel free to modify and enhance the application as needed.

For a more detailed explanation, please read the Tourism Agency Java Project Explaination.pdf.
