package pl.edu.agh.kis.pz1.commands;

/**
 * A class representing service after command 'help'
 */
public class HelpCommand extends Command {

    /**
     * Executes the help command.
     * <p>This method prints a list of all available commands along with their descriptions
     * to the console. The information is intended to guide the user in navigating the application.</p>
     */
    @Override
    public void execute(){
        System.out.println("""
                   Dostępne komendy:
                   - 'prices' listuje wszystkie pokoje oraz ich ceny za dobę
                   - 'view' wypisuje wszystkie informacje o wybranym pokoju
                   - 'checkin' pozwala na zameldowanie gości w wybranym pokoju
                   - 'checkout' pozwala na wymeldowanie gościa z pokoju
                   - 'list' listuje wszystkie pokoje wraz z informacją o zajętości
                   - 'save' zapisuje aktualny stan w pliku .xlsx
                   - 'exit' zamyka program
                   
                   Wprowadź komendę: \s""");
    }
}
