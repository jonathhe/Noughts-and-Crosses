package core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EasyOpponentTest {
    private EasyOpponent easyOpponent;
    private Board board;

    /**
     * Setup method, running before each test method.
     */
    @Before
    public void setUp() {
        board = new Board();
        board.setPlayer2(new Player());
        easyOpponent = new EasyOpponent();
    }

    private void boardSetup1() {
        board.resetBoard();
        board.doMove(0);
        board.doMove(1);
        board.doMove(2);
    }

    private void boardSetup2() {
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
    public void easyChooseSquare() {
        Assert.assertTrue(checkRange(easyOpponent.chooseSquare(board.getSquares()), 0, 8));
        boardSetup1();
        Assert.assertTrue(checkRange(easyOpponent.chooseSquare(board.getSquares()), 3, 8));
        boardSetup2();
        try {
            easyOpponent.chooseSquare(board.getSquares());
            Assert.fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            //ok
        }
    }
}