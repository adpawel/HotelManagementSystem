package pl.edu.agh.kis.pz1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;


/**
 * Represents a hotel room with various attributes such as room number, price, type, capacity, etc.
 * It also allows for managing guest reservations and calculating the room price for the stay based on the
 * number of days.
 */
public class Room {
    private int number;
    private int price;
    private String type;
    private int capacity;
    private List<Guest> guests;
    private LocalDate startDate;
    private LocalDate endDate;
    private String additionalInfo;

    /**
     * Constructs a new Room object with the specified attributes.
     *
     * @param number    the room number
     * @param price     the price per night for the room
     * @param type      the type of the room (e.g., single, double, suite)
     * @param capacity  the maximum number of guests the room can accommodate
     */
    public Room(int number, int price, String type, int capacity) {
        this.number = number;
        this.price = price;
        this.type = type;
        this.capacity = capacity;
        this.guests = new ArrayList<>();
    }

    /**
     * Getter for room number
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Setter number
     * @param number integer
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Getter for price
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Setter for price
     * @param price integer
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Getter for type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type
     * @param type String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for capacity
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Setter for capacity
     * @param capacity integer
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Getter for list of guests
     * @return guests
     */
    public List<Guest> getGuests() {
        return guests;
    }

    /**
     * Setter for guests list
     * @param guests List of guests
     */
    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    /**
     * Getter for start date of stay
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Setter for startDate
     * @param startDate LocalDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter for end date of stay
     * @return endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Setter for endDate
     * @param endDate LocalDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter for additional information
     * @return additionalInfo
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Setter for additional information
     * @param additionalInfo String
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * Clears the room's guest list, booking dates, and additional information.
     * This can be used to reset the room after a guest checks out.
     */
    public void clear(){
        guests.clear();
        startDate = null;
        endDate = null;
        additionalInfo = null;
    }

    /**
     * Checks if the room is free (i.e., if there are no guests currently booked).
     *
     * @return true if the room is free, false otherwise
     */
    public boolean isFree(){
        return guests.isEmpty();
    }

    /**
     * Calculates the price of the room based on the number of days the room has been booked.
     * If the room has been booked in the past, it calculates the price based on the start date.
     * If the room is free, it calculates the price for the current date.
     *
     * @return the calculated price of the room
     */
    public long calculatePrice(){
        LocalDate today = LocalDate.now();
        System.out.println(startDate.toString() + " -- " + today);
        return max(price, ChronoUnit.DAYS.between(startDate, today) * price);
    }

    /**
     * Returns a string representation of the room with its number, price, type, and capacity.
     *
     * @return a string representation of the room
     */
    @Override
    public String toString() {
        return "Pokój nr " + number + ", cena: " + price + ", typ:" + type + ", " + capacity + "-osobowy";
    }
}

