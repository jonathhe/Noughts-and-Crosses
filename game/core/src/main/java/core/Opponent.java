package core;

import java.util.List;

public interface Opponent {

    Board board = null;

    /**
     * Chooses the square on board the computer opponent wants to do a move in.
     * @param squares List of squares representing the board.
     * @return Integer representing the square opponent wants to do a move in.
     */
    int chooseSquare(List<Square> squares);

    /**
     * Setter for board variable in some opponent classes.
     * @param board class containing logic some opponent classes depends on.
     */
    void setBoard(Board board);
}
