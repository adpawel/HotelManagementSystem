package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.Guest;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * A test class for {@link CheckinCommand} class
 * <p>This class provides test cases to functions that handles user input during checkin.</p>
 */
class CheckinCommandTest {
    private CheckinCommand checkinCommand;
    ExcelHandler excelHandlerMock;
    MyMap<Integer, Room> roomsMock;

    /**
     * Sets up mocks of ExcelHandler and Rooms map and creates a new instance of CheckinCommand with these mocks.
     */
    @BeforeEach
    void setUp() {
        // Mock ExcelHandler and Room
        excelHandlerMock = mock(ExcelHandler.class);
        roomsMock = mock(MyMap.class);

        checkinCommand = new CheckinCommand(excelHandlerMock, roomsMock);
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
    }


    /**
     * Tests execute function with fixed input
     * <p>
     *     <ul>
     *         <li>Sets input to InputStream and catches output to outContent</li>
     *         <li>Creates spyCommand with simulated actions needed to run execute function.</li>
     *         <li>Creates roomMock and uses Mockito.when() to arrange actions</li>
     *         <li>Sets expected output and comapres it with real normalized output.</li>
     *         <li>Verifies invocations of proper methods</li>
     *         <li>Restores System.out</li>
     *     </ul>
     * </p>
     */
    @Test
    void testExecute(){
        String input = "Janek\nDodatkowe informacje";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Guest guestMock = mock(Guest.class);
        LocalDate startDate = LocalDate.of(2024, 11, 17);
        CheckinCommand spyCommand = Mockito.spy(checkinCommand);
        // Override the readRoomNumber method to return the room number 102
        doReturn(102).when(spyCommand).readRoomNumber("checkin");
        doReturn(guestMock).when(spyCommand).readMainGuestData();
        doReturn(startDate).when(spyCommand).readStartDate();
        doReturn(5).when(spyCommand).readDuration();

        Room roomMock = mock(Room.class);
        when(roomsMock.get(102)).thenReturn(roomMock);
        when(roomsMock.get(102).getCapacity()).thenReturn(2);

        spyCommand.execute();

        String expectedOutput = """
                    Podaj dane kolejnego gościa:
                    Imię i nazwisko:
                    Jeśli masz jakieś dodatkowe informacje napisz je tutaj:
                    
                    Wprowadź komendę:""";
        String actualOutput = new String(outContent.toByteArray());

        // Normalize the line separators to \n
        String normalizedExpectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
        String normalizedActualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

        assertEquals(normalizedExpectedOutput.trim(), normalizedActualOutput.trim());
        verify(roomMock, times(1)).setStartDate(startDate);
        verify(roomMock, times(1)).setEndDate(startDate.plusDays(5));
        verify(roomMock, times(1)).setAdditionalInfo("Dodatkowe informacje");
    }

    /**
     * Tests if readStartDate function correctly handles empty string in the input
     * <p>Function is expected to set startDate as today's date in this case.</p>
     */
    @Test
    void testReadStartDate_emptyInput(){
        String input = "\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        LocalDate result = checkinCommand.readStartDate();
        assertEquals(LocalDate.now(), result);
    }

    /**
     * Tests if readStartDate function properly reads valid start date.
     * <p>Sets up input by passing it in InputStream and compare result of method readStartDate with expectations</p>
     */
    @Test
    void testReadStartDate_validDate() {
        // Simulate input for the start date
        String input = "2024-12-01\n"; // valid date input
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Call the method
        LocalDate result = checkinCommand.readStartDate();

        // Assert that the date is correct
        assertEquals(LocalDate.parse("2024-12-01"), result);
    }
    /**
     * Tests if readStartDate function properly reads valid start date.
     * <p>
     *     <ul>
     *         <li>Sets up input by passing it in InputStream</li>
     *         <li>Expects function not to claim invalid input but the second one which is valid.</li>
     *         <li>Compares communicate about the error.</li>
     *         <li>Compares result of method readStartDate with expectations</li>
     *     </ul>
     * </p>
     */
    @Test
    void testReadStartDate_invalidDate() {
        // Simulate input for an invalid date first and then a valid date
        String input = "invalid-date\n2024-12-01\n"; // invalid followed by a valid date
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Call the method
        LocalDate result = checkinCommand.readStartDate();

        String expectedOutput = "Podaj datę zameldowania (w formacie RRRR-MM-DD):Błędny format daty. Spróbuj ponownie.";
        assertEquals(expectedOutput, outContent.toString().trim());

        // Assert that the result is the valid date
        assertEquals(LocalDate.parse("2024-12-01"), result);
    }

    /**
     * Tests process of reading data of main guest which is extended compared to usual guest.
     * <p>
     *     <ul>
     *         <li>Sets up input in InputStream</li>
     *         <li>Call the method</li>
     *         <li>Compare received data with expectations</li>
     *     </ul>
     * </p>
     */
    @Test
    void testReadMainGuestData() {
        String input = "John Doe\n12345678901\njohn.doe@example.com\n123456789\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Guest mainGuest = checkinCommand.readMainGuestData();

        assertEquals("John Doe", mainGuest.getName());
        assertEquals("123456789", mainGuest.getIdNumber());
        assertEquals("john.doe@example.com", mainGuest.getEmailAddress());
        assertEquals("12345678901", mainGuest.getPhoneNumber());
    }

    /**
     * Tests if a function correctly read a valid number of days.
     */
    @Test
    void testReadDuration_validInput() {
        // Simulate valid input for duration
        String input = "5\n"; // valid duration input
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Call the method
        int duration = checkinCommand.readDuration();

        // Assert the result
        assertEquals(5, duration);
    }

    /**
     * Tests if a function correctly avoid invalid input and read valid number
     * <p>
     *     Sets up duration in InputStream calls method and verify result with fixed duration.
     * </p>
     */
    @Test
    void testReadDuration_invalidInput() {
        // Simulate invalid input for duration and then valid input
        String input = "invalid\n5\n"; // invalid input followed by valid
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Call the method
        int duration = checkinCommand.readDuration();

        // Assert the result
        assertEquals(5, duration);
    }
}