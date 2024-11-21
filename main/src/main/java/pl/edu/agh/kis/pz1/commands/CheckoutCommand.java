package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

/**
 * A class representing service after command 'checkout'
 * This class reads a room number and
 * charges a fee based on the price and duration of guests' stay
 *
 * <p>Extends the {@link Command}.</p>
 */
public class CheckoutCommand extends Command{


    /**
     * Constructs a new CheckoutCommand.
     *
     * @param excelHandler the handler for updating room information in Excel
     * @param r a map of room numbers to room objects
     */
    public CheckoutCommand(ExcelHandler excelHandler, MyMap<Integer, Room> r) {
        super(excelHandler, r);
    }

    /**
     * Executes the check-out process for a guest.
     * <p>This method performs the following actions:</p>
     * <ul>
     *     <li>Prompts the user to input the room number for check-out.</li>
     *     <li>Retrieves the room information and calculates the total due amount for the stay.</li>
     *     <li>Prints the calculated amount to the console.</li>
     *     <li>Clears the room's data, making it available for future reservations.</li>
     *     <li>Updates the room's status in the system via {@link ExcelHandler}.</li>
     * </ul>
     * <p>The method interacts with the user through console input and output.</p>
     */
    @Override
    public void execute() {
        int roomNr = readRoomNumber("checkout");
        Room room = rooms.get(roomNr);

        System.out.println("Należność: " + room.calculatePrice() + " PLN");

        room.clear();
        excelHandler.clearRoomInfo(room);

        System.out.print("\nWprowadź komendę: ");
    }
}
