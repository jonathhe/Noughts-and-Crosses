package core;

import java.util.List;

public class HardOpponent extends Player implements Opponent {

    private transient Board board;

    /**
     * Chooses the index of a square to make the best possible move on board.
     * @param squares list of nine squares representing the board.
     * @return index of the square a move should be done in.
     */
    @Override
    public int chooseSquare(List<Square> squares) {
        if (Board.isBoardFull(squares)) {
            throw new IllegalStateException("Board is full.");
        }
        String player = board.getCurrentPlayer().getSymbol();

        return minmax(squares, player, 0)[0];
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Minimax algorithm that iterates through every move to find the best one.
     * @param squares List of nine squares representing the board.
     * @param player String representing current players turn.
     * @param depth Integer representing the depth of the minimax tree.
     * @return Index of a square a move should be done in.
     */
    //Can improve runtime with pruning.
    private int[] minmax(List<Square> squares, String player, int depth) {
        int[] bestIndexAndScore = {-1, 0};
        //looks for terminal state on board.
        if (Board.isWinning(squares, "X")) {
            return new int[] {-1, -10 + depth};
        } else if (Board.isWinning(squares, "O")) {
            return new int[] {-1, 10 - depth};
        } else if (Board.isBoardFull(squares)) {
            return new int[] {-1, 0 - depth};
        }
        //try every move until terminal state.
        //return the index of the best move.
        if (player.equals("O")) {
            int bestMoveScore = -1000;
            for (int i = 0; i < 9; i++) {
                if (squares.get(i).isAvailable()) {
                    squares.get(i).setState(player);
                    int[] moveScore = minmax(squares, "X", depth + 1);
                    if (bestMoveScore < moveScore[1]) {
                        bestMoveScore = moveScore[1];
                        bestIndexAndScore[1] = moveScore[1];
                        bestIndexAndScore[0] = i;
                    }
                    Square s = new Square();
                    squares.set(i, s);
                }
            }
            return bestIndexAndScore;
        } else {
            int bestMoveScore = 1000;
            for (int i = 0; i < 9; i++) {
                if (squares.get(i).isAvailable()) {
                    squares.get(i).setState(player);
                    int[] moveScore = minmax(squares, "O", depth + 1);
                    if (bestMoveScore > moveScore[1]) {
                        bestMoveScore = moveScore[1];
                        bestIndexAndScore[1] = moveScore[1];
                        bestIndexAndScore[0] = i;
                    }
                    Square s = new Square();
                    squares.set(i, s);
                }
            }
            return bestIndexAndScore;
        }
    }
}
