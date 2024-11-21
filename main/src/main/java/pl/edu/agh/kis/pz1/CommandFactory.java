package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.commands.*;

/**
 * A factory class responsible for creating different {@link Command} objects based on the command type.
 * <p>The class provides a method to instantiate specific command classes (such as {@link PricesCommand},
 * {@link ViewCommand}, etc.) according to the given string command type.</p>
 */
public class CommandFactory {
    /**
     * ExcelHandler field
     */
    protected ExcelHandler excelHandler;
    /**
     * ExcelHandler field
     */
    protected MyMap<Integer, Room> rooms;

    /**
     * Constructs a new {@code CommandFactory} with the provided {@link ExcelHandler} and a map of rooms.
     *
     * @param eh an instance of {@link ExcelHandler} to handle file operations
     * @param r a map containing room numbers as keys and {@link Room} objects as values
     */
    public CommandFactory(ExcelHandler eh, MyMap<Integer, Room> r){
        excelHandler = eh;
        rooms = r;
    }

    /**
     * Returns an appropriate {@link Command} object based on the provided command type.
     * <p>This method matches the given command type to the corresponding command class. If the command type
     * is invalid, it returns {@code null}.</p>
     *
     * @param commandType the type of the command (e.g., "prices", "view", "checkin")
     * @return a {@link Command} object corresponding to the provided command type or {@code null} if no match
     *         is found
     */
    public Command getCommand(String commandType){
        switch (commandType) {
            case "prices" -> {
                return new PricesCommand(excelHandler, rooms);
            }
            case "view" -> {
                return new ViewCommand(excelHandler, rooms);
            }
            case "checkin" -> {
                return new CheckinCommand(excelHandler, rooms);
            }
            case "checkout" -> {
                return new CheckoutCommand(excelHandler, rooms);
            }
            case "list" -> {
                return new ListCommand(excelHandler, rooms);
            }
            case "save" -> {
                return new SaveCommand(excelHandler, rooms);
            }
            case "exit" -> {
                return new ExitCommand(excelHandler, rooms);
            }
            case "help" -> {
                return new HelpCommand();
            }
            default -> {
                return null;
            }
        }
    }
}
