package fxui;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.glassfish.grizzly.http.server.HttpServer;
import restserver.GameGrizzlyApp;

public class FxApp extends Application {
    private HttpServer restServer = null;

    @Override
    public void start(final Stage stage) throws Exception {
        URI baseUri = null;
        final List<String> args = getParameters().getRaw();
        if (args.size() >= 1 && args.get(0).equals("serverOnly")) {
            restServer = GameGrizzlyApp.startServer(5);
        } else if (args.size() >= 1) {
            baseUri = URI.create(args.get(0));
            restServer = GameGrizzlyApp.startServer(5);
        }
        if (args.size() == 0 || ! args.get(0).equals("serverOnly")) {
            final String fxml = (baseUri != null ? "/mainRest.fxml" : "/main.fxml");
            final Parent root = FXMLLoader.load(getClass().getResource(fxml));
            final Scene scene = new Scene(root, 800, 550);
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void stop() throws Exception {
        if (restServer != null) {
            restServer.shutdown();
        }
        super.stop();
    }

    /**
     * Launches the app.
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        // only needed on ios
        System.setProperty("os.target", "ios");
        System.setProperty("os.name", "iOS");
        System.setProperty("glass.platform", "ios");
        System.setProperty("targetos.name", "iOS");
        launch(args);
    }
}

