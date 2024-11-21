package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertTrue;

/**
 * A test class for {@link ExitCommand} class
 */
class ExitCommandTest {

    private ExcelHandler excelHandler;
    private MyMap<Integer, Room> rooms;
    private ExitCommand exitCommand;
    private boolean isExited;

    /**
     * Sets up excelHandler mock, new instance of MyMap rooms and new exitCommand
     * <p>Uses mock to simulate behaviour of real class.</p>
     * <p>Uses special constructor to change boolean isExited to false during closing the application</p>
     */
    @BeforeEach
    void setUp(){
        excelHandler = mock(ExcelHandler.class);
        rooms = new MyMap<>();
        isExited = false;
        exitCommand = new ExitCommand(excelHandler, rooms, () -> isExited = true);
    }
    /**
     * Tests execute function
     * <p>Executes the function from of exitCommand and check if the isExited is true
     * (means the application closed correctly)</p>
     */
    @Test
    void testExecute() {
        exitCommand.execute();

        // Verify that the excelHandler's close method is called
        verify(excelHandler).close();

        // Verify that the exit action has been triggered
        assertTrue(isExited, "System exit action was not triggered.");
    }
}