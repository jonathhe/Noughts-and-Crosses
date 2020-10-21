package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import core.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PlayerDeserializer {
    private String path;

    private void setPath(String path) {
        if (! path.matches(PlayerSerializer.JSON_PATTERN)) {
            throw new IllegalArgumentException("Invalid path.");
        }
        this.path = path;
    }

    private List<String> readFromFile() throws IOException {
        File file = new File(this.path);
        List<String> data = new ArrayList<>();
        if (! file.exists()) {
            return data;
        }
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), StandardCharsets.UTF_8)
                );
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            data.add(str);
        }
        bufferedReader.close();
        return data;
    }

    /**
     * Reads a local file containing JSON-formatted Player data and returns a List of
     * Player objects from it.
     * @param path          the local file path.
     * @return              any Player-objects in the local file.
     * @throws IOException  Throws an IOException if something goes wrong in opening the file.
     */
    public List<Player> loadPlayers(String path) throws IOException {
        this.setPath(path);
        List<Player> players = new ArrayList<>();
        Gson gson = new Gson();
        List<String> data = this.readFromFile();
        for (String json : data) {
            Player player = gson.fromJson(json, Player.class);
            players.add(player);
        }
        return players;
    }

    /**
     * Deserializes a Player object from a JSON-formatted String.
     * @param json  a JSON-formatted String representing a Player.
     * @return      a Player object.
     */
    public Player deserializePlayer(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Player.class);
    }

    /**
     * Deserializes a list of Player objects from a JSON-formatted String.
     * @param json  a JSON-formatted String representing a List of Player objects.
     * @return      a List of Player objects
     */
    public List<Player> deserializePlayers(String json) {
        Gson gson = new Gson();
        Type listOfPlayerObject = new TypeToken<ArrayList<Player>>() {}.getType();
        return gson.fromJson(json, listOfPlayerObject);
    }
}
