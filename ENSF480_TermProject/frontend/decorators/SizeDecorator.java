package frontend.decorators;
import javax.swing.JComponent;

/**Decorator to change size of components. */
public class SizeDecorator extends ComponentDecorator {
    int width;
    int height;

    public SizeDecorator(JComponent cmp, int width, int height) {
        super(cmp);
        this.width = width;
        this.height = height;
        applyDecoration();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void applyDecoration() {
        decoratedComponent.setPreferredSize(new java.awt.Dimension(width, height));
        decoratedComponent.revalidate();
    }
}