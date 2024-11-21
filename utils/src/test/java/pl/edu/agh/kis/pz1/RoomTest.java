package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RoomTest {
    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room(101, 200, "Deluxe", 2);
    }

    @Test
    void testIsFree_WhenRoomIsEmpty_ShouldReturnTrue() {
        assertTrue(room.isFree(), "Pokój powinien być pusty");
    }

    @Test
    void testIsFree_WhenRoomIsOccupied_ShouldReturnFalse() {
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("Jan Kowalski"));
        room.setGuests(guests);

        assertFalse(room.isFree(), "Pokój nie powinien być pusty");
    }

    @Test
    void testCalculatePrice_WhenDatesAreValid_ShouldReturnCorrectPrice() {
        room.setStartDate(LocalDate.now().minusDays(3));
        room.setPrice(200);

        long expectedPrice = 3 * 200;
        assertEquals(expectedPrice, room.calculatePrice(), "Błędnie obliczona należność");
    }

    @Test
    void testCalculatePrice_WhenStartDateIsNull_ShouldThrowException() {
        room.setStartDate(null);

        assertThrows(NullPointerException.class, room::calculatePrice, "Should throw NullPointerException when startDate is null");
    }

    @Test
    void testClear_ShouldResetAllFields() {
        List<Guest> guests = new ArrayList<>();
        guests.add(new Guest("John Doe"));
        room.setGuests(guests);
        room.setStartDate(LocalDate.now());
        room.setEndDate(LocalDate.now().plusDays(2));
        room.setAdditionalInfo("Some info");

        room.clear();

        assertTrue(room.getGuests().isEmpty(), "Guests list should be cleared");
        assertNull(room.getStartDate(), "Start date should be null");
        assertNull(room.getEndDate(), "End date should be null");
        assertNull(room.getAdditionalInfo(), "Additional info should be null");
    }
}