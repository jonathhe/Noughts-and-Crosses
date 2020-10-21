package core;

public class Square {
    private String selected = null;

    /**
     * Sets the state of this Square. Can only be changed from or to null,
     * not from a String to another String.
     * @param input a String, or null to reset the state.
     */
    public void setState(String input) {
        if (this.selected != null && input != null) {
            throw new IllegalStateException("Square already clicked.");
        }
        this.selected = input;
    }

    /**
     * Getter for the Square's state.
     * @return the state of this Square, null if it it's empty.
     */
    public String getState() {
        return selected;
    }

    /**
     * Checks if the Square is available.
     * @return true if the Square is available (state == null), false otherwise.
     */
    public boolean isAvailable() {
        return this.selected == null;
    }

    /**
     * A String-representation of the Square.
     * @return a String  representing the marker in this Square, a single space if it's empty.
     */
    @Override
    public String toString() {
        if (this.selected == null) {
            return " ";
        } else {
            return this.selected;
        }
    }
}
