package frontend.decorators;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ActionListenerDecorator extends ComponentDecorator {
    JButton button;

    /**Uses ComponentDecorator constructor to initialize component. Sets ActionListener to specified button through ComponentDecorator. 
     * @param cmp Component to add listener to
     * @param button Button Component with listener
     * @param listener ActionListener object that is added to component
    */
    public ActionListenerDecorator(JComponent cmp, JButton button, ActionListener listener) {
        super(cmp);
        this.button = button;
        applyDecoration(listener);
    }

    public void applyDecoration(ActionListener listener) {
        button.addActionListener(listener);
    }

    @Override
    public void applyDecoration() {
        throw new UnsupportedOperationException("Unimplemented method 'applyDecoration'");
    }
}