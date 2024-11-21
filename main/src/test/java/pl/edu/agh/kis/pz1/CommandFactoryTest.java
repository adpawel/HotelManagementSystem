package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.commands.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * A test class for {@link CommandFactory} class
 * <p>This class provides test cases to function that returns proper Commands based on input command</p>
 */
class CommandFactoryTest {
    private CommandFactory commandFactory;

    /**
     * Sets up mocks of ExcelHandler and rooms in order to create an instance of CommandFactory
     */
    @BeforeEach
    void setUp(){
        // Mocking or creating test dependencies
        ExcelHandler mockExcelHandler = mock(ExcelHandler.class);
        MyMap<Integer, Room> mockRooms = new MyMap<>(); // Assuming MyMap is instantiated here
        commandFactory = new CommandFactory(mockExcelHandler, mockRooms);
    }

    /**
     * Tests if a function returns instance of correct class.
     */
    @Test
    void testGetCommand_PricesCommand() {
        Command command = commandFactory.getCommand("prices");
        assertNotNull(command);
        assertTrue(command instanceof PricesCommand);
    }

    /**
     * Tests if a function returns instance of correct class.
     */
    @Test
    void testGetCommand_ViewCommand() {
        Command command = commandFactory.getCommand("view");
        assertNotNull(command);
        assertTrue(command instanceof ViewCommand);
    }

    /**
     * Tests if a function returns instance of correct class.
     */
    @Test
    void testGetCommand_CheckinCommand() {
        Command command = commandFactory.getCommand("checkin");
        assertNotNull(command);
        assertTrue(command instanceof CheckinCommand);
    }

    /**
     * Tests if a function returns instance of correct class.
     */
    @Test
    void testGetCommand_CheckoutCommand() {
        Command command = commandFactory.getCommand("checkout");
        assertNotNull(command);
        assertTrue(command instanceof CheckoutCommand);
    }

    /**
     * Tests if a function returns instance of correct class.
     */
    @Test
    void testGetCommand_ListCommand() {
        Command command = commandFactory.getCommand("list");
        assertNotNull(command);
        assertTrue(command instanceof ListCommand);
    }

    /**
     * Tests if a function returns instance of correct class.
     */
    @Test
    void testGetCommand_SaveCommand() {
        Command command = commandFactory.getCommand("save");
        assertNotNull(command);
        assertTrue(command instanceof SaveCommand);
    }

    /**
     * Tests if a function returns instance of correct class.
     */
    @Test
    void testGetCommand_ExitCommand() {
        Command command = commandFactory.getCommand("exit");
        assertNotNull(command);
        assertTrue(command instanceof ExitCommand);
    }

    /**
     * Tests if a function returns instance of correct class.
     */
    @Test
    void testGetCommand_HelpCommand() {
        Command command = commandFactory.getCommand("help");
        assertNotNull(command);
        assertTrue(command instanceof HelpCommand);
    }

    /**
     * Tests if a function returns null in default case.
     */
    @Test
    void testGetCommand_InvalidCommand() {
        Command command = commandFactory.getCommand("invalid");
        assertNull(command, "CommandFactory should return null for unknown command types");
    }
}
