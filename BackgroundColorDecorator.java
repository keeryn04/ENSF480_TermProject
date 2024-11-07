import java.awt.Color;

import javax.swing.JComponent;

/**Decorator to change background of components. */
class BackgroundColorDecorator extends ComponentDecorator {
    Color color;
    /**Uses ComponentDecorator constructor to initialize component. Adjusts background of component through ComponentDecorator. */
    public BackgroundColorDecorator(JComponent cmp, Color color) {
        super(cmp);
        this.color = color;
        applyDecoration();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void applyDecoration() {
        decoratedComponent.setBackground(color);
        decoratedComponent.setOpaque(true);
    }
}