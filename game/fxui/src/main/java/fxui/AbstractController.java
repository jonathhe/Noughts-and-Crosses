package fxui;

import core.Player;
import core.Square;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public abstract class AbstractController {
    @FXML
    protected GridPane gridPane;

    @FXML
    protected TextField player1NameField;

    @FXML
    protected TextField player2NameField;

    @FXML
    protected TextArea textArea;

    @FXML
    protected Label p1;

    @FXML
    protected Label p1stats;

    @FXML
    protected Label p2;

    @FXML
    protected Label p2stats;

    @FXML
    protected Button restartButton;

    @FXML
    protected ChoiceBox<String> choiceBox;

    protected List<Player> allPlayers;
    protected Player player1;
    protected Player player2;
    protected List<Button> buttons;
    protected List<Square> squares;

    protected GameDataAccess dataAccess;

    @FXML
    protected void initialize() {
        print("Welcome to the game! "
                + "To join the game, enter your name in one of "
                + "the player fields and press <ENTER>. "
                + "Press the restart button to start a new game.");
        refreshScene();
        setUpChoiceBox();

        // Adds listeners to the name field, checking whether content changes or enter is pressed.
        player1NameField.focusedProperty().addListener((obs, oldVal, newVal) ->
                addPlayer1());
        player1NameField.setOnAction((event) ->
                addPlayer1());

        player2NameField.focusedProperty().addListener((obs, oldVal, newVal) ->
                addPlayer2());
        player2NameField.setOnAction((event) ->
                addPlayer2());
    }

    protected abstract void setDataAccess();

    @FXML
    protected void clickSquare(Button button) {
        int index = buttons.indexOf(button);
        if (!dataAccess.isGameOver() && squares.get(index).isAvailable()) {
            dataAccess.doMove(index);
        }
        refreshScene();
    }

    protected void print(String text) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        textArea.appendText(dtf.format(now) + " - " + text + "\n");
    }

    // Reads stored player names from file
    protected abstract void loadPlayers(String playerFile);

    // Saves player names and stats to file
    protected abstract void savePlayers();

    protected void addPlayer1() {
        String name = player1NameField.getText();
        if (dataAccess.isGameOver() || ! dataAccess.isGameStarted()) {
            if (! name.equals(player1.getName())) {
                player1 = findPlayer(name);
                dataAccess.setPlayer1(player1);
            }
        }
        refreshScene();
    }

    protected void addPlayer2() {
        String name = player2NameField.getText();
        if (dataAccess.isGameOver() || ! dataAccess.isGameStarted()) {
            if (! name.equals(dataAccess.getPlayer2().getName())) {
                player2 = findPlayer(name);
                dataAccess.setPlayer2(player2);
            }
        }
        refreshScene();
    }

    protected Player findPlayer(String name) {
        for (Player player : allPlayers) {
            if (player.getName().equals(name)) {
                print("Welcome back, " + name);
                return player;
            }
        }
        Player player = new Player();
        player.setName(name);
        allPlayers.add(player);
        savePlayers();
        print(name + " has joined the game.");
        return player;
    }

    protected void generateBoard() {
        gridPane.getChildren().clear();
        this.buttons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = generateButton();
                buttons.add(button);
                gridPane.add(button, j, i);
            }
        }
    }

    protected Button generateButton() {
        Button button = new Button(" ");
        button.setPrefSize(100, 100);
        button.setFocusTraversable(false);
        String style =    "-fx-font-size: 40px;"
                + "-fx-border-radius: 1px;"
                + "-fx-border-width: 1px;"
                + "-fx-background-color: #EAEAEA;"
                + "-fx-border-color: #273C2C;"
                + "-fx-color: #C0D6DF;"
                ;
        button.setStyle(style);
        button.setOnAction((event -> clickSquare((button))));
        return button;
    }

    // This refreshes local data and UI state
    protected void refreshScene() {
        this.squares = dataAccess.getSquares();
        Player p1 = dataAccess.getPlayer1();
        if (player1 == null || ! p1.getName().equals(player1.getName())) {
            player1 = findPlayer(p1.getName());
        }

        Player p2 = dataAccess.getPlayer2();
        if (player2 == null || ! p2.getName().equals(player2.getName())) {
            player2 = findPlayer(p2.getName());
        }

        for (Button button : buttons) {
            int index = buttons.indexOf(button);
            Square square = squares.get(index);
            button.setText(square.toString());
        }

        if (dataAccess.isGameOver()) {
            endGame();
        } else {
            playersTurn();
        }

        if (dataAccess.isGameOver() || ! dataAccess.isGameStarted()) {
            player1NameField.setText(player1.getName());
            p1stats.setText(player1.getStats());
            player2NameField.setText(player2.getName());
            p2stats.setText(player2.getStats());
            restartButton.setDisable(false);
        } else if (dataAccess.isGameStarted()) {
            player1NameField.setDisable(true);
            player2NameField.setDisable(true);
            restartButton.setDisable(true);
            choiceBox.setDisable(true);
        }
    }

    protected void endGame() {
        for (Button button : buttons) {
            button.setDisable(true);
        }
        Player winner = dataAccess.getWinner();
        Player loser = dataAccess.getLoser();
        if (winner == null || loser == null) {
            player1.draws += 1;
            player2.draws += 1;
            print("It's a draw!");
        } else {
            if (player1.getName().equals(winner.getName())) {
                player1.wins += 1;
                player2.losses += 1;
            } else {
                player2.wins += 1;
                player1.losses += 1;
            }
            print(winner.getName() + " wins!");
        }
        savePlayers();
    }

    @FXML
    protected void restartGame() {
        dataAccess.resetBoard();
        for (Button button : buttons) {
            button.setDisable(false);
        }
        player1NameField.setDisable(false);
        player2NameField.setDisable(false);
        choiceBox.setDisable(false);
        print("The game has been restarted.");
        refreshScene();
    }

    protected void playersTurn() {
        if (dataAccess.isFirstPlayersTurn()) {
            p1.setText("Player 1: - Your turn");
            p1.setStyle("-fx-background-color:"
                    + " linear-gradient(from 25% 25% to 100% 100%, #32cd32, transparent)");
            p2.setText("Player 2:");
            p2.setStyle("-fx-background-color: transparent");
        } else {
            p2.setText("Player 2: - Your turn");
            p2.setStyle("-fx-background-color:"
                    + " linear-gradient(from 25% 25% to 100% 100%, #32cd32, transparent)");
            p1.setText("Player 1:");
            p1.setStyle("-fx-background-color: transparent");
        }
    }

    protected void setUpChoiceBox() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Easy",
                        "Medium",
                        "Hard",
                        "2 Player"
                );
        choiceBox.getItems().addAll(options);
        choiceBox.setValue("Easy");
        choiceBox.getSelectionModel().selectedItemProperty().addListener(
            (v, oldValue, newValue) -> changeOpponent(newValue));
    }

    protected void changeOpponent(String opponent) {
        Player placeHolder = new Player();
        placeHolder.setName(opponent);
        dataAccess.changeOpponent(placeHolder);
        refreshScene();
    }
}
