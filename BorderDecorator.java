import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**Decorator to change border of the Window. */
class BorderDecorator extends PanelDecorator {
    /**Uses PanelDecorator constructor to initialize panel / component. Adjusts border of window through PanelDecorator. */
    public BorderDecorator(JPanel panel, Color color, int thickness) {
        super(panel);
        Border border = BorderFactory.createLineBorder(color, thickness);
        decoratedPanel.setBorder(border);
    }
}