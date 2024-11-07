
import javax.swing.*;
import java.awt.*;

/**Decorator abstract that all decorators inherit. All decorators run through this to decorate panel*/
public abstract class PanelDecorator extends JPanel {
    protected JPanel decoratedPanel;

    public PanelDecorator(JPanel panel) {
        this.decoratedPanel = panel;
        setLayout(new BorderLayout());
        System.out.println("Panel Decorator");
        add(decoratedPanel, BorderLayout.CENTER);
    }
}
