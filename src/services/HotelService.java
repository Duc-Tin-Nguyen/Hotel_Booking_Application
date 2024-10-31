package services;

import datamodel.Hotel;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HotelService {
    private List<Hotel> hotelList = new ArrayList<>();

    public void readHotelsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header row

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0].trim();  // Changed from id
                String available_room = data[1].trim();  // Changed from name
                String available_date = data[2].trim();  // New field
                double rating = Double.parseDouble(data[3].trim());
                double pricePerDay = Double.parseDouble(data[4].trim());

                Hotel hotel = new Hotel(name, available_room, available_date, rating, pricePerDay);
                hotelList.add(hotel);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void writeHotelsToCSV(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Hotel hotel : hotelList) {
                bw.write(String.format("%s,%s,%s,%.2f,%.2f%n",
                        hotel.getName(), hotel.getAvailableRoom(), hotel.getAvailableDate(),
                        hotel.getRating(), hotel.getPricePerDay()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Hotel> getHotelList() {
        return hotelList;
    }

    public void displayHighRatingLowestPriceHotels() {
        List<Hotel> filteredHotels = new ArrayList<>(hotelList);
        filteredHotels.removeIf(hotel -> hotel.getRating() < 4.5 || hotel.getPricePerDay() > 100.0);

        System.out.println("Hotels with high ratings (>= 4.5) and lowest price (<= 100€/day):");
        filteredHotels.sort(Comparator.comparingDouble(Hotel::getPricePerDay));
        for (Hotel hotel : filteredHotels) {
            System.out.println(hotel);
        }
    }
}
