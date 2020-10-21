package restserver;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import restapi.GameService;
import core.Board;

import java.io.IOException;


public class GameConfig extends ResourceConfig {
    Board board = new Board();

    public GameConfig() {
        register(GameService.class);
        register(JacksonFeature.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(board);
            }
        });
    }

}