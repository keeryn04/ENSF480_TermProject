import javax.swing.*;

/**Decorator to change button details */
class LabelDecorator extends JLabel {
    protected JLabel decoratedLabel;

    public LabelDecorator(JLabel label) {
        this.decoratedLabel = label;
        System.out.println("Label Decorator");
    }
}
