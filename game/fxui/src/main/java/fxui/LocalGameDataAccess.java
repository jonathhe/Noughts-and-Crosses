package fxui;

import core.Board;
import core.Player;
import core.Square;
import java.util.List;

public class LocalGameDataAccess implements GameDataAccess {
    private Board board;

    /**
     * Generates a new Board with playable squares.
     */
    public LocalGameDataAccess() {
        this.board = new Board();
    }

    @Override
    public void setPlayer1(Player player) {
        board.setPlayer1(player);
    }

    @Override
    public void setPlayer2(Player player) {
        board.setPlayer2(player);
    }

    @Override
    public Player getPlayer1() {
        return board.getPlayer1();
    }

    @Override
    public Player getPlayer2() {
        return board.getPlayer2();
    }

    @Override
    public Player getWinner() {
        return board.getWinner();
    }

    @Override
    public Player getLoser() {
        return board.getLoser();
    }

    @Override
    public List<Square> getSquares() {
        return board.getSquares();
    }

    @Override
    public boolean isFirstPlayersTurn() {
        return board.isFirstPlayersTurn();
    }

    @Override
    public void doMove(int index) {
        board.doMove(index);
    }

    @Override
    public void resetBoard() {
        board.resetBoard();
    }

    @Override
    public boolean isGameOver() {
        return board.isGameOver();
    }

    @Override
    public boolean isGameStarted() {
        return board.isGameStarted();
    }

    @Override
    public void changeOpponent(Player opponent) {
        board.changeOpponent(opponent.getName());
    }
}
