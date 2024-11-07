import javax.swing.JButton;

public class ButtonSizeDecorator extends ButtonDecorator {
    public ButtonSizeDecorator(JButton button, int width, int height) {
        super(button);
        decoratedButton.setSize(width, height);
    }
}