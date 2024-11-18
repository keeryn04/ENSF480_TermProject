package frontend.decorators;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**Decorator to adjust text of components. */
public class TextDecorator extends ComponentDecorator {
    String text;

    public TextDecorator(JComponent cmp, String text) {
        super(cmp);
        this.text = text;
        applyDecoration();
    }

    public String getText() {
        return text;
    }

    @Override
    public void applyDecoration() {
        if (decoratedComponent instanceof JLabel) {
            ((JLabel) decoratedComponent).setText(text);
        } else if (decoratedComponent instanceof JButton) {
            ((JButton) decoratedComponent).setText(text);  
        }
        
    }
}