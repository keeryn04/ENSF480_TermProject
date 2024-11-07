
import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**Builds the page depending on what aspects you want for that page. Has various methods to make those components*/
public class PageBuilder {
    private JPanel panel;
    
    /**Made a new panel to be built with PageBuilder */
    public PageBuilder() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
    }

    /**Sets the overall layout of the page
     * @param layoutSet Set the layout of the page to this
     */
    public PageBuilder setLayout(LayoutManager layoutSet) {
        LayoutManager layout = layoutSet;
        panel.setLayout(layout);
        return this;
    }

    public PageBuilder addComponent(JComponent decoratedComponent, String position) {
        panel.add(decoratedComponent, position);
        return this;
    }

    /**Build the page with the components */
    public JPanel build() {
        return panel;
    }
}
