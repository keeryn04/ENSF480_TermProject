import javax.swing.JComponent;

/**Decorator to change position of components. */
public class PositionDecorator extends ComponentDecorator {
    private String position;

    public PositionDecorator(JComponent cmp, String position) {
        super(cmp);
        this.position = position;
        applyDecoration();
    }

    public String getPosition() {
        return position;
    }

    @Override
    public void applyDecoration() {}
}