package Chess;

import javax.swing.*;

public abstract class Piesa {
    private Color color;

    public Piesa(Color color) {
        this.color = color;

    }

    public Color getColor() {
        return color;
    }

    abstract ImageIcon getImageIcon();

    abstract boolean canMove(int x1, int y1, int x2, int y2, Casuta casuta);

}
