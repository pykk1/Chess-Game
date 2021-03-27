package Chess;

import javax.swing.*;

public class Casuta extends JButton {

    Piesa piesa;

    public Casuta(int x, int y, Piesa piesa) {
        super();
        setLocation(x, y);
        setSize(64, 64);
        this.piesa = piesa;
        ImageIcon imageIcon = this.piesa.getImageIcon();
        setIcon(imageIcon);
    }

    public Casuta(int x, int y) {
        super();
        setLocation(x, y);
        setSize(64, 64);
        piesa = null;
    }

    public Piesa getPiesa() {
        return piesa;
    }

    public void setPiesa(Piesa piesa) {
        this.piesa = piesa;
    }

    public ImageIcon getImageIcon() {
        return this.piesa.getImageIcon();
    }


}
