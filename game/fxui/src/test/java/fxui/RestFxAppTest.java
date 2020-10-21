package fxui;

import java.net.URL;
import javafx.scene.control.Button;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import restserver.GameGrizzlyApp;

public class RestFxAppTest extends AbstractFxAppTest {
    private HttpServer currentServer;

    @Override
    protected URL getFxmlResource() {
        try {
            currentServer = GameGrizzlyApp.startServer(5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getClass().getResource("/mainRest.fxml");
    }

    @Override
    protected void setDataAccess() {
        controller.setDataAccess();
        dataAccess = controller.dataAccess;
    }

    @Test
    public void testSquare() {
        Button square = controller.buttons.get(0);
        clickOn(square);
        Assert.assertEquals("X", dataAccess.getSquares().get(0).getState());
    }

    @Test
    public void testNameField() {
        Assert.assertEquals("Mr Placeholder",dataAccess.getPlayer1().getName());
        Assert.assertEquals("Mr Easy",dataAccess.getPlayer2().getName());
    }

    /**
     * Shuts down the server after testing.
     */
    @After
    public void stopServer() {
        currentServer.shutdownNow();
    }
}
