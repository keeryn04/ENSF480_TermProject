import java.awt.Color;

import javax.swing.JComponent;

/**Decorator to change foreground of components. */
public class ForegroundColorDecorator extends ComponentDecorator {
    Color color;
    /**Uses ComponentDecorator constructor to initialize component. Adjusts foreground of object through ComponentDecorator. */
    public ForegroundColorDecorator(JComponent cmp, Color color) {
        super(cmp);
        this.color = color;
        applyDecoration();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void applyDecoration() {
        decoratedComponent.setForeground(color);
        decoratedComponent.setOpaque(true);
    }
}

