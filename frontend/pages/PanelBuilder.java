package frontend.pages;
import java.awt.LayoutManager;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**Builds the page depending on what aspects you want for that page. Has various methods to make those components*/
public class PanelBuilder {
    private JPanel panel;
    
    /**Made a new panel to be built with PageBuilder */
    public PanelBuilder() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS)); //Default Layout
    }

    /**Sets the overall layout of the page
     * @param layoutSet Set the layout of the page to this
     */
    public PanelBuilder setLayout(LayoutManager layoutSet) {
        LayoutManager layout = layoutSet;
        panel.setLayout(layout);
        return this;
    }

    public PanelBuilder addComponent(JComponent decoratedComponent, String position) {
        panel.add(decoratedComponent, position);
        return this;
    }

    /**Build the page with the components */
    public JPanel build() {
        return panel;
    }
}