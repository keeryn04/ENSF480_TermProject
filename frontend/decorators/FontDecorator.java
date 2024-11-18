package frontend.decorators;
import java.awt.Font;

import javax.swing.JComponent;

/**Decorator to change font of components. */
public class FontDecorator extends ComponentDecorator {
    Font font;

    public FontDecorator(JComponent cmp, Font font) {
        super(cmp);
        this.font = font;
        applyDecoration();
    }

    public Font getFont() {
        return font;
    }

    @Override
    public void applyDecoration() {
        decoratedComponent.setFont(font);
        decoratedComponent.revalidate();
    }
}