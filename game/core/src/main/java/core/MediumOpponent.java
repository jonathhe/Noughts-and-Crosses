package core;

import java.util.List;
import java.util.Random;

public class MediumOpponent extends Player implements Opponent {

    private transient Board board;
    //sum of factors should not be more than 1
    private double randomFactor = 0.50;
    private double bestFactor = 0.50;
    //Borrows methods from easy and hard opponent.
    private HardOpponent hardOpp = new HardOpponent();
    private EasyOpponent easyOpp = new EasyOpponent();

    /**
     * Chooses the square a move should me done in with one
     * out of a selection of methods.
     * @param squares A list of nine squares representing the board.
     * @return the index of the square a move should be done in.
     */
    @Override
    public int chooseSquare(List<Square> squares) {
        if (Board.isBoardFull(squares)) {
            throw new IllegalStateException("Board is full.");
        }
        int move = selection();
        if (move == 1) {
            return easyOpp.chooseSquare(squares);
        }
        hardOpp.setBoard(this.board);
        return hardOpp.chooseSquare(squares);
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Selects an integer based on a randomly generated double in range [0,1)
     * and predefined factors that represent a chooseSquare method.
     * @return an integer representing the method that should me used.
     */
    //not boolean in case we want to implement other possible moves.
    private int selection() {
        Random rand = new Random();
        double r = rand.nextDouble();
        if (r < randomFactor) {
            return 1;
        }
        return 2;
    }

    /**
     * Set probability factor for available move-selection-methods.
     * Does not allow the sum of factors to be greater than 1.
     * @param rf the double value random factor should be set to.
     * @param bf the double value best move factor should be set to.
     */
    public void setFactors(double rf, double bf) {
        if ((rf + bf) <= 1) {
            this.bestFactor = bf;
            this.randomFactor = rf;
        } else {
            throw new IllegalArgumentException("Factor sum can not be greater than 1.");
        }
    }

    /**
     * Get probability factor for using easy opponent when medium opponent is selected.
     * Value in range [0, 1)
     * @return Double representing probability for using easy opponent methods.
     */
    public double getRandomFactor() {
        return this.randomFactor;
    }

    /**
     * Get probability factor for using hard opponent when medium opponent is selected.
     * Value in range [0, 1)
     * @return Double representing probability for using hard opponent methods.
     */
    public double getBestFactor() {
        return this.bestFactor;
    }
}
