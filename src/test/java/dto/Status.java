package dto;

/**
 * Enum representing the status of a pet.
 */
public enum Status {

    AVAILABLE("AVAILABLE"),
    PENDING("pending"),
    SOLD("sold");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    /**
     * Gets the string representation of the status.
     *
     * @return the string representation of the status
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns the Status enum constant corresponding to the provided string value.
     *
     * @param value the string value of the status
     * @return the Status enum constant, or null if no match is found
     */
    public static Status fromString(String value) {
        for (Status status : Status.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null; // or throw IllegalArgumentException if preferred
    }
}
