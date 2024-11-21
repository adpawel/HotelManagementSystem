package pl.edu.agh.kis.pz1;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A class responsible for handling the Excel file interactions.
 * <p>
 * The ExcelHandler class is used to read and write data from/to an Excel file
 * containing information about rooms and guests. It loads data from the file,
 * processes it, and updates the Excel file accordingly. The class also handles
 * saving and closing the workbook.
 * </p>
 */
public class ExcelHandler {
    private static final Logger LOGGER = Logger.getLogger(ExcelHandler.class.getName());
    private final String excelFilePath;
    private Workbook workbook;

    /**
     * Retrieves the current workbook.
     *
     * @return the {@link Workbook} object associated with the Excel file.
     */
    public Workbook getWorkbook() {
        return workbook;
    }

    /**
     * Constructor that initializes the ExcelHandler by opening the specified Excel file.
     *
     * @param excelFilePath the path to the Excel file to be used.
     * @throws IOException if there is an error reading the Excel file.
     */
    public ExcelHandler(String excelFilePath) throws IOException {
        this.excelFilePath = excelFilePath;
        try (FileInputStream fis = new FileInputStream(excelFilePath)) {
            this.workbook = new XSSFWorkbook(fis);
        }
    }

    /**
     * Reads the room data from the Excel file and returns it as a map of room numbers to room objects.
     * <p>
     * This method processes each sheet in the Excel workbook, extracting room information such as
     * room number, price, type, capacity, guest information, start date, and end date.
     * </p>
     *
     * @return a {@link MyMap} containing room data, where keys are room numbers, and values are {@link Room} objects.
     */
    public MyMap<Integer, Room> getRoomsData() {
        MyMap<Integer, Room> rooms = new MyMap<>();

        for(int k = 0; k < workbook.getNumberOfSheets(); k++) {
            Sheet sheet = workbook.getSheetAt(k);
            int i = 0;

            for (Row row : sheet) {
                if (i != 0 && row.getCell(0) != null) {
                    int roomNr = (int) row.getCell(0).getNumericCellValue();
                    int price = (int) row.getCell(1).getNumericCellValue();
                    String type = row.getCell(2).getStringCellValue();
                    int capacity = (int) row.getCell(3).getNumericCellValue();

                    Room room = new Room(roomNr, price, type, capacity);

                    List<Guest> guestList = getGuestsListFromRow(row);
                    if(!guestList.isEmpty()){
                        room.setGuests(guestList);
                    }

                    room.setStartDate(getExcelDate(row.getCell(8)));
                    room.setEndDate(getExcelDate(row.getCell(9)));
                    rooms.put(roomNr, room);
                }
                i++;
            }
        }
        return rooms;
    }

    /**
     * Converts an Excel date to a {@link LocalDate}.
     * <p>
     * The Excel date format is numeric, where the value represents the number of days since January 1, 1900.
     * This method converts that value into a {@link LocalDate}.
     * </p>
     *
     * @param dateCell the Excel cell containing the date value.
     * @return a {@link LocalDate} representing the Excel date, or null if the date cell is empty or invalid.
     */
    LocalDate getExcelDate(Cell dateCell) {
        if (dateCell != null && dateCell.getCellType() == CellType.NUMERIC) {
            LocalDate excelStartDate = LocalDate.of(1900, 1, 1);
            int daysSince1900 = (int) dateCell.getNumericCellValue() - 2;
            return excelStartDate.plusDays(daysSince1900);
        }
        return null;
    }

    /**
     * Extracts guest information from the given row.
     * <p>
     * This method parses the guest information from cells 4 to 7 in the row, splitting the cell's value into individual
     * attributes such as name, email, ID number, and phone number.
     * </p>
     *
     * @param row the row containing guest data.
     * @return a list of {@link Guest} objects created from the guest data.
     */
    protected List<Guest> getGuestsListFromRow(Row row) {
        List<Guest> guestList = new ArrayList<>();

        for (int j = 4; j < 8; ++j) {
            Cell cell = row.getCell(j);
            if(cell != null){
                String[] parts = cell.getStringCellValue().split(",");
                String name = parts[0].trim();
                if (parts.length > 1) {
                    String emailAddress = parts[1].trim();
                    String idNumber = parts[2].trim();
                    String phoneNumber = parts[3].trim();
                    guestList.add(new Guest(name, emailAddress, idNumber, phoneNumber));
                } else {
                    guestList.add(new Guest(name));
                }
            }
        }
        return guestList;
    }

    /**
     * Updates the information of a room in the Excel file.
     * <p>
     * This method updates guest information, start and end dates, and additional information for a specific room.
     * </p>
     *
     * @param room the {@link Room} object containing the updated information.
     */
    public void updateRoomInfo(Room room) {
            int floor = room.getNumber() /100;
            Sheet sheet = workbook.getSheetAt(floor - 1); // indeksowane od '0' i dlatego -1

            int numberAtFloor = room.getNumber()%100;
            Row row = sheet.getRow(numberAtFloor);
            int i = 5;
            for (Guest g : room.getGuests()) {
                if(g.isMain()){
                    Cell cell = row.getCell(4);
                    if (cell == null) {
                        cell = row.createCell(4);
                    }
                    cell.setCellValue(g.getName() + "," +
                                            g.getEmailAddress() + "," +
                                            g.getPhoneNumber() + "," +
                                            g.getIdNumber());
                }
                else{
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        cell = row.createCell(i);
                    }
                    cell.setCellValue(g.getName());
                    ++i;
                }
            }
            Cell cellStartDate = row.getCell(8);
            if (cellStartDate == null) {
                cellStartDate = row.createCell(8);
            }
            cellStartDate.setCellValue(room.getStartDate());

            Cell cellEndDate = row.getCell(9);
            if (cellEndDate == null) {
                cellEndDate = row.createCell(9);
            }
            cellEndDate.setCellValue(room.getEndDate());

            Cell cellInfo = row.getCell(10);
            if (cellInfo == null) {
                cellInfo = row.createCell(10);
            }
            cellInfo.setCellValue(room.getAdditionalInfo());
    }

    /**
     * Clears the information of a room in the Excel file.
     * <p>
     * This method removes guest details, start and end dates, and additional information from the Excel sheet for the room.
     * </p>
     *
     * @param room the {@link Room} object whose information needs to be cleared.
     */
    public void clearRoomInfo(Room room) {
            int floor = room.getNumber() / 100;
            Sheet sheet = workbook.getSheetAt(floor - 1); // Indexed from 0, hence -1

            int numberAtFloor = room.getNumber()%100;
            Row row = sheet.getRow(numberAtFloor);
            for(int i = 4; i < 11; ++i){
                Cell cell = row.getCell(i);
                if (cell != null) {
                    row.removeCell(cell);
                }
            }
    }

    /**
     * Saves the current state of the Excel file.
     * <p>
     * This method writes all changes made to the workbook back to the file at the specified path.
     * </p>
     */
    public void save() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(fileOutputStream);
            System.out.println("Pomyślnie zapisano zmiany");
            System.out.print("Wprowadź komendę: ");
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }

    /**
     * Closes the workbook and releases any resources.
     */
    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }
}
