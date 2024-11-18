package frontend.decorators;
import javax.swing.JComponent;

public class PositionDecorator extends ComponentDecorator {
    private int x, y;

    public PositionDecorator(JComponent cmp, int x, int y) {
        super(cmp);
        this.x = x;
        this.y = y;
        applyDecoration();
    }

    @Override
    public void applyDecoration() {
        decoratedComponent.setBounds(x, y, decoratedComponent.getPreferredSize().width, decoratedComponent.getPreferredSize().height);
        decoratedComponent.revalidate();
    }
}
