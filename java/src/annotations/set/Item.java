package annotations.set;


import annotations.bytecodeAnnotations.LogEntry;

import java.util.Objects;

/**
 * An item with a description and a part number.
 *
 * @author Cay Horstmann
 * @version 1.01 2012-01-26
 */
public class Item {
    private String description;
    private int partNumber;

    /**
     * Constructs an item.
     *
     * @param aDescription the item's description
     * @param aPartNumber  the item's part number
     */
    public Item(String aDescription, int aPartNumber) {
        description = aDescription;
        partNumber = aPartNumber;
    }

    /**
     * Gets the description of this item.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public String toString() {
        return "[description=" + description + ", partNumber=" + partNumber + "]";
    }

    @LogEntry(logger = "com.horstmann")
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        var other = (Item) otherObject;
        return Objects.equals(description, other.description) && partNumber == other.partNumber;
    }

    @LogEntry(logger = "com.horstmann")
    public int hashCode() {
        return Objects.hash(description, partNumber);
    }
}
