package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

/**
 * A class representing the command to handle application termination.
 * <p>This command ensures that any necessary cleanup is performed, such as closing
 * the Excel file via {@link ExcelHandler}, before exiting the application.</p>
 *
 * <p>By default, the application exits with status code 0, but an alternative
 * {@link Runnable} exit action can be provided for testing or customization.</p>
 */
public class ExitCommand extends Command {
    private final Runnable exitAction;

    /**
     * Constructs a new {@code ExitCommand} with default exit behavior.
     * <p>The default behavior includes closing the Excel file and terminating the application
     * with status code 0.</p>
     *
     * @param excelHandler the handler used to manage the Excel file
     * @param r a map containing room numbers and their corresponding {@link Room} objects
     */
    public ExitCommand(ExcelHandler excelHandler, MyMap<Integer, Room> r) {
        this(excelHandler, r, () -> System.exit(0));
    }

    /**
     * Constructs a new {@code ExitCommand} with a customizable exit action.
     * <p>This constructor allows you to provide an alternative {@link Runnable} exit action,
     * useful for testing or non-standard termination processes.</p>
     *
     * @param excelHandler the handler used to manage the Excel file
     * @param r a map containing room numbers and their corresponding {@link Room} objects
     * @param exitAction the action to execute during application termination
     */
    public ExitCommand(ExcelHandler excelHandler, MyMap<Integer, Room> r, Runnable exitAction) {
        super(excelHandler, r);
        this.exitAction = exitAction;
    }

    /**
     * Executes the exit command.
     * <p>This method performs the following actions in order:</p>
     * <ul>
     *     <li>Closes the Excel file using {@link ExcelHandler#close()} to ensure data integrity.</li>
     *     <li>Executes the provided {@link Runnable} exit action (e.g., terminating the application).</li>
     * </ul>
     */
    @Override
    public void execute() {
        excelHandler.close();
        exitAction.run();
    }
}