package core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SquareTest {
    private Square square;

    /**
     * Setup method, running before each test method.
     */
    @Before
    public void setUp() {
        square = new Square();
    }

    @Test(expected = IllegalStateException.class)
    public void testSetState() {
        Assert.assertEquals(true, square.isAvailable());
        square.setState("x");
        Assert.assertEquals("x", square.getState());
        Assert.assertEquals(false, square.isAvailable());
        square.setState("y");
    }
}

