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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * A test class for {@link ViewCommand} class
 * <p>This class checks if class provides correct output about a specific room.</p>
 */
class ViewCommandTest {

    private ExcelHandler mockExcelHandler;
    private MyMap<Integer, Room> mockRooms;
    private ViewCommand viewCommand;
    private ByteArrayOutputStream outputStream;

    /**
     * Sets up mocks of ExcelHandler, rooms Map, creates an instance of ViewCommand with mocks and catches
     * the output to outputStream array.
     */
    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockExcelHandler = mock(ExcelHandler.class);
        mockRooms = mock(MyMap.class);

        // Create the ViewCommand with mocked dependencies
        viewCommand = new ViewCommand(mockExcelHandler, mockRooms);

        // Redirect System.out to capture console output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    /**
     * Tests if execute function returns correct info about a room.
     * <p>
     *     <ul>
     *         <li>Simulate actions of method of mockRooms Map.</li>
     *         <li>Uses spy command to make method readRoomNumber act in a way that we want.</li>
     *         <li>Normalize separators to avoid errors.</li>
     *         <li>Verify output with expected one.</li>
     *     </ul>
     * </p>
     */
    @Test
    void testExecuteWithGuests() {
        // Prepare a mocked Room with guests
        Room mockRoom = mock(Room.class);
        Guest guest1 = mock(Guest.class);
        Guest guest2 = mock(Guest.class);

        // Mock the room behavior
        when(mockRooms.get(101)).thenReturn(mockRoom);
        when(mockRoom.toString()).thenReturn("Room 101 details");
        when(mockRoom.getGuests()).thenReturn(List.of(guest1, guest2));
        when(guest1.toString()).thenReturn("John Doe");
        when(guest2.toString()).thenReturn("Jane Smith");

        // Create a spy for ViewCommand
        ViewCommand spyCommand = Mockito.spy(viewCommand);

        // Override the readRoomNumber method to return a specific room number
        doReturn(101).when(spyCommand).readRoomNumber("view");

        // Execute the command
        spyCommand.execute();

        // Normalize line separators
        String output = outputStream.toString().trim().replace("\r\n", "\n").replace("\r", "\n");
        String expectedOutput = """
            Room 101 details
            Goście w wybranym pokoju:
            1. John Doe
            2. Jane Smith
            \nWprowadź komendę:""".trim().replace("\r\n", "\n").replace("\r", "\n");

        // Assert output
        assertEquals(expectedOutput, output);

        // Verify interactions
        verify(mockRooms, times(1)).get(101);
        verify(spyCommand, times(1)).readRoomNumber("view");
    }

    /**
     * Tests if execute function correctly handles viewCommand when room has no guests.
     * <p>Mocks map rooms behaviour and verify if the output is proper.</p>
     */
    @Test
    void testExecuteWithNoGuests() {
        // Prepare a mocked Room with no guests
        Room mockRoom = mock(Room.class);

        // Mock behavior of the room
        when(mockRooms.get(102)).thenReturn(mockRoom);
        when(mockRoom.toString()).thenReturn("Room 102 details");
        when(mockRoom.getGuests()).thenReturn(List.of());

        // Create a spy for ViewCommand
        ViewCommand spyCommand = Mockito.spy(viewCommand);

        // Override the readRoomNumber method to return the room number 102
        doReturn(102).when(spyCommand).readRoomNumber("view");

        // Execute the spy command
        spyCommand.execute();

        // Normalize line separators for cross-platform compatibility
        String output = outputStream.toString().trim().replace("\r\n", "\n").replace("\r", "\n");
        String expectedOutput = """
            Room 102 details
            Pokój jest wolny.
            \nWprowadź komendę:""".trim().replace("\r\n", "\n").replace("\r", "\n");

        // Assert that the output matches the expected result
        assertEquals(expectedOutput, output);

        // Verify interactions
        verify(mockRooms, times(1)).get(102);
        verify(spyCommand, times(1)).readRoomNumber("view");
    }
}
