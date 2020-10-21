package fxui;

import com.fasterxml.jackson.core.JsonProcessingException;

import core.Player;
import core.Square;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.PlayerDeserializer;
import persistence.PlayerSerializer;
import persistence.SquareDeserializer;


public class RestGameDataAccess implements GameDataAccess {
    private final String baseUrlString;
    SquareDeserializer squareDeserializer = new SquareDeserializer();
    PlayerSerializer playerSerializer = new PlayerSerializer();
    PlayerDeserializer playerDeserializer = new PlayerDeserializer();

    private static final Logger logger = Logger.getLogger(RestGameDataAccess.class.getName());

    private URI getRequestUri(final String path) {
        try {
            return new URI(baseUrlString + path);
        } catch (final URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * A constructor for the DataAccess-layer. This translates
     * requests between frontend and backend.
     * @param baseUrlString The address of the web server to be used.
     */
    public RestGameDataAccess(final String baseUrlString) {
        this.baseUrlString = baseUrlString;
    }

    @Override
    public void setPlayer1(Player player) {
        String serializedPlayer = playerSerializer.serializePlayer(player);
        try {
            final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/player/1"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(serializedPlayer))
                    .build();
            HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPlayer2(Player player) {
        String serializedPlayer = playerSerializer.serializePlayer(player);
        try {
            final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/player/2"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(serializedPlayer))
                    .build();
            HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Player requestHelper(HttpRequest request) {
        try {
            final HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return playerDeserializer.deserializePlayer(response.body());
        } catch (Exception e) {
            logging(e);
        }
        return null;
    }

    @Override
    public Player getPlayer1() {
        final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/player/1"))
                .header("Accept", "application/json")
                .GET()
                .build();
        return requestHelper(request);
    }

    @Override
    public Player getPlayer2() {
        final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/player/2"))
                .header("Accept", "application/json")
                .GET()
                .build();
        return requestHelper(request);
    }

    @Override
    public Player getWinner() {
        final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/winner"))
                .header("Accept", "application/json")
                .GET()
                .build();
        return requestHelper(request);
    }

    @Override
    public Player getLoser() {
        final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/loser"))
                .header("Accept", "application/json")
                .GET()
                .build();
        return requestHelper(request);
    }

    @Override
    public List<Square> getSquares() {
        final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/squares"))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            final HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return squareDeserializer.deserializeSquares(response.body());
        } catch (Exception e) {
            logging(e);
        }
        return null;
    }

    @Override
    public boolean isFirstPlayersTurn() {
        final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/playersTurn"))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            final HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return 0 == response.body().compareTo("player1");
        } catch (Exception e) {
            logging(e);
        }
        return false;
    }

    @Override
    public void doMove(int index) {
        final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/move/" + index))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logging(e);
        }
    }

    @Override
    public void resetBoard() {
        final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/reset"))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            logging(e);
        }
    }

    @Override
    public boolean isGameOver() {
        final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/over"))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            final HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return 0 == response.body().compareTo("over");
        } catch (Exception e) {
            logging(e);
        }
        return false;
    }

    @Override
    public boolean isGameStarted() {
        final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/started"))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            final HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return 0 == response.body().compareTo("started");
        } catch (Exception e) {
            logging(e);
        }
        return false;
    }

    @Override
    public void changeOpponent(Player opponent) {
        String serializedPlayer = playerSerializer.serializePlayer(opponent);
        try {
            final HttpRequest request = HttpRequest.newBuilder(getRequestUri("/changeOpponent"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(serializedPlayer))
                    .build();
            HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void logging(Exception e) {
        try {
            logger.addHandler(new FileHandler("logger.log", true));
            logger.setLevel(Level.ALL);
            logger.log(Level.SEVERE, "An exception has been thrown", e);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
