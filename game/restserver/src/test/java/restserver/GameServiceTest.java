package restserver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import core.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.*;

import core.Board;

import persistence.PlayerDeserializer;
import persistence.PlayerSerializer;
import persistence.SquareDeserializer;
import persistence.SquareSerializer;
import restapi.GameService;

public class GameServiceTest extends SimpleExJerseyTest {

    private static final String GAME_SERVICE_PATH = GameService.GAME_SERVICE_PATH;
    String baseUrlString = "http://localhost:8080/";

    SquareSerializer squareSerializer;
    SquareDeserializer squareDeserializer;
    PlayerSerializer playerSerializer;
    PlayerDeserializer playerDeserializer;
    Board board;
    Player player1;
    Player player2;
    Player player3;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        player1 = new Player();
        player1.setSymbol("X");
        player2 = new Player();
        player2.setSymbol("O");
        player3 = new Player();
        player3.setName("Player3");

        board = new Board();
        board.setPlayer1(player1);
        board.setPlayer2(player2);
        squareSerializer = new SquareSerializer();
        squareDeserializer = new SquareDeserializer();
        playerSerializer = new PlayerSerializer();
        playerDeserializer = new PlayerDeserializer();
    }


    private URI getRequestUri(final String path) {
        try {
            return new URI(baseUrlString + path);
        } catch (final URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    public void testEstablishConnection()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        // GET
        final Response response = target(GAME_SERVICE_PATH).path("")
                .request("application/json; charset=UTF-8")
                .get();
        Assert.assertEquals(200, response.getStatus());
    }


    @Test
    public void testSetBoard()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        testGetSquares();
        testIsGameOver();
        testIsGameStarted();
        testDoMove();
        testPlayersTurn();
        testSetPlayer1();
        testGetPlayer();
        testGetWinner();
        testGetLoser();
        testReset();
    }






    public void testGetSquares()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        // GET
        final Response response = target(GAME_SERVICE_PATH).path("/squares")
                .request("application/json; charset=UTF-8")
                .get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(squareSerializer.serializeSquareList(board.getSquares()), response.readEntity(String.class));
    }

    public void testIsGameOver()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        // GET
        final Response response = target(GAME_SERVICE_PATH).path("/over")
                .request("application/json; charset=UTF-8")
                .get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("notOver", response.readEntity(String.class));
    }

    public void testIsGameStarted()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        // GET
        final Response response = target(GAME_SERVICE_PATH).path("/started")
                .request("application/json; charset=UTF-8")
                .get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("notStarted", response.readEntity(String.class));
    }
    public void testDoMove()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        // GET
        board.doMove(1);
        final Response response = target(GAME_SERVICE_PATH).path("/move/1")
                .request("application/json; charset=UTF-8")
                .get();
        Assert.assertEquals(204, response.getStatus());
    }
    public void testSetPlayer1()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        board.setPlayer1(player3);
        final Response putResponse = target(GAME_SERVICE_PATH).path("/player/1")
                .request("application/json; charset=UTF-8")
                .post(Entity.entity(playerSerializer.serializePlayer(player3), MediaType.APPLICATION_JSON));
        Assert.assertEquals(204, putResponse.getStatus());
    }

    public void testGetPlayer()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        // GET
        final Response response = target(GAME_SERVICE_PATH).path("/player/1")
                .request("application/json; charset=UTF-8")
                .get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(board.getPlayer1().getName(), playerDeserializer.deserializePlayer(response.readEntity(String.class)).getName());
    }

    public void testPlayersTurn()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        // GET
        final Response response = target(GAME_SERVICE_PATH).path("/playersTurn")
                .request("application/json; charset=UTF-8")
                .get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("player1", response.readEntity(String.class));
    }
    public void testGetWinner()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        // GET
        final Response response = target(GAME_SERVICE_PATH).path("/winner")
                .request("application/json; charset=UTF-8")
                .get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("null", response.readEntity(String.class));
    }
    public void testGetLoser()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        // GET
        final Response response = target(GAME_SERVICE_PATH).path("/loser")
                .request("application/json; charset=UTF-8")
                .get();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("null", response.readEntity(String.class));
    }
    public void testReset()throws MalformedURLException, IOException, ProtocolException, JsonParseException, JsonMappingException {
        //GET
        board.resetBoard();
        final Response response = target(GAME_SERVICE_PATH).path("/reset")
                .request("application/json; charset=UTF-8")
                .get();
        Assert.assertEquals(204, response.getStatus());
    }
}
