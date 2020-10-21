package core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HardOpponentTest {
    private HardOpponent hardOpponent;
    private Board board;

    /**
     * Setup method, running before each test method.
     */
    @Before
    public void setUp() {
        board = new Board();
        board.setPlayer2(new Player("Dummy player"));
        hardOpponent = new HardOpponent();
        hardOpponent.setBoard(board);
    }

    private void boardSetUp1() {
        board.resetBoard();
        board.doMove(0);
        board.doMove(1);
        board.doMove(2);
        board.doMove(5);
        board.doMove(4);
        board.doMove(6);
    }

    private void boardSetUp2() {
        board.resetBoard();
        board.doMove(2);
        board.doMove(0);
        board.doMove(4);
        board.doMove(6);
    }

    private void boardSetUp3() {
        board.resetBoard();
        board.doMove(1);
        board.doMove(0);
        board.doMove(3);
        board.doMove(6);
        board.doMove(4);
        board.doMove(7);
        board.doMove(8);
    }

    private void boardSetUp4() {
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
    public void hardChooseSquare() {
        Assert.assertTrue(checkRange(hardOpponent.chooseSquare(board.getSquares()), 0, 8));
        boardSetUp1();
        Assert.assertEquals(8, hardOpponent.chooseSquare(board.getSquares()));
        boardSetUp2();
        Assert.assertEquals(3, hardOpponent.chooseSquare(board.getSquares()));
        boardSetUp3();
        Assert.assertEquals(5, hardOpponent.chooseSquare(board.getSquares()));
        boardSetUp4();
        try {
            hardOpponent.chooseSquare(board.getSquares());
            Assert.fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            //ok
        }
    }
}
