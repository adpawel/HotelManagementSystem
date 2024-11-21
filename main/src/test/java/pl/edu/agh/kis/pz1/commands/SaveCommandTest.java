package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

import static org.mockito.Mockito.*;

/**
 * A test class for {@link SaveCommand} class
 * <p>Tests if the saveCommand correctly call save method of ExcelHandler</p>
 */
class SaveCommandTest {

    private ExcelHandler mockExcelHandler;
    private MyMap<Integer, Room> mockRoomMap;
    private SaveCommand saveCommand;

    /**
     * Sets up mocks of ExcelHandler, roomsMap and creates an instance of SaveCommand with these mocks.
     */
    @BeforeEach
    void setUp() {
        mockExcelHandler = mock(ExcelHandler.class);
        mockRoomMap = mock(MyMap.class);

        saveCommand = new SaveCommand(mockExcelHandler, mockRoomMap);
    }

    /**
     * Verify if the method save of ExcelHandler was called and exactly once.
     */
    @Test
    void testExecuteCallsSave() {
        saveCommand.execute();

        // Verify that ExcelHandler's save method was called exactly once
        verify(mockExcelHandler, times(1)).save();

        // Verify no unnecessary interactions with other objects
        verifyNoInteractions(mockRoomMap);
    }
}
