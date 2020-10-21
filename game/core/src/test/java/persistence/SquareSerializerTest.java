package persistence;

import core.Square;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SquareSerializerTest {
    private SquareSerializer serializer;
    private Square s1;
    private Square s2;
    private List<Square> squares;

    /**
     * Setup method, running before each test method.
     */
    @Before
    public void setUp() {
        serializer = new SquareSerializer();
        s1 = new Square();
        s2 = new Square();
        s1.setState("X");
        squares = new ArrayList<>();
        squares.add(s1);
        squares.add(s2);
        s2.setState("O");
    }

    @Test
    public void serializeSquare() {
        String jsonString = serializer.serializeSquare(s1);
        String expectedString = "{\"selected\":\"X\"}";
        Assert.assertEquals(expectedString, jsonString);
    }

    @Test
    public void serializeSquares() {
        String jsonString = serializer.serializeSquareList(squares);
        String expectedString = "[{\"selected\":\"X\"},{\"selected\":\"O\"}]";
        Assert.assertEquals(expectedString, jsonString);
    }

    @Test
    public void serializeSquareListAsMatrix() {
        squares = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                squares.add(new Square());
            }
        }
        String json = serializer.serializeSquareListAsMatrix(squares);
        String expectedString = "[[{},{},{}],[{},{},{}],[{},{},{}]]";
        Assert.assertEquals(expectedString, json);
    }
}
