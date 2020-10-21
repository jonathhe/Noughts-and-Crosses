package persistence;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import core.Square;
import java.util.List;

public class SquareSerializer {
    /**
     * Serializes a Square object into a JSON-formatted String.
     * @param square    the Square object to be serialized.
     * @return          a JSON-formatted String.
     */
    public String serializeSquare(Square square) {
        Gson gson = new Gson();
        return gson.toJson(square);
    }

    /**
     * Serializes a List of Square objects into a JSON-formatted String.
     * @param squares   the List of Square objects to be serialized.
     * @return          a JSON-formatted string.
     */
    public String serializeSquareList(List<Square> squares) {
        Gson gson = new Gson();
        return gson.toJson(squares);
    }

    /**
     * Serializes a List of Square objects as an
     * array of arrays (3x3 matrix) of JSON-formatted strings.
     * @param squares   the List of Square objects to be serialized.
     * @return          a JSON-formatted string.
     */
    public String serializeSquareListAsMatrix(List<Square> squares) {
        Gson gson = new Gson();
        String json = "[";
        List<List<Square>> lists = Lists.partition(squares, 3);
        for (List<Square> sublist : lists) {
            json += gson.toJson(sublist);
            json += ",";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";
        return json;
    }
}
