package pl.edu.agh.kis.pz1;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExcelHandlerTest {

    private static final String TEST_FILE_PATH = "test_excel_file.xlsx";
    private Workbook workbook;
    private ExcelHandler excelHandler;

    @BeforeEach
    void setUp() throws IOException {
        // Create a test workbook and sheet
        workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Add test data to the sheet
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Room Number");
        headerRow.createCell(1).setCellValue("Price");
        headerRow.createCell(2).setCellValue("Type");
        headerRow.createCell(3).setCellValue("Capacity");
        headerRow.createCell(4).setCellValue("Additional Info");

        Row firstRow = sheet.createRow(1);
        firstRow.createCell(0).setCellValue(101);
        firstRow.createCell(1).setCellValue(250);
        firstRow.createCell(2).setCellValue("Standard");
        firstRow.createCell(3).setCellValue(2);
        firstRow.createCell(4).setCellValue("Widok na morze");

        try (FileOutputStream fos = new FileOutputStream(TEST_FILE_PATH)) {
            workbook.write(fos);
        }

        excelHandler = new ExcelHandler(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }

    @Test
    void testGetRoomsData() {
        MyMap<Integer, Room> rooms = excelHandler.getRoomsData();
        Room room = rooms.get(101);
        assertNotNull(rooms);
        assertEquals(101, room.getNumber());
        assertEquals(250, room.getPrice());
        assertEquals(2, room.getCapacity());
        assertEquals("Standard", room.getType());
    }

    @Test
    void testGetExcelDate() {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.createRow(1);
        Cell dateCell = row.createCell(0);
        dateCell.setCellValue(43500);  // Excel date format value
        LocalDate date = excelHandler.getExcelDate(dateCell);
        assertEquals(LocalDate.of(2019, 2, 4), date);
    }

    @Test
    void testUpdateRoomInfo() {
        Room room = new Room(101, 250, "Standard", 2);
        room.setStartDate(LocalDate.of(2023, 1, 1));
        room.setEndDate(LocalDate.of(2023, 12, 31));

        excelHandler.updateRoomInfo(room);
        Room updatedRoom = excelHandler.getRoomsData().get(101);
        assertEquals(101, updatedRoom.getNumber());
        assertEquals(LocalDate.of(2023, 1, 1), updatedRoom.getStartDate());
        assertEquals(LocalDate.of(2023, 12, 31), updatedRoom.getEndDate());
    }

    @Test
    void testClearRoomInfo() {
        Room room = new Room(101, 250, "Standard", 2);
        Cell additionalinfoBeforeClearing =  excelHandler.getWorkbook().getSheetAt(0).getRow(1).getCell(4);
        assertNotNull(additionalinfoBeforeClearing);

        excelHandler.clearRoomInfo(room);
        Cell additionalInfoAfterClearing = excelHandler.getWorkbook().getSheetAt(0).getRow(1).getCell(4);
        assertNull(additionalInfoAfterClearing);
    }
}
