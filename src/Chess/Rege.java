package Chess;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.abs;

public class Rege extends Piesa {

    ImageIcon imageIcon;

    public Rege(Color color) {
        super(color);
        if (color == Color.BLACK) {
            imageIcon = new ImageIcon("RegeNegru.png");
        } else {
            imageIcon = new ImageIcon("RegeAlb.png");
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
        if (casuta.getPiesa() != null) {
            if (abs(x2 - x1) <= 1 && abs(y2 - y1) <= 1 && super.getColor() != casuta.getPiesa().getColor()) {
                return true;
            }
        } else if (abs(x2 - x1) <= 1 && abs(y2 - y1) <= 1) {
            return true;
        }
        return false;
    }
}
