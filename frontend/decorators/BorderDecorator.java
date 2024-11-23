package frontend.decorators;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

/**Decorator to change border of components. */
class BorderDecorator extends ComponentDecorator {
    Border border;
    /**Uses ComponentDecorator constructor to initialize component. Adjusts border of window through ComponentDecorator. */
    public BorderDecorator(JComponent cmp, Color color, int thickness) {
        super(cmp);
        this.border = BorderFactory.createLineBorder(color, thickness);
        applyDecoration();
    }

    public Border getBorder() {
        return border;
    }

    @Override
    public void applyDecoration() {
        decoratedComponent.setBorder(border);
    }
}