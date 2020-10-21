package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import core.Square;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SquareDeserializer {
    /**
     * Deserializes a Square object from a JSON-formatted string.
     * @param json  a JSON-formatted String representing a Square.
     * @return      a Square object.
     */
    public Square deserializeSquare(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Square.class);
    }

    /**
     * Deserialises a list of Square objects from a JSON-formatted String.
     * @param json  a JSON-formatted String containing a List of Square objects.
     * @return      a List of Square objects
     */
    public List<Square> deserializeSquares(String json) {
        Gson gson = new Gson();

        Type listOfSquareObject = new TypeToken<ArrayList<Square>>() {}.getType();
        return gson.fromJson(json, listOfSquareObject);
    }
}
