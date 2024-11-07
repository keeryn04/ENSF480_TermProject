import javax.swing.*;

/**Decorator to change button details */
class ButtonDecorator extends JButton {
    protected JButton decoratedButton;

    public ButtonDecorator(JButton button) {
        this.decoratedButton = button;
        System.out.println("Button Decorator");
    }

    public JButton getDecoratedButton() {
        return decoratedButton;
    }
}
