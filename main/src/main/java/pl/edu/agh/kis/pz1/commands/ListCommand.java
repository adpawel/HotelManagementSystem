package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.Guest;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;


/**
 * A class representing the command to display information about all rooms.
 * <p>This command iterates through all the rooms in the hotel and prints their details,
 * including room information, guest data (if available), and the stay duration.</p>
 *
 * @see Command
 */
public class ListCommand extends Command{
    /**
     * Constructs a new {@code ListCommand}.
     * <p>This constructor initializes the command with the required {@link ExcelHandler}
     * and a map of rooms.</p>
     *
     * @param excelHandler the handler used to manage the Excel file
     * @param r a map containing room numbers and their corresponding {@link Room} objects
     */
    public ListCommand(ExcelHandler excelHandler, MyMap<Integer, Room> r) {
        super(excelHandler, r);
    }

    /**
     * Executes the list command.
     * <p>This method iterates through all rooms and prints detailed information about each one.
     * If a room has guests, their data is also printed, followed by the duration of their stay.</p>
     */
    @Override
    public void execute() {
        for(int k : rooms.keys()){
            Room room = rooms.get(k);
            System.out.println(room.toString());
            if(room.getGuests() != null && !room.getGuests().isEmpty()) {
                System.out.println("Dane gości:");
                for (Guest g : room.getGuests()) {
                    System.out.println( "   " + g.toString());
                }
                System.out.println("    Pobyt (RRRR-MM-DD) od " + room.getStartDate() + " do " + room.getEndDate());
            }
        }
        System.out.print("\nWprowadź komendę: ");
    }
}
