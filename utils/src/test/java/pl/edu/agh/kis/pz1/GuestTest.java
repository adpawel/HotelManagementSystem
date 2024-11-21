package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestTest {

    Guest guest;
    Guest mainGuest;

    @BeforeEach
    void setUp() {
        guest = new Guest("Jan Kowalski");
        mainGuest = new Guest("Adam Nowak", "anowak@gmail.com", "03700203947", "765982019");
    }

    @Test
    void isMain() {
        assertTrue(mainGuest.isMain());
        assertFalse(guest.isMain());
    }

    @Test
    void setMain() {
        Guest normalGuest = new Guest("Anna Styczeń");
        normalGuest.setMain(true);
        assertTrue(normalGuest.isMain());
    }

    @Test
    void getName() {
        assertEquals("Jan Kowalski", guest.getName());
    }

    @Test
    void setName() {
        Guest g1 = new Guest("Maria Marzec");
        g1.setName("Marian Marzec");
        assertEquals("Marian Marzec", g1.getName());
    }

    @Test
    void getEmailAddress() {
        assertEquals("anowak@gmail.com", mainGuest.getEmailAddress());
    }

    @Test
    void setEmailAddress() {
        mainGuest.setEmailAddress("adamn@gmail.com");
        assertEquals("adamn@gmail.com", mainGuest.getEmailAddress());
    }

    @Test
    void getPhoneNumber() {
        assertEquals("765982019", mainGuest.getPhoneNumber());
    }

    @Test
    void setPhoneNumber(){
        mainGuest.setPhoneNumber("234567890");
        assertEquals("234567890", mainGuest.getPhoneNumber());
    }

    @Test
    void getIdNumber() {
        assertEquals("03700203947", mainGuest.getIdNumber());
    }

    @Test
    void setIdNumber() {
        mainGuest.setIdNumber("98789789102");
        assertEquals("98789789102", mainGuest.getIdNumber());
    }
}