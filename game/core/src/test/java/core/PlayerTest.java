package core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private Player player;

    /**
     * Setup method, running before each test method.
     */
    @Before
    public void setUp() {
        player = new Player();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetName() {
        player.setName("Ola Nordmann");
        Assert.assertEquals("Ola Nordmann", player.getName());
        player.setName("Petter@");
    }

    @Test
    public void testToString() {
        player.setName("Tester 1");
        player.wins = 5;
        player.losses = 3;
        player.draws = 2;
        Assert.assertEquals("Tester 1 (5/3/2)", player.toString());
    }
}
