package fxui;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public abstract class AbstractFxAppTest extends ApplicationTest {

    /**
     * Sets up required properties before testing begins.
     */
    @BeforeClass
    public static void headless() {
        if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
            System.setProperty("java.awt.headless", "true");
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("glass.platform", "Monocle");
            System.setProperty("monocle.platform", "Headless");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("testfx.setup.timeout", "2500");
        }
    }

    protected AbstractController controller;
    protected GameDataAccess dataAccess;

    protected abstract URL getFxmlResource();

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getFxmlResource());
        final Parent root = loader.load();
        this.controller = loader.getController();
        setDataAccess();
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    protected abstract void setDataAccess();

    @Test
    public void testController() {
        Assert.assertNotNull(this.controller);
    }

}
