package fxui;

import core.Square;
import java.net.URL;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.Assert;
import org.junit.Test;

public class FxAppTest extends AbstractFxAppTest {

    @Override
    protected URL getFxmlResource() {
        return getClass().getResource("/main.fxml");
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
        Assert.assertEquals("X",square.getText());
    }

    @Test
    public void testNameField() {
        TextField nameField = controller.player1NameField;
        Assert.assertEquals("Mr Placeholder",nameField.getText());
        doubleClickOn(nameField);
        write("test");
        Assert.assertEquals("Mr test",nameField.getText());
    }

    @Test
    public void testReset() {
        int square = 0;
        try {
            while (square < 9 && ! dataAccess.isGameOver()) {
                clickOn(controller.buttons.get(square));
                if (square < 9) {
                    square += 1;
                }
            }
            clickOn(controller.restartButton);
            for (Square s : controller.squares) {
                if (s.getState() != null) {
                    Assert.fail();
                }
            }
            Assert.assertTrue(dataAccess.isFirstPlayersTurn());
        } catch (Exception e) {
            System.out.println(e);
            Assert.fail();
        }
    }

    @Test
    public void testSeeSquares() {
        for (Square s : dataAccess.getSquares()) {
            if (s.getState() != null) {
                Assert.fail();
            }
        }
        Button square = controller.buttons.get(0);
        clickOn(square);
        Assert.assertEquals("X",dataAccess.getSquares().get(0).getState());
    }
}
