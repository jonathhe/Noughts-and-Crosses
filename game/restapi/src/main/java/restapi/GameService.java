package restapi;

import core.Board;
import core.Player;
import org.glassfish.jersey.server.ResourceConfig;
import persistence.PlayerSerializer;
import persistence.SquareSerializer;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

@Path("/game")
public class GameService extends ResourceConfig {

    SquareSerializer squareSerializer = new SquareSerializer();
    PlayerSerializer playerSerializer = new PlayerSerializer();

    public static final String GAME_SERVICE_PATH = "game";

    @Inject
    private Board board;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String connectionEstablished() {
        return "Connection Established";
    }

    @GET
    @Path("/squares")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSquares() {
        return squareSerializer.serializeSquareList(board.getSquares());
    }

    @GET
    @Path("/over")
    @Produces(MediaType.APPLICATION_JSON)
    public String isGameOver() {
        if(board.isGameOver()){
            return "over";
        }else{
            return "notOver";
        }
    }

    @GET
    @Path("/started")
    @Produces(MediaType.APPLICATION_JSON)
    public String isGameStarted() {
        if(board.isGameStarted()){
            return "started";
        }else{
            return "notStarted";
        }
    }

    @GET
    @Path("/player/{num}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPlayer(@PathParam("num") int num) {
        if(num == 1){
            return playerSerializer.serializePlayer(board.getPlayer1());
        } else{
            return playerSerializer.serializePlayer(board.getPlayer2());
        }
    }

    @GET
    @Path("/winner")
    @Produces(MediaType.APPLICATION_JSON)
    public String getWinner() {
        return playerSerializer.serializePlayer(board.getWinner());
    }

    @GET
    @Path("/loser")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLoser() {
        return playerSerializer.serializePlayer(board.getLoser());
    }

    @GET
    @Path("/move/{num}")
    public void doMove(@PathParam("num") int num) {
        board.doMove(num);
    }

    @GET
    @Path("/reset")
    @Produces(MediaType.APPLICATION_JSON)
    public void reset() {
        board.resetBoard();
    }

    @GET
    @Path("/resetBoard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response resetBoard() {
        board.resetBoard();
        return Response.ok(boardState()).header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/player/{num}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void setPlayer(Player player, @PathParam("num") int num) {
        if(num == 1){
            board.setPlayer1(player);
        } else{
            board.setPlayer2(player);
        }
    }

    @GET
    @Path("/playersTurn")
    @Produces(MediaType.APPLICATION_JSON)
    public String isFirstPlayerTurn() {
        if (board.isFirstPlayersTurn()) {
            return "player1";
        } else {
            return "player2";
        }
    }

    @POST
    @Path("/changeOpponent")
    @Consumes(MediaType.APPLICATION_JSON)
    public void changeOpponent(Player opponent) {
        board.changeOpponent(opponent.getName());
    }

    @GET
    @Path("/clientBoard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response board(){
        return Response.ok(boardState()).header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/sendMove/{num}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMove(@PathParam("num") int num){
        board.doMove(num);
        return Response.ok(boardState()).header("Access-Control-Allow-Origin", "*").build();
    }


    @POST
    @Path("/changeName/{num}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response name(@PathParam("name") String name, @PathParam("num") int num){
        if(num == 1) {
            board.getPlayer1().setName(name);
        }
        else {
            if(name.equals("Easy") || name.equals("Medium") || name.equals("Hard") || name.equals("2 Player")){
                board.changeOpponent(name);
            }else{
                board.getPlayer2().setName(name);
            }
        }
        return Response.ok(boardState()).header("Access-Control-Allow-Origin", "*").build();
    }

    private String boardState() {
        String json = "{";
        json += "\"board\":";
        json += squareSerializer.serializeSquareListAsMatrix(board.getSquares());
        json += ",";
        if(board.isFirstPlayersTurn()) {
            json += "\"isFirstPlayerTurn\":true,";
        } else {
            json += "\"isFirstPlayerTurn\":false,";
        }
        if(board.isGameOver()) {
            json += "\"isGameOver\":true,";
        } else {
            json += "\"isGameOver\":false,";
        }
        if(board.getWinner() != null) {
            json += "\"winner\":"+"\""+board.getWinner().getName()+"\""+",";
        } else {
            json += "\"winner\":"+""+null+""+",";
        }

        json += "\"player1\":"+"\""+board.getPlayer1().getName()+"\""+",";
        json += "\"player2\":"+"\""+board.getPlayer2().getName()+"\""+"}";
        return json;
    }

}

