package fxui;

import java.io.IOException;
import javafx.fxml.FXML;
import persistence.PlayerDeserializer;
import persistence.PlayerSerializer;

public class RestController extends AbstractController {

    /**
     * Empty constructor required for REST-API.
     */
    public RestController(){

    }

    private static final String playerFile = "allPlayers.json";

    @FXML
    protected void initialize() {
        generateBoard();
        setDataAccess();
        loadPlayers(playerFile);
        super.initialize();
    }

    protected void setDataAccess() {
        this.dataAccess = new RestGameDataAccess("http://localhost:8080/game");
    }

    // Reads stored player names from file
    protected void loadPlayers(String playerFile) {
        PlayerDeserializer loader = new PlayerDeserializer();
        try {
            this.allPlayers = loader.loadPlayers(playerFile);
        } catch (IOException e) {
            print(e.getMessage());
        }
    }

    // Saves player names and stats to file
    protected void savePlayers() {
        PlayerSerializer saver = new PlayerSerializer();
        try {
            saver.savePlayers(this.allPlayers, playerFile);
        } catch (IOException e) {
            print(e.getMessage());
        }
    }
}
