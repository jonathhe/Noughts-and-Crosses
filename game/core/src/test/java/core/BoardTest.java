package core;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {

    Board board;
    List<Square> squares;
    Player player1;
    Player player2;

    /**
     * Setup method, running before each test method.
     */
    @Before
    public void setUp() {
        player1 = new Player();
        player1.setSymbol("X");
        player2 = new Player();
        player2.setSymbol("O");

        squares = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            squares.add(new Square());
        }
        board = new Board();
        board.setPlayer1(player1);
        board.setPlayer2(player2);
    }

    @Test
    public void testIsFirstPlayersTurn() {
        Assert.assertTrue(board.isFirstPlayersTurn());
        board.doMove(0);
        Assert.assertFalse(board.isFirstPlayersTurn());
    }

    @Test
    public void testDoMove() {
        board.doMove(0);
        Assert.assertEquals("X", board.getSquares().get(0).getState());
        board.doMove(1);
        Assert.assertEquals("O", board.getSquares().get(1).getState());
        board.doMove(2);
        try {
            board.doMove(2);
            Assert.fail("Invalid index.");
        } catch (IllegalArgumentException e) {
            // OK
        }
        try {
            board.doMove(10);
            Assert.fail("Invalid index.");
        } catch (IllegalArgumentException e) {
            // OK
        }
        board.doMove(3);
        board.doMove(4);
        board.doMove(5);

        board.doMove(6);

        try {
            board.doMove(8);
            Assert.fail("The game should be over.");
        } catch (IllegalStateException e) {
            Assert.assertEquals(true, board.isGameOver());
        }
    }

    @Test
    public void testIsWinning() {
        board.setPlayer2(new Player());
        Assert.assertFalse(board.isWinning("X"));
        Assert.assertEquals(null, board.getWinner());
        Assert.assertEquals(null, board.getLoser());

        board.doMove(0);
        board.doMove(8);
        board.doMove(1);
        board.doMove(6);
        board.doMove(2);

        Assert.assertEquals(board.getPlayer1(), board.getWinner());
        Assert.assertEquals(board.getPlayer2(), board.getLoser());
        Assert.assertTrue(board.isWinning("X"));
    }

    @Test
    public void testIsGameOver() {
        Assert.assertFalse(board.isGameOver());
        Assert.assertTrue(! board.isGameStarted());
        board.doMove(4);
        Assert.assertTrue(board.isGameStarted());
        board.doMove(1);
        board.doMove(0);
        board.doMove(8);
        board.doMove(2);
        Assert.assertFalse(board.isGameOver());
        board.doMove(6);
        board.doMove(3);
        board.doMove(5);
        board.doMove(7);
        Assert.assertEquals(null, board.getWinner());
        Assert.assertTrue(board.isGameOver());
    }

    @Test
    public void testGetCurrentPlayer() {
        Player playerOne = new Player();
        Player playerTwo = new Player();
        board.setPlayer1(playerOne);
        Assert.assertEquals(playerOne, board.getPlayer1());
        board.setPlayer2(playerTwo);
        Assert.assertEquals(playerTwo, board.getPlayer2());
        Assert.assertEquals(playerOne, board.getCurrentPlayer());
        board.doMove(1);
        Assert.assertEquals(playerTwo, board.getCurrentPlayer());
        Assert.assertEquals(playerOne, board.getPlayers().get(0));
    }

    @Test
    public void testGetSquares() {
        board.doMove(0);
        List<Square> s1 = board.getSquares();
        Assert.assertEquals("X", s1.get(0).getState());
    }

    @Test
    public void testOpponentMove() {
        board = new Board();
        Assert.assertTrue(board.getPlayer2() instanceof Opponent);
        board.doMove(0);
    }

    @Test
    public void testResetBoard() {
        board.doMove(0);
        Assert.assertEquals("X", board.getSquares().get(0).toString());
        board.resetBoard();
        Assert.assertEquals(null, board.getSquares().get(0).getState());
    }

    @Test
    public void testChangeOpponent() {
        board.changeOpponent("Easy");
        Assert.assertTrue(board.getPlayer2() instanceof EasyOpponent);
        board.changeOpponent("Medium");
        Assert.assertTrue(board.getPlayer2() instanceof MediumOpponent);
        board.changeOpponent("Hard");
        Assert.assertTrue(board.getPlayer2() instanceof HardOpponent);
        board.changeOpponent("2 Player");
        Assert.assertFalse(board.getPlayer2() instanceof HardOpponent);
        Assert.assertFalse(board.getPlayer2() instanceof MediumOpponent);
        Assert.assertFalse(board.getPlayer2() instanceof EasyOpponent);
    }
}
