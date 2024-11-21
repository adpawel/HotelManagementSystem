package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

/**
 * A class representing the command to save the current state of the application.
 * <p>This command saves the current state of the rooms and their data into a file using
 * the {@link ExcelHandler}. This ensures that the changes made during the session are preserved.</p>
 */
public class SaveCommand extends Command{
    /**
     * Constructs a new {@code SaveCommand}.
     * <p>This constructor initializes the command with the required {@link ExcelHandler}
     * and a map of rooms.</p>
     *
     * @param excelHandler the handler used to save the state to a file
     * @param r a map containing room numbers and their corresponding {@link Room} objects
     */
    public SaveCommand(ExcelHandler excelHandler, MyMap<Integer, Room> r) {
        super(excelHandler, r);
    }

    /**
     * Executes the save command.
     * <p>This method invokes the {@link ExcelHandler#save()} method to save the current state of the rooms
     * and their data to a file. This ensures that any changes made to room data are written to the file system.</p>
     */
    @Override
    public void execute() {
        excelHandler.save();
    }
}
