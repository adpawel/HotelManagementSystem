package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A parent class for all command types.
 * <p>This class provides shared properties and methods that are common across different commands
 * in the system, such as handling room numbers and interacting with the system's data.</p>
 *
 * <p>Concrete subclasses should override the {@link #execute()} method to define specific behavior for each command.</p>
 */
public class Command {
    /**
     * ExcelHandler field
     */
    protected ExcelHandler excelHandler;
    /**
     * MyMap containing rooms of the hotel
     */
    protected MyMap<Integer, Room> rooms;

    /**
     * Default constructor for the {@code Command} class.
     */
    public Command(){}

    /**
     * Constructs a new {@code Command} with the specified data handlers.
     *
     * @param excelHandler the handler used to interact with the Excel file for updating room information
     * @param r a map containing room numbers and their corresponding {@link Room} objects
     */
    public Command(ExcelHandler excelHandler, MyMap<Integer, Room> r) {
        this.excelHandler = excelHandler;
        this.rooms = r;
    }

    /**
     * Executes the command.
     * <p>This method is intended to be overridden by subclasses to define specific behavior for each command.</p>
     */
    public void execute(){
        // parent method
    }

    /**
     * Handles input of a room number from the user.
     * <p>This method prompts the user to enter a room number and validates it based on the provided command:</p>
     * <ul>
     *     <li>{@code "view"} - any valid room number can be selected.</li>
     *     <li>{@code "checkin"} - ensures the room is free before returning its number.</li>
     *     <li>{@code "checkout"} - ensures the room is occupied before returning its number.</li>
     * </ul>
     *
     * @param command the command type that determines the validation logic ({@code "view"}, {@code "checkin"}, {@code "checkout"})
     * @return the valid room number inputted by the user
     */
    public int readRoomNumber(String command) {
        Scanner scanner = new Scanner(System.in);
        int roomNumber;

        while (true) {
            System.out.print("Wybierz numer pokoju: ");
            try {
                roomNumber = scanner.nextInt();

                // Check if the room exists in the list of rooms
                if (!rooms.contains(roomNumber)) {
                    System.out.println("Pokój o numerze " + roomNumber + " nie istnieje. Spróbuj ponownie.");
                    continue;
                }

                // Handle different commands
                switch (command) {
                    case "view":
                        return roomNumber;

                    case "checkin":
                        if (rooms.get(roomNumber).isFree()) {
                            return roomNumber;
                        } else {
                            System.out.println("Pokój o numerze " + roomNumber + " jest już zajęty. Podaj inny numer pokoju.");
                        }
                        break;

                    case "checkout":
                        if (!rooms.get(roomNumber).isFree()) {
                            return roomNumber;
                        } else {
                            System.out.println("Pokój o numerze " + roomNumber + " jest wolny. Podaj inny numer pokoju.");
                        }
                        break;

                    default:
                        System.out.println("Nieznana komenda: " + command);
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Podaj poprawny numer pokoju (np. 503).");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}
