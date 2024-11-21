package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

/**
 * A class representing the command to display the prices of all rooms.
 * <p>This command prints the prices of all rooms in the hotel, showing each room's number, type, and price.</p>
 */
public class PricesCommand extends Command{

    /**
     * Constructs a new {@code PricesCommand}.
     * <p>This constructor initializes the command with the required {@link ExcelHandler}
     * and a map of rooms.</p>
     *
     * @param excelHandler the handler used to manage the Excel file
     * @param r a map containing room numbers and their corresponding {@link Room} objects
     */
    public PricesCommand(ExcelHandler excelHandler, MyMap<Integer, Room> r) {
        super(excelHandler, r);
    }

    /**
     * Executes the prices command.
     * <p>This method iterates through all rooms and prints their room number, type, and price per night.</p>
     * <p>The price is displayed in PLN.</p>
     */
    @Override
    public void execute() {
        System.out.print("Nr    Typ     Cena\n");
        for(Integer roomNr : rooms.keys()){
            Room room = rooms.get(roomNr);
            System.out.print(roomNr + " - "+ room.getType() + ": " + room.getPrice() + " PLN\n");
        }
        System.out.print("Wprowadź komendę: ");
    }
}
