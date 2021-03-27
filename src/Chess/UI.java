package Chess;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Math.abs;

public class UI extends JFrame {
    static Casuta[][] casute = new Casuta[8][8];
    static Piesa piesaSelectata;
    static Piesa piesaInitialaCheck;
    static boolean selectat = false;
    static int x1;
    static int y1;
    static JFrame frame;
    static JLabel tabla;
    static int xRegeAlb = 7;
    static int yRegeAlb = 4;
    static int xRegeNegru = 0;
    static int yRegeNegru = 4;
    static boolean whiteTurn = true;
    static boolean whiteChecked = false;
    static boolean blackChecked = false;
    static boolean checkOK = true;

    public UI() {
        frame = new JFrame();
        tabla = new JLabel(new ImageIcon("tabla.jpg"));
        tabla.setBounds(0, 0, 823, 820);
        frame.setBounds(0, 0, 839, 859);

        JPanel butoane = new JPanel();
        butoane.setLayout(null);
        int location = 24;
        int rows = 23;
        int increment = 98;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casuta casuta;
                if (i == 1)
                    casuta = new Casuta(location + (j * increment), rows, new Pion(Color.BLACK));
                else if ((i == 0 && j == 0) || (i == 0 && j == 7))
                    casuta = new Casuta(location + (j * increment), rows, new Tura(Color.BLACK));
                else if ((i == 0 && j == 1) || (i == 0 & j == 6))
                    casuta = new Casuta(location + (j * increment), rows, new Cal(Color.BLACK));
                else if (i == 0 && j == 2 || i == 0 & j == 5)
                    casuta = new Casuta(location + (j * increment), rows, new Nebun(Color.BLACK));
                else if (i == 0 && j == 3)
                    casuta = new Casuta(location + (j * increment), rows, new Regina(Color.BLACK));
                else if (i == 0 && j == 4)
                    casuta = new Casuta(location + (j * increment), rows, new Rege(Color.BLACK));

                else if (i == 6)
                    casuta = new Casuta(location + (j * increment), rows, new Pion(Color.WHITE));
                else if (i == 7 && j == 0 || i == 7 && j == 7)
                    casuta = new Casuta(location + (j * increment), rows, new Tura(Color.WHITE));
                else if (i == 7 && j == 1 || i == 7 & j == 6)
                    casuta = new Casuta(location + (j * increment), rows, new Cal(Color.WHITE));
                else if (i == 7 && j == 2 || i == 7 & j == 5)
                    casuta = new Casuta(location + (j * increment), rows, new Nebun(Color.WHITE));
                else if (i == 7 && j == 3)
                    casuta = new Casuta(location + (j * increment), rows, new Regina(Color.WHITE));
                else if (i == 7 && j == 4)
                    casuta = new Casuta(location + (j * increment), rows, new Rege(Color.WHITE));
                else casuta = new Casuta(location + (j * increment), rows);
                casuta.addActionListener(new tabla());
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) {
                    casuta.setBackground(new java.awt.Color(240, 217, 181));
                } else {
                    casuta.setBackground(new java.awt.Color(181, 136, 99));
                }
                casuta.setSize(98, 98);
                casute[i][j] = casuta;
                tabla.add(casute[i][j]);
            }
            rows += 98;
        }
        frame.add(tabla);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static class tabla implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++) {
                    if (e.getSource() == casute[i][j]) {
                        if (((casute[i][j] == casute[x1][y1]) && selectat)     //Deselectare la dublu click sau daca piesa nu se poate muta
                                || (selectat && !piesaSelectata.canMove(x1, y1, i, j, casute[i][j]))
                                || (selectat && isBlocked(x1, y1, i, j, casute[x1][y1]))
                                || (selectat && !rightTurn(piesaSelectata.getColor()))) {
                            selectat = false;
                            piesaSelectata = null;
                            setColor(x1, y1);
                        } else if (casute[i][j].getPiesa() != null && !selectat) { //Selectare piesa+colorare
                            piesaSelectata = casute[i][j].getPiesa();
                            casute[i][j].setBackground(java.awt.Color.blue);
                            selectat = true;
                            x1 = i;
                            y1 = j;
                        } else if (casute[i][j].getPiesa() == null && selectat
                                && !isBlocked(x1, y1, i, j, casute[x1][y1])
                                && rightTurn(piesaSelectata.getColor())) { //Piesa selectata si mutata pe casuta goala
                            if (piesaSelectata.canMove(x1, y1, i, j, casute[i][j])) {
                                if (piesaSelectata instanceof Rege) {
                                    if (piesaSelectata.getColor() == Color.BLACK) {
                                        xRegeNegru = i;
                                        yRegeNegru = j;
                                    } else {
                                        xRegeAlb = i;
                                        yRegeAlb = j;
                                    }
                                }
                                changePlayer();
                                casute[i][j].setPiesa(piesaSelectata);
                                casute[i][j].setIcon(casute[i][j].getImageIcon());
                                setColor(i, j);
                                casute[x1][y1].setIcon(null);
                                casute[x1][y1].setPiesa(null);
                                setColor(x1, y1);
                                if (!hasCheckedOk(piesaSelectata.getColor())) { //ROLLBACK
                                    if (piesaSelectata instanceof Rege) {
                                        if (piesaSelectata.getColor() == Color.BLACK) {
                                            xRegeNegru = x1;
                                            yRegeNegru = y1;
                                        } else {
                                            xRegeAlb = x1;
                                            yRegeAlb = y1;
                                        }
                                    }
                                    changePlayer();
                                    casute[x1][y1].setPiesa(piesaSelectata);
                                    casute[x1][y1].setIcon(piesaSelectata.getImageIcon());
                                    casute[i][j].setIcon(null);
                                    casute[i][j].setPiesa(null);
                                    setColor(i, j);
                                    setColor(x1, y1);
                                    selectat = false;
                                }
                            }
                            selectat = false;
                            setColor(x1, y1);
                        } else if (casute[i][j].getPiesa() != null && selectat) { //Piesa selectata si mutata pe casuta ocupata
                            if (casute[i][j].getPiesa().getColor() == casute[x1][y1].getPiesa().getColor()) { //Daca se vrea mutarea pe o casuta unde se afla o piesa de aceeasi culoare
                                piesaSelectata = null;
                                selectat = false;
                                setColor(x1, y1);
                            } else if (piesaSelectata.canMove(x1, y1, i, j, casute[i][j])
                                    && !isBlocked(x1, y1, i, j, casute[x1][y1])
                                    && rightTurn(piesaSelectata.getColor())) { //Piesa se poate muta
                                changePlayer();
                                if (piesaSelectata instanceof Rege) {
                                    if (piesaSelectata.getColor() == Color.BLACK) {
                                        xRegeNegru = i;
                                        yRegeNegru = j;
                                    } else {
                                        xRegeAlb = i;
                                        yRegeAlb = j;
                                    }
                                } // Setare noi coordonate daca se muta regele
                                piesaInitialaCheck = casute[i][j].getPiesa();
                                casute[i][j].setPiesa(piesaSelectata);
                                casute[i][j].setIcon(casute[i][j].getImageIcon());
                                casute[x1][y1].setIcon(null);
                                casute[x1][y1].setPiesa(null);
                                setColor(i, j);
                                setColor(x1, y1);
                                selectat = false;
                                if (!hasCheckedOk(piesaSelectata.getColor())) { //ROLLBACK
                                    if (piesaSelectata instanceof Rege) {
                                        if (piesaSelectata.getColor() == Color.BLACK) {
                                            xRegeNegru = x1;
                                            yRegeNegru = y1;
                                        } else {
                                            xRegeAlb = x1;
                                            yRegeAlb = y1;
                                        }
                                    }
                                    changePlayer();
                                    casute[x1][y1].setPiesa(piesaSelectata);
                                    casute[x1][y1].setIcon(piesaSelectata.getImageIcon());
                                    casute[i][j].setIcon(piesaInitialaCheck.getImageIcon());
                                    casute[i][j].setPiesa(piesaInitialaCheck);
                                    setColor(i, j);
                                    setColor(x1, y1);
                                    selectat = false;
                                }

                            }
                        }
                        break;
                    }
                }

        }
    }

    public static void setColor(int i, int j) {
        if ((i + j) % 2 == 0) {
            casute[i][j].setBackground(new java.awt.Color(240, 217, 181));
        } else casute[i][j].setBackground(new java.awt.Color(181, 136, 99));
    }

    public static boolean isBlocked(int x1, int y1, int x2, int y2, Casuta casuta) {
        if (casuta.getPiesa() instanceof Tura) {
            if (y1 == y2) { // Tura sus-jos
                if (x2 > x1) {
                    if (x2 - x1 == 1) {
                        return false;
                    }
                    for (int i = x1 + 1; i < x2; i++) {
                        if (casute[i][y1].getPiesa() != null) {
                            return true;
                        }
                    }
                } else if (x1 - x2 == 1) {
                    return false;
                }
                for (int i = x2 + 1; i < x1; i++) {
                    if (casute[i][y1].getPiesa() != null) {
                        return true;
                    }
                }
            } else if (x1 == x2) { // Tura stanga-dreapta
                if (y2 > y1) {
                    if (y2 - y1 == 1) {
                        return false;
                    }
                    for (int i = y1 + 1; i < y2; i++) {
                        if (casute[x1][i].getPiesa() != null) {
                            return true;
                        }
                    }
                } else if (y1 - y2 == 1) {
                    return false;
                }
                for (int i = y2 + 1; i < y1; i++) {
                    if (casute[x1][i].getPiesa() != null) {
                        return true;
                    }
                }
            }
        } else if (casuta.getPiesa() instanceof Nebun) {

            if (x2 > x1 && y2 > y1) {
                for (int i = x1 + 1, j = y1 + 1; i < x2; i++, j++) {
                    if (casute[i][j].getPiesa() != null) {
                        return true;
                    }
                }
            } else if (x1 > x2 && y1 > y2) {
                for (int i = x2 + 1, j = y2 + 1; i < x1; i++, j++) {
                    if (casute[i][j].getPiesa() != null) {
                        return true;
                    }
                }
            } else if (x2 > x1 && y1 > y2) {
                for (int i = x1 + 1, j = y1 - 1; i < x2; i++, j--) {
                    if (casute[i][j].getPiesa() != null) {
                        return true;
                    }
                }
            } else if (x1 > x2 && y2 > y1) {
                for (int i = x2 + 1, j = y2 - 1; i < x1; i++, j--) {
                    if (casute[i][j].getPiesa() != null) {
                        return true;
                    }
                }
            }
        } else if (casuta.getPiesa() instanceof Regina) {
            if (y1 == y2) {
                if (x2 > x1) {
                    if (x2 - x1 == 1) {
                        return false;
                    }
                    for (int i = x1 + 1; i < x2; i++) {
                        if (casute[i][y1].getPiesa() != null) {
                            return true;
                        }
                    }
                } else if (x1 - x2 == 1) {
                    return false;
                }
                for (int i = x2 + 1; i < x1; i++) {
                    if (casute[i][y1].getPiesa() != null) {
                        return true;
                    }
                }
            } else if (x1 == x2) {
                if (y2 > y1) {
                    if (y2 - y1 == 1) {
                        return false;
                    }
                    for (int i = y1 + 1; i < y2; i++) {
                        if (casute[x1][i].getPiesa() != null) {
                            return true;
                        }
                    }
                } else if (y1 - y2 == 1) {
                    return false;
                }
                for (int i = y2 + 1; i < y1; i++) {
                    if (casute[x1][i].getPiesa() != null) {
                        return true;
                    }
                }
            } else if (x2 > x1 && y2 > y1) {
                for (int i = x1 + 1, j = y1 + 1; i < x2; i++, j++) {
                    if (casute[i][j].getPiesa() != null) {
                        return true;
                    }
                }
            } else if (x1 > x2 && y1 > y2) {
                for (int i = x2 + 1, j = y2 + 1; i < x1; i++, j++) {
                    if (casute[i][j].getPiesa() != null) {
                        return true;
                    }
                }
            } else if (x2 > x1 && y1 > y2) {
                for (int i = x1 + 1, j = y1 - 1; i < x2; i++, j--) {
                    if (casute[i][j].getPiesa() != null) {
                        return true;
                    }
                }
            } else if (x1 > x2 && y2 > y1) {
                for (int i = x2 + 1, j = y2 - 1; i < x1; i++, j--) {
                    if (casute[i][j].getPiesa() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean hasCheckedOk(Color color) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (color == Color.WHITE) { //Randul lui alb
                    //if vreo piesa alba canMove && !isBlocked la regeNegru return true;
                    if (casute[i][j].getPiesa() != null
                            && casute[i][j].getPiesa().canMove(i, j, xRegeNegru, yRegeNegru, casute[i][j])
                            && !isBlocked(i, j, xRegeNegru, yRegeNegru, casute[i][j])
                            && casute[i][j].getPiesa().getColor() == Color.WHITE) {
                        System.out.println("Black checked");
                        blackChecked = true;
                        return true;
                    }
                    //if vreo piesa neagra canMove && !isBlocked la regeAlb return false;
                    else if (casute[i][j].getPiesa() != null
                            && casute[i][j].getPiesa().getColor() == Color.BLACK
                            && casute[i][j].getPiesa().canMove(i, j, xRegeAlb, yRegeAlb, casute[i][j])
                            && !isBlocked(i, j, xRegeAlb, yRegeAlb, casute[i][j])) {
                        System.out.println("Can't move, will go in check");
                        return false;
                    }

                } else if (color == Color.BLACK) {//Randul lui negru
                    //if vreo piesa neagra canMove && !isBlocked la regeAlb return true;
                    if (casute[i][j].getPiesa() != null
                            && casute[i][j].getPiesa().canMove(i, j, xRegeAlb, yRegeAlb, casute[i][j])
                            && !isBlocked(i, j, xRegeAlb, yRegeAlb, casute[i][j])
                            && casute[i][j].getPiesa().getColor() == Color.BLACK) {
                        System.out.println("White checked");
                        whiteChecked = true;
                        return true;
                    }
                    //if vreo piesa alba canMove && !isBlocked la regeAlba return false;
                    else if (casute[i][j].getPiesa() != null
                            && casute[i][j].getPiesa().getColor() == Color.WHITE
                            && casute[i][j].getPiesa().canMove(i, j, xRegeNegru, yRegeNegru, casute[i][j])
                            && !isBlocked(i, j, xRegeNegru, yRegeNegru, casute[i][j])) {
                        System.out.println("Can't move, will go in check");
                        return false;
                    }
                }
            }
        System.out.println("No check situation");
        return true;
    }

    public static void changePlayer() {
        whiteTurn = !whiteTurn;
    }

    public static boolean rightTurn(Color color) {
        if (whiteTurn && color == Color.WHITE) {
            return true;
        } else if (!whiteTurn && color == Color.BLACK) {
            return true;
        }
        return false;
    }

}
