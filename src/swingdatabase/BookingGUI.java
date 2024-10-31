package swingdatabase;

import databases.BookingDatabase;
import databases.HotelDatabase;
import datamodel.Booking;
import datamodel.Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookingGUI extends JFrame {

    private JTable hotelTable;
    private DefaultTableModel tableModel;

    private JButton bookButton;
    private JTextField customerNameField;
    private JTextField durationField;
    private JCheckBox paidCheckBox;

    private Hotel selectedHotel;
    private HotelDatabase hotelDatabase;
    private BookingDatabase bookingDatabase;

    // Constructor
    public BookingGUI() {
        initialize();   // Initialize databases
        setupUI();      // Setup the user interface
        loadData();     // Load initial data into the table
        setupListeners(); // Setup event listeners
    }

    // Initialize databases
    private void initialize() {
        hotelDatabase = new HotelDatabase();
        hotelDatabase.createHotelTable();
        hotelDatabase.readHotelsFromCSV("data/hotel.csv");
        bookingDatabase = new BookingDatabase();
        bookingDatabase.createBookingTable();
    }

    // Setup the graphical user interface
    private void setupUI() {
        setTitle("Vietnam Hotel Booking");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Table to display hotel information
        tableModel = new DefaultTableModel();
        hotelTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(hotelTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for booking controls
        JPanel bookingPanel = new JPanel(new FlowLayout());
        bookButton = new JButton("Book Hotel");
        customerNameField = new JTextField(20);
        durationField = new JTextField(10);
        paidCheckBox = new JCheckBox("Paid");

        bookingPanel.add(new JLabel("Customer Name:"));
        bookingPanel.add(customerNameField);
        bookingPanel.add(new JLabel("Duration (days):"));
        bookingPanel.add(durationField);
        bookingPanel.add(paidCheckBox);
        bookingPanel.add(bookButton);

        add(bookingPanel, BorderLayout.SOUTH);
    }

    // Load data into the table
    private void loadData() {
        List<Hotel> hotelList = hotelDatabase.getHotelList();

        // Set column names
        tableModel.addColumn("Name");
        tableModel.addColumn("Available Room");
        tableModel.addColumn("Available Date");
        tableModel.addColumn("Rating");
        tableModel.addColumn("Price Per Day");

        // Populate the table with hotel data
        for (Hotel hotel : hotelList) {
            Object[] rowData = {
                    hotel.getName(),
                    hotel.getAvailableRoom(),
                    hotel.getAvailableDate(),
                    hotel.getRating(),
                    hotel.getPricePerDay()
            };
            tableModel.addRow(rowData);
        }
    }

    // Setup event listeners
    private void setupListeners() {
        // Listener for selecting a row in the table
        hotelTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = hotelTable.getSelectedRow();
            if (selectedRow >= 0) {
                String selectedHotelName = (String) hotelTable.getValueAt(selectedRow, 0);
                selectedHotel = hotelDatabase.getHotelByName(selectedHotelName);
            }
        });

        // Listener for the "Book Hotel" button
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedHotel != null) {
                    // Get booking information from user input
                    String customerName = customerNameField.getText();
                    int duration = Integer.parseInt(durationField.getText());
                    boolean paid = paidCheckBox.isSelected();

                    // Create a new booking object
                    Booking booking = new Booking(customerName, selectedHotel.getName(),
                            selectedHotel.getAvailableRoom(), selectedHotel.getAvailableDate(),
                            duration, paid);

                    // Insert the booking into the database
                    bookingDatabase.insertBooking(booking);

                    // Display a success message
                    JOptionPane.showMessageDialog(BookingGUI.this, "Booking successful!");

                    // Clear input fields
                    customerNameField.setText("");
                    durationField.setText("");
                    paidCheckBox.setSelected(false);
                } else {
                    JOptionPane.showMessageDialog(BookingGUI.this, "Please select a hotel first.");
                }
            }
        });
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingGUI bookingGUI = new BookingGUI();
            bookingGUI.setVisible(true);
        });
    }
}
