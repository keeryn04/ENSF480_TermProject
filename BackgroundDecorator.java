import java.awt.Color;

import javax.swing.JPanel;

/**Decorator to change background of the Window. */
class BackgroundDecorator extends PanelDecorator {
    /**Uses PanelDecorator constructor to initialize panel / component. Adjusts background of window through PanelDecorator. */
    public BackgroundDecorator(JPanel panel, Color color) {
        super(panel);
        decoratedPanel.setBackground(color);
    }
}