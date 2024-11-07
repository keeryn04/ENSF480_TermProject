import java.awt.Color;
import javax.swing.JButton;

public class ButtonColorDecorator extends ButtonDecorator {
    public ButtonColorDecorator(JButton button, Color color) {
        super(button);
        decoratedButton.setBackground(color);
    }
}
