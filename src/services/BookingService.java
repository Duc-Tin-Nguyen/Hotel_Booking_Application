package services;

import datamodel.Booking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookingService {
    private List<Booking> bookingList = new ArrayList<>();

    public void readBookingsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header row

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0].trim();
                String hotelName = data[1].trim();
                String room = data[2].trim();
                String bookingDate = data[3].trim();
                int duration = Integer.parseInt(data[4].trim());
                boolean paid = Boolean.parseBoolean(data[5].trim());

                Booking booking = new Booking(name, hotelName, room, bookingDate, duration, paid);
                bookingList.add(booking);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void writeBookingsToCSV(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Booking booking : bookingList) {
                bw.write(String.format("%s,%s,%s,%s,%d,%b%n",
                        booking.getName(), booking.getHotelName(), booking.getRoom(),
                        booking.getBookingDate(), booking.getDuration(), booking.isPaid()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertBooking(Booking booking) {
        bookingList.add(booking);
    }

    public void displayHighCostDestinations() {
        // Implement your logic to display high-cost destinations
    }

    public void displayPaidCustomerNamesInAscendingOrder() {
        List<Booking> paidBookings = new ArrayList<>();
        for (Booking booking : bookingList) {
            if (booking.isPaid()) {
                paidBookings.add(booking);
            }
        }

        paidBookings.sort(Comparator.comparing(Booking::getName));

        System.out.println("Customer names for paid bookings in ascending order:");
        for (Booking booking : paidBookings) {
            System.out.println(booking.getName());
        }
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void bookHotel(String selectedHotelName, String customerName, String bookingDate, int duration,
            boolean paid) {
    }
}
