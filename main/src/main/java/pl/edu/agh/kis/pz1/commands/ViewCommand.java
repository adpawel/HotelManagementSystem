package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.Guest;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

/**
 * A class representing the service for the 'view' command.
 * <p>This command retrieves and displays detailed information about a chosen room, including
 * whether it is occupied by guests or available for new guests.</p>
 */
public class ViewCommand extends Command{

    /**
     * Constructs a new {@code ViewCommand}.
     * <p>This constructor initializes the command with the required {@link ExcelHandler}
     * and a map of rooms.</p>
     *
     * @param excelHandler the handler used to interact with the file system
     * @param r a map containing room numbers and their corresponding {@link Room} objects
     */
    public ViewCommand(ExcelHandler excelHandler, MyMap<Integer, Room> r) {
        super(excelHandler, r);
    }

    /**
     * Executes the 'view' command.
     * <p>This method displays detailed information about the chosen room. It first asks the user to input
     * the room number, retrieves the room data, and prints the information about the room. If the room is occupied,
     * it lists the guests staying in the room; otherwise, it indicates that the room is available.</p>
     *
     * @see Room
     * @see Guest
     */
    @Override
    public void execute() {
        int roomNumber = readRoomNumber("view");
        Room room = rooms.get(roomNumber);
        System.out.println(room.toString());

        // Prints either guests or information that the chosen room is empty
        if(!room.getGuests().isEmpty()){
            System.out.println("Goście w wybranym pokoju:");
            int i = 1;
            for (Guest g : room.getGuests()) {
                System.out.println(i + ". " + g.toString());
                i++;
            }
        }
        else{
            System.out.println("Pokój jest wolny.");
        }
        System.out.print("\nWprowadź komendę: ");
    }
}
