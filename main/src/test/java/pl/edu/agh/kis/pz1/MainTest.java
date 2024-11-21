package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**

 * A test class for {@link Main} class

 * <p>This class provides test cases to functions starting the application.</p>

 */

class MainTest {
    private ExcelHandler mockExcelHandler;
    private MyMap<Integer, Room> mockRooms;

    /**
     * Sets up mocks of ExcelHandler and Rooms map and captures output to outputStream array.
     */
    @BeforeEach
    void setUp() {
        mockExcelHandler = mock(ExcelHandler.class);
        mockRooms = mock(MyMap.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Tests if a CommandFactory is correctly created.
     * @throws IOException when occurs a problem with creating
     */

    @Test
    void testCreateCommandFactory() throws IOException {
        when(mockExcelHandler.getRoomsData()).thenReturn(mockRooms);
        try (var mockedExcelHandlerConstructor = Mockito.mockConstruction(ExcelHandler.class, (mock, context) -> {
            when(mock.getRoomsData()).thenReturn(mockRooms);
        })) {
            CommandFactory commandFactory = Main.createCommandFactory();
            // Assert
            assertNotNull(commandFactory);
            assertEquals(1, mockedExcelHandlerConstructor.constructed().size());
        }
    }
}