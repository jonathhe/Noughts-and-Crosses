package core;

import java.util.List;
import java.util.Random;

public class EasyOpponent extends Player implements Opponent {

    /**
     * Chooses a random square form the board to do a move in.
     * @param squares A list of nine squares representing the board.
     * @return the index of a square a move should be done in.
     */
    @Override
    public int chooseSquare(List<Square> squares) {
        if (Board.isBoardFull(squares)) {
            throw new IllegalStateException("Board is full.");
        }
        Random random = new Random();
        while (true) {
            int index = random.nextInt(9);
            if (squares.get(index).isAvailable()) {
                return index;
            }
        }
    }

    @Override
    public void setBoard(Board board) {
        //Not used.
    }
}
