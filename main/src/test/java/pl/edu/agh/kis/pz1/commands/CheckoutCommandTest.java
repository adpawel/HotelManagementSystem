package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

/**
 * A test class for {@link CheckoutCommand} class
 * <p>This class provides test cases to functions that handle clearing data and displaying charge.</p>
 */
class CheckoutCommandTest {
    private MyMap<Integer, Room> roomMap;
    private ExcelHandler excelHandler;
    private Room room;

    private CheckoutCommand checkoutCommand;

    /**
     * Sets up mocks of ExcelHandler, Rooms map, room and creates an instance of CheckoutCommand with these mocks.
     * <p>
     *     <ul>
     *         <li>Ensures that after calling roomMap.get() we always get the mocked room.</li>
     *         <li>Ensures that after calling room.calculatePrice() we always get fixed price.</li>
     *     </ul>
     * </p>
     */
    @BeforeEach
    void setUp() {
        excelHandler = mock(ExcelHandler.class);
        roomMap = mock(MyMap.class);
        room = mock(Room.class);

        // Mock behavior for roomMap to return the mocked room
        when(roomMap.get(anyInt())).thenReturn(room);

        // Mock behavior for the room price
        when(room.calculatePrice()).thenReturn(200L); // For example, room price is 200 PLN
        checkoutCommand = new CheckoutCommand(excelHandler, roomMap);
    }

    /**
     * Tests if execute function calls correct methods and prints desired output.
     * <p>Uses spy command to simulate call of the readRoomNumber method</p>
     */
    @Test
    void testExecute() {
        // Mocking the readRoomNumber method
        CheckoutCommand spyCommand = Mockito.spy(checkoutCommand);
        doReturn(101).when(spyCommand).readRoomNumber("checkout");

        // Output handling
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Execute the command
        spyCommand.execute();

        String expectedOutput = """
                Należność: 200 PLN
                                
                Wprowadź komendę:
                """;
        String actualOutput = new String(outputStream.toByteArray());

        // Normalize the line separators to \n
        String normalizedExpectedOutput = expectedOutput.replace("\r\n", "\n").replace("\r", "\n");
        String normalizedActualOutput = actualOutput.replace("\r\n", "\n").replace("\r", "\n");

        Assertions.assertEquals(normalizedExpectedOutput.trim(), normalizedActualOutput.trim());
        // Verify that the correct room is selected and the price is printed
        verify(roomMap).get(101);  // Verify room lookup
        verify(room).calculatePrice();  // Verify price calculation
        verify(room).clear();  // Verify that the room is cleared
        verify(excelHandler).clearRoomInfo(room);  // Verify that ExcelHandler clears the room info

        System.setOut(System.out);
        System.setIn(System.in);
    }
}
