package persistence;

import com.google.gson.Gson;
import core.Player;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PlayerSerializer {
    private String path;
    protected static final String JSON_PATTERN = "([0-9A-Za-z-_ ]{1,32})(\\.json)";

    private void saveToFile(String data) throws IOException {
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(
                    new FileOutputStream(path, false), StandardCharsets.UTF_8);
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private void setPath(String path) {
        if (! path.matches(JSON_PATTERN)) {
            throw new IllegalArgumentException("Invalid path.");
        }
        this.path = path;
    }

    /**
     * Saves a List of Player objects as a local JSON-formatted file at the given path.
     * If the file already exists, it will be overwritten.
     * @param players       The List of Player objects to be saved.
     * @param path          The local file path to be used.
     * @throws IOException  Throws an IOException if something goes
     *                      wrong in opening or creating the file.
     */
    public void savePlayers(List<Player> players, String path) throws IOException {
        this.setPath(path);
        Gson gson = new Gson();
        String data = "";
        for (Player player : players) {
            String json = gson.toJson(player);
            data += json + "\n";
        }
        this.saveToFile(data);
    }

    /**
     * Serializes a Player object into a JSON-formatted String.
     * @param player    the Player object to be serialized.
     * @return          a JSON-formatted String.
     */
    public String serializePlayer(Player player) {
        Gson gson = new Gson();
        return gson.toJson(player);
    }

    /**
     * Serializes a List of Player objects into a JSON-formatted String.
     * @param players   the List of Player objects to be serialized.
     * @return          a JSON-formatted String.
     */
    public String serializePlayerList(List<Player> players) {
        Gson gson = new Gson();
        return gson.toJson(players);
    }
}
