package core;

public class Player {
    public int wins = 0;
    public int draws = 0;
    public int losses = 0;
    private String name = "Player";
    private transient String symbol = "";

    /**
     * Empty constructor which does not set a player name.
     */
    public Player() {
        // No-args constructor which is used for GSON serialization.
    }

    /**
     * Constructor which also sets a player name.
     * @param name Must consist of letters A-Z or numbers,
     *              the only special characters allowed are hyphens, underscores and spaces.
     */
    public Player(String name) {
        setName(name);
    }

    /**
     * Sets the player name, if the given name is valid.
     * @param name  Must consist of letters A-Z or numbers,
     *              the only special characters allowed are hyphens, underscores and spaces.
     */
    public void setName(String name) {
        if (isValidName(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
    }

    /** Getter for player name.
     * @return The player's name.
     */
    public String getName() {
        return this.name;
    }

    private boolean isValidName(String name) {
        String expression = "^[A-Za-z0-9 _-]*[A-Za-z0-9][A-Za-z0-9 _-]*$";
        return name.matches(expression);
    }

    /**
     * Sets the symbol used by the player in the game, it can be any single symbol.
     * @param symbol    Must be a single symbol.
     */
    public void setSymbol(String symbol) {
        if (symbol.length() != 1) {
            throw new IllegalArgumentException("Invalid symbol, should be a single character.");
        }
        this.symbol = symbol;
    }

    /**
     * Getter for player marker.
     * @return  The symbol used as the game marker for this player, as a String.
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Get the players stats in a string format.
     * @return A string containing stats; wins, losses and draws.
     */
    public String getStats() {
        return String.format(" (%s/%s/%s)", wins, losses, draws);
    }

    @Override
    public String toString() {
        return name + String.format(" (%s/%s/%s)", wins, losses, draws);
    }
}


