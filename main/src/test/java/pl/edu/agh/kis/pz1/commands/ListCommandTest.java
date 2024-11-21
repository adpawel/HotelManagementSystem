package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.Guest;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A test class for {@link ListCommand} class.
 * <p>Checks if the output information about rooms are correct.</p>
 */
class ListCommandTest {
    private ExcelHandler excelHandler;
    private MyMap<Integer, Room> rooms;
    private ListCommand listCommand;
    private ByteArrayOutputStream outContent;

    /**
     * Sets up mock of excelHandler, new instances of rooms, listCommand and sets output to
     * ByteArrayOutputStream outContent.
     * <p>excelHandler mock is supposed to act like real ExcelHandler</p>
     */
    @BeforeEach
    void setUp() {
        excelHandler = Mockito.mock(ExcelHandler.class);
        rooms = new MyMap<>();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        listCommand = new ListCommand(excelHandler, rooms);
    }

    /**
     * Restores system out after each test.
     */
    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    /**
     * Tests if function print valid information with fixed input.
     * <p>
     *     <ul>
     *         <li>Creates 2 rooms and connect one list of Guests to each of the rooms.</li>
     *         <li>Sets start and end dates of stay.</li>
     *         <li>Verify if the output is correct.</li>
     *         <li>Normalize output to avoid separators errors.</li>
     *     </ul>
     * </p>
     */
    @Test
    void testExecuteWithRoomsAndGuests() {
        // Create mock rooms and guests
        Room room1 = new Room(101, 250, "Standard", 2);
        Room room2 = new Room(102, 300, "Deluxe", 3);

        Guest guest1 = new Guest("Jan Kowalski", "jan@example.com", "98098781987", "123456789");
        Guest guest2 = new Guest("Kacper Nowak", "nowak@example.com", "98098789176", "098765432");

        List<Guest> guestsRoom1 = new ArrayList<>();
        guestsRoom1.add(guest1);

        List<Guest> guestsRoom2 = new ArrayList<>();
        guestsRoom2.add(guest2);

        room1.setGuests(guestsRoom1);
        room2.setGuests(guestsRoom2);
        room1.setStartDate(LocalDate.of(2024, 11, 17));
        room1.setEndDate(LocalDate.of(2024, 11, 20));
        room2.setStartDate(LocalDate.of(2024, 11, 1));
        room2.setEndDate(LocalDate.of(2024, 11, 15));

        rooms.put(101, room1);
        rooms.put(102, room2);

        // Execute the command
        listCommand.execute();

        // Verify the output
        String expectedOutput = """
                Pokój nr 101, cena: 250, typ:Standard, 2-osobowy
                Dane gości:
                   Imię i nazwisko: Jan Kowalski, Adres email: jan@example.com, Nr telefonu: 123456789, Pesel/Numer identyfikacyjny: 98098781987
                    Pobyt (RRRR-MM-DD) od 2024-11-17 do 2024-11-20
                Pokój nr 102, cena: 300, typ:Deluxe, 3-osobowy
                Dane gości:
                   Imię i nazwisko: Kacper Nowak, Adres email: nowak@example.com, Nr telefonu: 098765432, Pesel/Numer identyfikacyjny: 98098789176
                    Pobyt (RRRR-MM-DD) od 2024-11-01 do 2024-11-15
                                
                Wprowadź komendę:
                """;
        String actualOutput = new String(outContent.toByteArray());

        // Normalize the line separators to \n
        String normalizedExpectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
        String normalizedActualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");
        assertEquals(normalizedExpectedOutput.trim(), normalizedActualOutput.trim());
    }

    /**
     * Checks if the function correctly handles case when there is no rooms.
     */
    @Test
    void testExecuteWithEmptyRooms() {
        // Execute the command with no rooms
        listCommand.execute();

        // Verify the output
        String expectedOutput = "Wprowadź komendę:";
        assertEquals(expectedOutput, outContent.toString().trim());
    }
}

