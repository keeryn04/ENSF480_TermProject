
import javax.swing.JPanel;

/**Interface for each page. Requires creation of page when implemented*/
public interface Page {
    /**Implemented to create a page instance, specified by the derived class */
    JPanel createPage();
}
