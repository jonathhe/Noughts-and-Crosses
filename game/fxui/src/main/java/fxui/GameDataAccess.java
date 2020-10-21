package fxui;

import core.Player;
import core.Square;
import java.util.List;

/**
 * This interface describes the methods required for a data access layer which
 * handles communication between a Controller (front end) and the Board class
 * (back end).
 */
public interface GameDataAccess {

    /**
     * Set player one.
     * @param player the Player who goes first.
     */
    void setPlayer1(Player player);

    /**
     * Set player two.
     * @param player the Player who goes second.
     */
    void setPlayer2(Player player);

    /**
     * Get player one.
     * @return  the Player who goes first.
     */
    Player getPlayer1();

    /**
     * Get player two.
     * @return  the Player who goes second.
     */
    Player getPlayer2();

    /**
     * Get the winner of this round.
     * @return  the Player who won, or null if there is no winner
     *          (draw or game not over).
     */
    Player getWinner();

    /**
     * Get the loser of this round.
     * @return  the Player who lost, or null if there is no loser
     *          (draw or game not over).
     */
    Player getLoser();

    /**
     * Will return a copy of the list of squares which belongs to the board.
     * @return Returns a list of squares.
     */
    List<Square> getSquares();

    /**
     * Checks whose turn it is.
     * @return true if player one goes next, otherwise false.
     */
    boolean isFirstPlayersTurn();

    /**
     * Tries to execute a move at the given square. The squares are in a 3x3 grid,
     * with index 0 at the top left and index 8 at the bottom right.
     * The Board class will use the symbol of whichever player has the current turn.
     * @param index an int in the range 0-8.
     */
    void doMove(int index);

    /**
     * Tells the Board to reset the game state, so that a new round can begin.
     */
    void resetBoard();

    /**
     * Checks if the game is over. The game is over if all squares are taken
     * or if one of the players have managed to get 3 in a row.
     * @return true if the game is over, false otherwise.
     */
    boolean isGameOver();

    /**
     * Checks if there is a game in progress. The game has started if any of the squares
     * have been taken by a player.
     * @return true if the game has started, false otherwise.
     */
    boolean isGameStarted();

    /**
     * Changes the type of opponent a player plays against (Easy, Medium, Hard, Two Player).
     * Player object is used to prevent the need of a new JSON serializer and deserializer.
     * @param opponent Player object representing the type opponent should be set to.
     */
    void changeOpponent(Player opponent);
}
