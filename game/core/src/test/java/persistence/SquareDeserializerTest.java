package persistence;

import core.Square;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SquareDeserializerTest {
    private SquareDeserializer deserializer;
    private Square s1;
    private List<Square> squares;

    /**
     * Setup method, running before each test method.
     */
    @Before
    public void setUp() {
        deserializer = new SquareDeserializer();
    }

    @Test
    public void deserializeSquare() {
        String jsonString = "{\"selected\":\"X\"}";
        s1 = deserializer.deserializeSquare(jsonString);
        Assert.assertEquals("X", s1.getState());
    }

    @Test
    public void deserializeSquares() {
        String jsonString = "[{\"selected\":\"X\"},{\"selected\":\"O\"}]";
        squares = deserializer.deserializeSquares(jsonString);
        Assert.assertEquals("X", squares.get(0).getState());
        Assert.assertEquals(2, squares.size());
    }

}
