package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.ExcelHandler;
import pl.edu.agh.kis.pz1.Guest;
import pl.edu.agh.kis.pz1.MyMap;
import pl.edu.agh.kis.pz1.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A class representing service after command 'checkin'
 * This class gathers necessary guest information, assigns a room, and updates
 * the room's status using an ExcelHandler.
 *
 * <p>Extends the {@link Command}.</p>
 */
public class CheckinCommand extends Command{


    /**
     * Constructs a new CheckinCommand.
     *
     * @param excelHandler the handler for updating room information in Excel
     * @param r a map of room numbers to room objects
     */
    public CheckinCommand(ExcelHandler excelHandler, MyMap<Integer, Room> r) {
        super(excelHandler, r);
    }

    /**
     * Handles check-in process by:
     * <ul>
     *     <li>Reading room number and assigning a room</li>
     *     <li>Collecting main and additional guests data</li>
     *     <li>Setting the stay duration and additional details</li>
     *     <li>Updating room information in the system</li>
     * </ul>
     * <p>The method interacts with the user through console input to gather necessary information.</p>
     */
    @Override
    public void execute() {
        // create guest list
        List<Guest> guests = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // get proper room
        int roomNr = readRoomNumber("checkin");
        Room room = rooms.get(roomNr);
        int roomCapacity = rooms.get(roomNr).getCapacity();

        Guest mainGuest = readMainGuestData();
        guests.add(mainGuest);

        // get data of other guests
        roomCapacity--;
        while(roomCapacity > 0){
            System.out.println("Podaj dane kolejnego gościa:");
            System.out.println("Imię i nazwisko:");
            String guestName = scanner.nextLine();
            guests.add(new Guest(guestName));
            roomCapacity--;
        }

        // get date of the start of stay
        LocalDate startDate = readStartDate();
        int duration = readDuration();
        LocalDate endDate = startDate.plusDays(duration);

        // get additional info from guest
        System.out.println("Jeśli masz jakieś dodatkowe informacje napisz je tutaj:");
        String additionalInfo = scanner.nextLine();

        // set new rooms' data
        room.setGuests(guests);
        room.setStartDate(startDate);
        room.setEndDate(endDate);
        room.setAdditionalInfo(additionalInfo);

        // update
        excelHandler.updateRoomInfo(room);
        System.out.print("\nWprowadź komendę: ");
    }


    /**
     * Handles input of the main guest's data including:
     * <ul>
     *     <li>Full name</li>
     *     <li>Identification number</li>
     *     <li>Email address</li>
     *     <li>Phone number</li>
     * </ul>
     *
     * @return a new instance of {@link Guest} with the collected data
     */
    public Guest readMainGuestData(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj dane gościa głównego: ");
        System.out.println("Imię i nazwisko: ");
        String mainGuestName = scanner.nextLine();
        System.out.println("Nr PESEL / Nr identyfikacyjny: ");
        String mainGuestID = scanner.nextLine();

        System.out.println("Adres email: ");
        String emailPattern = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        String mainGuestEmail;
        while(true){
            mainGuestEmail = scanner.nextLine();
            if (Pattern.matches(emailPattern, mainGuestEmail)){
                break;
            }
            else {
                System.out.println("Błędny format adresu email. Spróbuj ponownie");
            }
        }

        System.out.println("Nr telefonu: ");
        String phonePattern = "(\\+\\d{2})?\\d{3}[- ]?\\d{3}[- ]?\\d{3}";
        String mainGuestPhoneNr;
        while(true){
            mainGuestPhoneNr = scanner.nextLine();
            if (Pattern.matches(phonePattern, mainGuestPhoneNr)){
                break;
            }
            else {
                System.out.println("Błędny format numeru telefonu. Spróbuj ponownie");
            }
        }
        return new Guest(mainGuestName, mainGuestEmail, mainGuestPhoneNr, mainGuestID);
    }

    /**
     * Reads the start date of the guest's stay. The user can enter the date in
     * the format "YYYY-MM-DD" or leave it blank to use the current date.
     *
     * @return the start date as an instance of {@link LocalDate}
     */
    public LocalDate readStartDate(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj datę zameldowania (w formacie RRRR-MM-DD):");
        LocalDate startDate = null;
        String datePattern = "\\d{4}-\\d{2}-\\d{2}";
        boolean isValid = false;
        while(!isValid){
            String dateString = scanner.nextLine();
            if (dateString.isEmpty()){
                startDate = LocalDate.now();
                isValid = true;
            }
            else {
                if(Pattern.matches(datePattern,dateString )) {
                    startDate = LocalDate.parse(dateString);
                    isValid = true;
                }
                else{
                    System.out.println("Błędny format daty. Spróbuj ponownie.");
                }
            }
        }
        return startDate;
    }

    /**
     * Reads the duration of the guest's stay in days. The method ensures that
     * the input is a positive integer.
     *
     * @return the duration of the stay in days
     */
    public int readDuration(){
        System.out.println("Podaj czas trwania pobytu: ");
        Scanner scanner = new Scanner(System.in);
        int duration;
        while(true){
            try{
                duration = scanner.nextInt();
                break;
            }
            catch(InputMismatchException e){
                System.out.println("Podaj całkowitą liczbę dodatnią.");
                scanner.nextLine();
            }
        }
        return duration;
    }
}

