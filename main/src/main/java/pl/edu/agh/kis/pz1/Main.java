package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.commands.Command;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Main class of the MyHotel application.
 * This class is the entry point for the hotel management system, responsible for:
 *     <ul>
 *          <li>Creating and managing the command factory for generating and executing user commands.</li>
 *          <li>Handling user input and executing corresponding commands.</li>
 *          <li>Loading room data from an Excel file.</li>
 *     </ul>
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * The main method that starts the application.
     * <p>
     * This method:
     * 1. Displays a banner.
     * 2. Initializes a command factory for handling user commands.
     * 3. Continuously listens for user input and executes corresponding commands.
     * </p>
     *
     * @param args command line arguments (not used)
     * @throws IOException if an error occurs while reading the rooms data from the Excel file
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        drawBanner();
        CommandFactory cf = createCommandFactory();

        while(scanner.hasNext()) {
            String userCommand = scanner.nextLine();
            Command command = cf.getCommand(userCommand);
            if (command != null) {
                command.execute();
            } else {
                System.out.print("Niepoprawna komenda. Spróbuj ponownie: ");
            }
        }
    }

    /**
     * Displays a welcome banner for the application.
     */
    static void drawBanner() {
        System.out.println(" _________________________________________________");
        System.out.println("|                                                |");
        System.out.println("|                  MyHotel APP                   |");
        System.out.println("|________________________________________________|");
    }

    /**
     * Creates and initializes the {@link CommandFactory} instance.
     * <p>
     * This method:
     *  - Loads room data from the specified Excel file.
     *  - Returns a {@link CommandFactory} instance that can generate commands based on user input.
     * </p>
     *
     * @return a new instance of {@link CommandFactory} configured with room data from the Excel file.
     * @throws IOException if an error occurs while reading the rooms data from the Excel file.
     */
    static CommandFactory createCommandFactory() throws IOException{
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    System.out.print("-");
                    Thread.sleep(40);
                }
                System.out.print("\n\nWprowadź komendę('help' aby zobaczyć dostępne komendy): ");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.info(e.getMessage());
            }
        });
        thread.start();
        ExcelHandler eh = new ExcelHandler("data/rooms.xlsx");
        MyMap<Integer, Room> rooms = eh.getRoomsData();
        return new CommandFactory(eh, rooms);
    }
}
