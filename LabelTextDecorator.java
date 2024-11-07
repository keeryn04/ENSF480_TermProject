import javax.swing.JLabel;

public class LabelTextDecorator extends LabelDecorator {
    public LabelTextDecorator(JLabel label, String text) {
        super(label);
        decoratedLabel.setText(text);
    }
}