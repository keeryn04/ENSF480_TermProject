import javax.swing.*;

/**Decorator to change component details */
public abstract class ComponentDecorator extends JComponent {
    protected JComponent decoratedComponent;

    public ComponentDecorator(JComponent component) {
        this.decoratedComponent = component;
    }

    public JComponent getDecoratedComponent() {
        return decoratedComponent;
    }

    public abstract void applyDecoration();
}