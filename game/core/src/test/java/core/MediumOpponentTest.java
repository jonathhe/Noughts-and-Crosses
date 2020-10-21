package core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MediumOpponentTest {
    private MediumOpponent mediumOpponent;
    private Board board;

    /**
     * Setup method, running before each test method.
     */
    @Before
    public void setUp() {
        board = new Board();
        board.setPlayer2(new Player("Dummy player"));
        mediumOpponent = new MediumOpponent();
        mediumOpponent.setBoard(board);
    }

    private void setUpBoard1() {
        board.resetBoard();
        board.doMove(0);
        board.doMove(1);
        board.doMove(2);

    }

    private void setUpBoard2() {
        board.resetBoard();
        board.doMove(0);
        board.doMove(1);
        board.doMove(2);
        board.doMove(3);
        board.doMove(4);
        board.doMove(6);
        board.doMove(5);
        board.doMove(7);
        board.doMove(8);
    }

    private boolean checkRange(int i, int min, int max) {
        return (i >= min && i <= max);
    }

    @Test
    public void mediumChooseSquare() {
        Assert.assertTrue(checkRange(mediumOpponent.chooseSquare(board.getSquares()), 0, 8));
        setUpBoard1();
        Assert.assertTrue(checkRange(mediumOpponent.chooseSquare(board.getSquares()), 3, 8));
        setUpBoard2();
        try {
            mediumOpponent.chooseSquare(board.getSquares());
            Assert.fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            //ok
        }
    }

    @Test
    public void setFactors() {
        double d = 0.001;
        Assert.assertEquals(mediumOpponent.getRandomFactor(), 0.5, d);
        Assert.assertEquals(mediumOpponent.getBestFactor(), 0.5, d);
        mediumOpponent.setFactors(0.3, 0.7);
        Assert.assertEquals(mediumOpponent.getRandomFactor(), 0.3, d);
        Assert.assertEquals(mediumOpponent.getBestFactor(), 0.7, d);
        try {
            mediumOpponent.setFactors(0.7, 0.7);
            Assert.fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //ok
        }
    }
}
