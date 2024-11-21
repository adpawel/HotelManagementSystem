package pl.edu.agh.kis.pz1.commands;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * A test class for {@link Command} class
 * <p>This class provides test cases to functions with either valid or invalid input.</p>
 */
class CommandTest {
    private Command command;
    private MyMap<Integer, Room> rooms;
    private Room mockRoom;

    /**
     * Sets up rooms mock, room mock and instance of Command
     * <p>Uses mocks to simulate behaviour of real classes.</p>
     */
    @BeforeEach
    void setUp() {
        rooms = Mockito.mock(MyMap.class);
        mockRoom = Mockito.mock(Room.class);
        command = new Command(null, rooms);
    }

    /**
     * Sets input stream to system
     * @param input of type string that we want to inject.
     */
    private void provideInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    /**
     * Tests readRoomNumber() function
     * <p>Checks if readRoomNumber function with 'view' parameter and valid integer input read number correctly</p>
     */
    @Test
    void testReadRoomNumber_viewValidRoom() {
        provideInput("101\n");
        when(rooms.contains(101)).thenReturn(true);

        int roomNumber = command.readRoomNumber("view");

        assertEquals(101, roomNumber);
        verify(rooms).contains(101);
    }

    /**
     * Tests readRoomNumber() function
     * <p>Checks if readRoomNumber function with 'checkin' parameter and valid integer room input
     * read number correctly</p>
     */
    @Test
    void testReadRoomNumber_checkinValidFreeRoom() {
        provideInput("202\n");
        when(rooms.contains(202)).thenReturn(true);
        when(rooms.get(202)).thenReturn(mockRoom);
        when(mockRoom.isFree()).thenReturn(true);

        int roomNumber = command.readRoomNumber("checkin");

        assertEquals(202, roomNumber);
        verify(rooms).contains(202);
        verify(rooms).get(202);
        verify(mockRoom).isFree();
    }

    /**
     * Tests readRoomNumber() function
     * <p>Checks if readRoomNumber function with 'checkin' parameter and valid integer input of occupied room
     * returns information that the room is not empty.</p>
     */
    @Test
    void testReadRoomNumber_checkinOccupiedRoom() {
        // Simulate input: first try room 303 (occupied), then try room 404 (free)
        provideInput("303\n404\n");

        when(rooms.contains(303)).thenReturn(true);
        when(rooms.contains(404)).thenReturn(true);
        when(rooms.get(303)).thenReturn(mockRoom);
        when(rooms.get(404)).thenReturn(mockRoom);
        when(mockRoom.isFree()).thenReturn(false).thenReturn(true);

        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int roomNumber = command.readRoomNumber("checkin");

        assertEquals(404, roomNumber); // Ensure we end with the correct room number

        // Verify output message for occupied room
        String consoleOutput = new String(outContent.toByteArray()).trim();
        assertTrue(consoleOutput.contains("Pokój o numerze 303 jest już zajęty. Podaj inny numer pokoju."));

        // Verify interactions
        verify(rooms, times(2)).contains(anyInt());
        verify(rooms, times(2)).get(anyInt());
        verify(mockRoom, times(2)).isFree();
    }

    /**
     * Tests readRoomNumber() function
     * <p>Checks if readRoomNumber function with 'checkout' parameter and valid integer input of occupied room
     * read number correctly</p>
     */
    @Test
    void testReadRoomNumber_checkoutOccupiedRoom() {
        provideInput("505\n");
        when(rooms.contains(505)).thenReturn(true);
        when(rooms.get(505)).thenReturn(mockRoom);
        when(mockRoom.isFree()).thenReturn(false);

        int roomNumber = command.readRoomNumber("checkout");

        assertEquals(505, roomNumber);
        verify(rooms).contains(505);
        verify(rooms).get(505);
        verify(mockRoom).isFree();
    }

    /**
     * Tests readRoomNumber() function
     * <p>Checks if readRoomNumber function with 'checkout' parameter and valid integer input of free room
     * returns info that the room is empty and there is no guest to check out.</p>
     */
    @Test
    void testReadRoomNumber_checkoutFreeRoom() {
        // Simulate input: try free room 606 first, then valid occupied room 707
        provideInput("606\n707\n");

        // Mock room behavior
        when(rooms.contains(606)).thenReturn(true);
        when(rooms.contains(707)).thenReturn(true);
        when(rooms.get(606)).thenReturn(mockRoom);
        when(rooms.get(707)).thenReturn(mockRoom);
        when(mockRoom.isFree()).thenReturn(true).thenReturn(false); // 707 is occupied

        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int roomNumber = command.readRoomNumber("checkout");

        assertEquals(707, roomNumber); // Ensure 707 is selected

        // Verify console output for free room message
        String consoleOutput = new String(outContent.toByteArray()).trim();
        assertTrue(consoleOutput.contains("Pokój o numerze 606 jest wolny. Podaj inny numer pokoju."));

        // Verify interactions
        verify(rooms, times(2)).contains(anyInt()); // Called twice for 606 and 707
        verify(rooms, times(2)).get(anyInt());
        verify(mockRoom, times(2)).isFree();
    }

    /**
     * Tests readRoomNumber() function
     * <p>Checks if readRoomNumber function with any parameter and invalid input handle it correctly</p>
     * <ul>
     *     <li>First input 'abc' should not be read.</li>
     *     <li>Verify that function read first valid input which is 101</li>
     * </ul>
     */
    @Test
    void testReadRoomNumber_invalidInput() {
        provideInput("abc\n101\n");
        when(rooms.contains(101)).thenReturn(true);

        int roomNumber = command.readRoomNumber("view");

        assertEquals(101, roomNumber);
        verify(rooms).contains(101);
    }

    /**
     * Tests readRoomNumber() function
     * <p>Checks if readRoomNumber function with any parameter and invalid integer input handle it correctly</p>
     * <ul>
     *     <li>First input 999 is an integer but does not exist in a hotel thus should not be read.</li>
     *     <li>Verify that function read first valid input which is 202</li>
     * </ul>
     */
    @Test
    void testReadRoomNumber_roomDoesNotExist() {
        provideInput("999\n202\n");
        when(rooms.contains(999)).thenReturn(false);
        when(rooms.contains(202)).thenReturn(true);

        int roomNumber = command.readRoomNumber("view");

        assertEquals(202, roomNumber);
        verify(rooms, times(2)).contains(anyInt());
    }
}
