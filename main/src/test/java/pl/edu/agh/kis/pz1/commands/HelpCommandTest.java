package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A test class for {@link HelpCommand} class
 */
class HelpCommandTest {

    /**
     * Tests if the message of help is correct.
     */
    @Test
    void testExecute() {
        // Arrange: Capture System.out output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Act: Execute the HelpCommand
            HelpCommand helpCommand = new HelpCommand();
            helpCommand.execute();

            // Assert: Verify the printed help content
            String expectedOutput = """
                   Dostępne komendy:
                   - 'prices' listuje wszystkie pokoje oraz ich ceny za dobę
                   - 'view' wypisuje wszystkie informacje o wybranym pokoju
                   - 'checkin' pozwala na zameldowanie gości w wybranym pokoju
                   - 'checkout' pozwala na wymeldowanie gościa z pokoju
                   - 'list' listuje wszystkie pokoje wraz z informacją o zajętości
                   - 'save' zapisuje aktualny stan w pliku .xlsx
                   - 'exit' zamyka program
                   
                   Wprowadź komendę:""";
            assertEquals(expectedOutput, outContent.toString().trim());
        } finally {
            // Restore System.out
            System.setOut(originalOut);
        }
    }
}
