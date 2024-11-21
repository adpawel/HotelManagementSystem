package pl.edu.agh.kis.pz1;

/**
 * A class representing a guest in the hotel system.
 * <p>
 * The Guest class holds information about an individual guest, including their name, contact details (email, phone number),
 * and identification number. A guest can also be marked as the "main" guest for a particular room.
 * </p>
 */
public class Guest {
    /**
     * is guest a main guest
     */
    protected boolean isMain;
    /**
     * Name of guest
     */
    protected String name;
    /**
     * EmailAddress
     */
    protected String emailAddress;
    /**
     * phoneNumber
     */
    protected String phoneNumber;
    /**
     * IdNumber
     */
    protected String idNumber;

    /**
     * Constructor for creating a main guest with all details.
     *
     * @param guestName the name of the guest.
     * @param email the email address of the guest, or null if not provided.
     * @param id the identification number (e.g., PESEL) of the guest, or null if not provided.
     * @param phone the phone number of the guest, or null if not provided.
     */
    public Guest(String guestName, String email, String id, String phone) {
        isMain = email != null;
        name = guestName;
        emailAddress = email;
        phoneNumber = phone;
        idNumber = id;
    }

    /**
     * Constructor for creating a guest which is not a main guest.
     * <p>
     * This constructor is used when only the name of the guest is available.
     * </p>
     *
     * @param name the name of the guest.
     */
    public Guest(String name) {
        this(name, null, null, null);
    }

    /**
     * Checks whether this guest is the main guest for the room.
     *
     * @return true if the guest is the main guest (i.e., has an email address); false otherwise.
     */
    public boolean isMain() {
        return isMain;
    }

    /**
     * Setter for isMain feature
     * @param main boolean value
     */
    public void setMain(boolean main) {
        isMain = main;
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name a name of guest
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for email address
     * @return emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Setter for email address
     * @param emailAddress an email address of a guest
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Getter for phone number
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter for phone number
     * @param phoneNumber phone number of type string
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for id number
     * @return idNumber
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * Setter for id number
     * @param idNumber of type String
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * Returns a string representation of the guest's details.
     * <p>
     * If the guest is the main guest, the string will include the name, email, phone number, and identification number.
     * If the guest is not the main guest, only the name will be included.
     * </p>
     *
     * @return a string representation of the guest's details.
     */
    @Override
    public String toString() {
        if (isMain) {
            return "Imię i nazwisko: " + name +
                    ", Adres email: " + emailAddress  +
                    ", Nr telefonu: " + phoneNumber +
                    ", Pesel/Numer identyfikacyjny: " + idNumber;
        }
        return "Imię i nazwisko: " + name;
    }
}
