package pl.edu.agh.kis.pz1.commands;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * A test class for {@link PricesCommand} class
 * <p>Checks if output is correct and function prints all rooms' prices.</p>
 */
class PricesCommandTest {
    private MyMap<Integer, Room> roomsMock;
    private PricesCommand pricesCommand;
    private ByteArrayOutputStream outputStream;

    /**
     * Sets up an excelHandler and rooms mocks, initializes PricesCommand with these mocks and catches output
     * to outputStream.
     */
    @BeforeEach
    void setUp() {
        ExcelHandler excelHandlerMock = mock(ExcelHandler.class);
        roomsMock = mock(MyMap.class);

        pricesCommand = new PricesCommand(excelHandlerMock, roomsMock);

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Tests execute function basic functionality.
     * <p>
     *     <ul>
     *         <li>Sets up a simulated actions of MyMap roomsMock.</li>
     *         <li>Verify if the output sticks to expected output.</li>
     *     </ul>
     * </p>
     */
    @Test
    void execute_shouldPrintRoomDetails() {
        Room room1 = new Room(101, 100,"Standard", 1);
        Room room2 = new Room(102, 200,"Standard", 2);

        when(roomsMock.keys()).thenReturn(List.of(101, 102));
        when(roomsMock.get(101)).thenReturn(room1);
        when(roomsMock.get(102)).thenReturn(room2);

        pricesCommand.execute();

        String expectedOutput = """
                Nr    Typ     Cena
                101 - Standard: 100 PLN
                102 - Standard: 200 PLN
                Wprowadź komendę:
                """.trim();

        assertEquals(expectedOutput, new String(outputStream.toByteArray()).trim());

        verify(roomsMock, times(1)).keys();
        verify(roomsMock, times(1)).get(101);
        verify(roomsMock, times(1)).get(102);
    }

    /**
     * Test execute function when rooms map is empty
     * <p>Cheks if the output is a basic one.</p>
     */
    @Test
    void execute_shouldHandleEmptyRooms() {
        when(roomsMock.keys()).thenReturn(List.of());

        pricesCommand.execute();

        String expectedOutput =
                "Nr    Typ     Cena\n" +
                        "Wprowadź komendę:";

        assertEquals(expectedOutput, new String(outputStream.toByteArray()).trim());

        verify(roomsMock, times(1)).keys();
        verifyNoMoreInteractions(roomsMock);
    }
}