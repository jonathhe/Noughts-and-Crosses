package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private Player player1;
    private Player player2;
    private List<Square> squares;
    private boolean isFirstPlayersTurn = true;

    /**
     * Empty constructor  which generates two Players. Player1 is a normal Player,
     * while Player2 is an EasyOpponent.
     */
    public Board() {
        this.setPlayer1(new Player("Mr Placeholder"));
        player2 = new EasyOpponent();
        player2.setName("Mr Easy");
        this.setPlayer2(player2);
        generateBoard();
    }

    private void generateBoard() {
        squares = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            squares.add(new Square());
        }
    }

    /**
     * Gets player 1.
     * @return Player 1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Sets player 1.
     * @param player1 Sets Player 1
     */
    public void setPlayer1(Player player1) {
        player1.setSymbol(("X"));
        this.player1 = player1;
    }

    /**
     * Gets player 2.
     * @return Player 2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Sets player 2.
     * @param player2 Sets Player 2
     */
    public void setPlayer2(Player player2) {
        player2.setSymbol(("O"));
        this.player2 = player2;
    }

    /**
     * Makes a new list consisting of both players.
     * @return Both players as a List
     */
    public List<Player> getPlayers() {
        return Arrays.asList(player1, player2);
    }


    /**
     * Generates a copy of the squares list without changing the original list.
     * @return  returns a list of squares with the same values as actual list of squares.
     */
    public List<Square> getSquares() {
        List<Square> squares = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Square newSquare = new Square();
            newSquare.setState(this.squares.get(i).getState());
            squares.add(newSquare);
        }
        return squares;
    }

    /**
     * Checks if it is player1 who has the next move.
     * @return  Returns true if it is player1's turn to make a move,
     *          and false if it is player2's turn.
     */
    public boolean isFirstPlayersTurn() {
        return isFirstPlayersTurn;
    }

    /**
     * Shows which player has the next move.
     * @return Returns the player that has the next move.
     */
    public Player getCurrentPlayer() {
        if (isFirstPlayersTurn) {
            return player1;
        }
        return player2;
    }

    /**
     * Makes a move.
     * @param index The index of the square you want to choose for your next move.
     */
    public void doMove(int index) {
        if (index > 8 || index < 0) {
            throw new IllegalArgumentException("Invalid index.");
        }
        this.doMove(this.squares.get(index));
        if (this.getCurrentPlayer() instanceof Opponent && ! this.isGameOver()) {
            Opponent opponent = (Opponent) this.getCurrentPlayer();
            int nextMove = opponent.chooseSquare(this.squares);
            this.doMove(nextMove);
        }
    }


    /**
     * Makes a move.
     * @param square The square you want to choose for your next move.
     */
    private void doMove(Square square) {
        if (isGameOver()) {
            throw new IllegalStateException("The game is over");
        }
        if (square == null) {
            throw new IllegalArgumentException(("No such square!"));
        }
        if (! square.isAvailable()) {
            throw new IllegalArgumentException("Not a valid move!");
        }
        square.setState(getCurrentPlayer().getSymbol());
        isFirstPlayersTurn = ! isFirstPlayersTurn;
    }


    /**
     * Tells you if someone has won the game.
     * @param state     The state you want to check for three-in-a-row
     * @return          returns true if the state occur three-in-a-row
     */
    protected boolean isWinning(String state) {
        return Board.isWinning(this.squares, state);
    }


    /**
     * Tells you if someone has won the game.
     * @param squares   A list of nine squares representing a board.
     * @param state     The state you want to check for three-in-a-row
     * @return          Returns true if the state occur three-in-a-row
     */
    protected static boolean isWinning(List<Square> squares, String state) {
        for (int i = 0; i < 3; i++) {
            // Check if it is three-in-a-row horizontally.
            if (helpIsWinning(state, true, i, squares)) {
                return true;
            }
            // Check if it is three-in-a-row vertically.
            if (helpIsWinning(state, false, i, squares)) {
                return true;
            }
        }
        return isDiagonal(squares, state);
    }


    /**
     * Helps isWinning decide if the state has won the game.
     * @param state     The state we want to check for three-in-a-row.
     * @param isRow     A boolean which is true if you want to check a row and
     *                  false if you want to check a column
     * @param start     The row or column you want to star with. An integer 0,1 or 2.
     * @param squares   A list of nine squares representing a board.
     * @return          True if the player is winning, otherwise false.
     */
    private static boolean helpIsWinning(String state, boolean isRow,
                                         int start, List<Square> squares) {
        boolean winner = true;
        int row = 1;
        int column = 1;
        if (isRow) {
            row = 3;
        } else {
            column = 3;
        }
        for (int i = 0; i < 3; i++) {
            if (squares.get(start * row + i * column).getState() != null) {
                if (!squares.get(start * row + i * column).getState().equals(state)) {
                    winner = false;
                }
            } else {
                winner = false;
            }
        }
        return winner;
    }


    /**
     * Tells you which player won the game.
     * @return  Returns the player who won the game or null if none of the players won
     */
    public Player getWinner() {
        if (this.isWinning(player1.getSymbol())) {
            return player1;
        } else if (this.isWinning(player2.getSymbol())) {
            return player2;
        } else {
            return null;
        }
    }


    /**
     * Tells you which player lost the game.
     * @return  Returns the player who lost the game or null if none of the players lost
     */
    public Player getLoser() {
        if (this.isWinning(player2.getSymbol())) {
            return player1;
        } else if (this.isWinning(player1.getSymbol())) {
            return player2;
        } else {
            return null;
        }
    }


    /**
     * Tells you if it is three-in-a-row diagonally.
     * @param squares   A list of nine squares representing a board.
     * @param state     The state we want to check for three-in-a-row diagonally.
     * @return          Returns true if the the state got three-in-a-row diagonally,
     *                  and false if it don't.
     */
    private static boolean isDiagonal(List<Square> squares, String state) {
        return helpIsDiagonal(state, squares, 0, 4, 9)
                ||
                helpIsDiagonal(state, squares, 2, 2, 7);
    }

    /**
     * Helping isDiagonal to find three-in-a-row diagonally.
     * @param state         The state we want to check for three-in-a-row diagonally.
     * @param squares       A list of nine squares representing a board.
     * @param startSquare   The index of the square representing the top left corner
     *                      or the top right corner.
     * @param jumps The difference between the squares:
     *              4 if you jump from the top left corner to the middle
     *              and then to the bottom right corner.
     *              2 if you jump from the top right corner to the middle
     *              and then to the bottom left corner.
     * @param endSquare     The number of the last square you want to check
     * @return              Returns true if the the state got three-in-a-row diagonally,
     *                      and false if it don't.
     */
    private static boolean helpIsDiagonal(String state, List<Square> squares,
                                          int startSquare, int jumps, int endSquare) {
        for (int n = startSquare; n < endSquare; n += jumps) {
            if (squares.get(n).getState() != null) {
                if (! squares.get(n).getState().equals(state)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /** A method to check if the game is over.
     * @return  Returns true if someone has won the game or every square is taken,
     *          false if the game is still going on.
     */
    public boolean isGameOver() {
        if (isWinning(player1.getSymbol()) || isWinning(player2.getSymbol())) {
            return true;
        }
        return isBoardFull(this.squares);
    }

    /** A method to tell if the game has started.
     * @return  Returns false if every square is available, and true any square is taken.
     */
    public boolean isGameStarted() {
        for (Square square : squares) {
            if (! square.isAvailable()) {
                return true;
            }
        }
        return false;
    }


    /** A method to check if all the squares are taken.
     * @param squares A list of nine squares representing a board.
     * @return Returns true if all the squares are taken and false if any square is available.
     */
    protected static boolean isBoardFull(List<Square> squares) {
        for (Square square : squares) {
            if (square.isAvailable()) {
                return false;
            }
        }
        return true;
    }



    /**
     * Clears the board and makes it player1's turn again.
     */
    public void resetBoard() {
        for (Square square : squares) {
            square.setState(null);
        }
        this.isFirstPlayersTurn = true;
    }

    /**
     * Changes the type of player player2 is based on input objects name variable opponent.
     * @param opponent Player object with name variable representing the
     *                 type of opponent player2 should be set to.
     */
    public void changeOpponent(String opponent) {
        if (!isGameStarted()) {
            switch (opponent) {
                case "Medium":
                    player2 = new MediumOpponent();
                    player2.setName("Mr Medium");
                    ((MediumOpponent) player2).setBoard(this);
                    this.setPlayer2(player2);
                    break;
                case "Hard":
                    player2 = new HardOpponent();
                    player2.setName("Mr Hard");
                    ((HardOpponent) player2).setBoard(this);
                    this.setPlayer2(player2);
                    break;
                case "2 Player":
                    player2 = new Player("Mr Spacehandler");
                    this.setPlayer2(player2);
                    break;
                default:
                    player2 = new EasyOpponent();
                    player2.setName("Mr Easy");
                    this.setPlayer2(player2);
                    break;
            }
        }
    }
}
