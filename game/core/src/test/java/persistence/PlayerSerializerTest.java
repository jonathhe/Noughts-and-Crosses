package persistence;

import core.Player;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerSerializerTest {
    PlayerSerializer serializer;
    Player player1;
    Player player2;
    List<Player> players;

    /**
     * Setup method, running before each test method.
     */
    @Before
    public void setUp() {
        serializer = new PlayerSerializer();
        player1 = new Player();
        player2 = new Player();
        player1.setName("Player One");
        player2.setName("Player Two");
        player1.wins = 5;
        player1.draws = 1;
        player2.losses = 5;
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
    }

    @Test
    public void serializePlayer() {
        String jsonString = serializer.serializePlayer(player1);
        String expectedString = "{\"wins\":5,\"draws\":1,\"losses\":0,\"name\":\"Player One\"}";
        Assert.assertEquals(expectedString,jsonString);
    }

    @Test
    public void serializePlayers() {
        String jsonString = serializer.serializePlayerList(players);
        String expectedString = "[{\"wins\":5,\"draws\":1,\"losses\":0,\"name\":\"Player One\"}"
                +  ",{\"wins\":0,\"draws\":0,\"losses\":5,\"name\":\"Player Two\"}]";
        Assert.assertEquals(expectedString,jsonString);
    }
}
