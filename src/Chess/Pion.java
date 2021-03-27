package Chess;

import javax.swing.*;
import java.awt.*;

public class Pion extends Piesa {

    ImageIcon imageIcon;

    public Pion(Color color) {
        super(color);
        if (color == Color.BLACK) {
            imageIcon = new ImageIcon("PionNegru.png");
        } else {
            imageIcon = new ImageIcon("PionAlb.png");
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
        if (x1 == 1 && x2 == x1 + 2 && y1 == y2 && casuta.getPiesa() == null) {
            return true;
        } else if (x1 == 6 && x2 == x1 - 2 && y1 == y2 && casuta.getPiesa() == null) {
            return true;
        } else if ((x2 - x1 == 1 && y2 - y1 == 0) && super.getColor() == Color.BLACK && casuta.getPiesa() == null) {
            return true;
        } else if (x2 - x1 == -1 && y2 - y1 == 0 && super.getColor() == Color.WHITE && casuta.getPiesa() == null) {
            return true;
        } else if (((x2 - x1 == 1 && y2 - y1 == 1) || (x2 - x1 == 1 && y2 - y1 == -1)) && super.getColor() == Color.BLACK && casuta.getPiesa() != null) {
            return true;
        } else if (((x2 - x1 == -1 && y2 - y1 == -1) || (x2 - x1 == -1 && y2 - y1 == 1)) && super.getColor() == Color.WHITE && casuta.getPiesa() != null) {
            return true;
        }
        return false;
    }

}
