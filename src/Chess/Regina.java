package Chess;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.abs;

public class Regina extends Piesa {

    ImageIcon imageIcon;

    public Regina(Color color) {
        super(color);
        if (color == Color.BLACK) {
            imageIcon = new ImageIcon("ReginaNegru.png");
        } else {
            imageIcon = new ImageIcon("ReginaAlb.png");
        }
        Image image = this.imageIcon.getImage();
        Image image1 = image.getScaledInstance(64, 60, Image.SCALE_SMOOTH);
        this.imageIcon = new ImageIcon(image1);
    }

    @Override
    ImageIcon getImageIcon() {
        return this.imageIcon;
    }

    @Override
    boolean canMove(int x1, int y1, int x2, int y2, Casuta casuta) {
        if (((abs(x2 - x1) > 0 && y1 == y2) || (x1 == x2 && abs(y2 - y1) > 0))
                || (abs(x2 - x1) >= 1) && (abs(y2 - y1) >= 1) && (abs(x2 - x1) == abs(y2 - y1))) {
            return true;
        }
        return false;
    }

}
