import java.util.ArrayList;

public class Computerspieler {
    //Klassenvariablen
    private Figur[] meineFiguren;
    private Wuerfel wuerfel;

    //Konstruktor
    public Computerspieler(Figur[] meineFiguren, Wuerfel wuerfel) {
        this.wuerfel = wuerfel;
        this.meineFiguren = meineFiguren;
    }

    //methode die von Spiel aufgerufen wird, um den Computergegner einen Zug machen zu lassen
    public void machZug(Figur[] figuren, Figur[] barrikaden, Feld[] felder, Feld zwischenlager) {
        boolean gesetzt = false; //ist true wenn der Computerspieler seine Figur gesetzt hat
        // Speichert die eigenen Figuren des Spielers in der Reihenfolge in der sie, wenn möglich, gesetzt werden sollen,
        //also die vorderste zuerst und die hinterste zuletzt. Wenn zwei Figuren auf der Gleichen höhe sind werden
        //sie in der Reihenfolge gespeichert in der sie Aufgerufen werden
        Figur[] reihenfolge = new Figur[4];
        for (Figur f : meineFiguren) {
            Figur tmp = f;
            Figur tmp2;
            for (int i = 0; i < 4; i++) {
                if (reihenfolge[i] == null) {
                    reihenfolge[i] = tmp;
                    break;
                } else if (tmp.getFeld().getPosY() < reihenfolge[i].getFeld().getPosY()) {
                    tmp2 = reihenfolge[i];
                    reihenfolge[i] = tmp;
                    tmp = tmp2;
                }
            }
        }
        //hier wird zuerst geguckt ob schon ein Zug gemacht wurde, sonst wird zuerst geguckt ob ein Zug mit dieser
        //Sinnvoll ist und dann wird er durchgeführt, sonst für die nächsten Figuren, es muss nicht immer der
        // beste Zug sein, nur der erste der sinnvoll ist
        for (Figur f : reihenfolge) {
            if (gesetzt) {
                break;
            }
            ArrayList<Feld> moeglichkeiten = f.berechneWeg(wuerfel.getZahl());
            for (int i = 0; i < moeglichkeiten.size(); i++) {
                if (istZugGut(moeglichkeiten.get(i), f.getFeld())) {
                    //hier wurde der Zug der gemacht werden soll festgelegt und wird jetzt durchgeführt
                    f.bewegen(moeglichkeiten.get(i), figuren, barrikaden, zwischenlager);
                    //hier wird, wenn eine Barrikade geschmissen wurde, die Barrikade versetzt
                    if (zwischenlager.getBarrikade()) {
                        Figur zuBewegen = null;
                        for (Figur b : barrikaden) {
                            if (b.getFeld() == zwischenlager) {
                                zuBewegen = b;
                                break;
                            }
                        }
                        // Hier werden die Gegnerrischen Figuren sortiert abgelegt, damit der vordersten der Weg versperrt werden kann
                        Figur[] besteGegnerfiguren = new Figur[12];
                        for (Figur bg : figuren) {
                            if (!isInArray(bg, meineFiguren)) {
                                Figur tmp = bg;
                                Figur tmp2;
                                for (int j = 0; j < 12; j++) {
                                    if (besteGegnerfiguren[j] == null) {
                                        besteGegnerfiguren[j] = tmp;
                                        break;
                                    } else if (tmp.getFeld().getPosY() < besteGegnerfiguren[j].getFeld().getPosY()) {
                                        tmp2 = besteGegnerfiguren[j];
                                        besteGegnerfiguren[j] = tmp;
                                        tmp = tmp2;
                                    }
                                }
                            }
                        }
                        //hier wird nun der vorher berechneten gegnerischen Figur der schnellste Weg zum Ziel verstellt
                        Figur besteGegnerfigur = besteGegnerfiguren[0];
                        float besteXPosition = berechneBesteXposition(besteGegnerfigur.getFeld());
                        Feld bestesFeld = felder[99];
                        for (Feld fe : felder) {
                            if (fe.getPosX() == besteXPosition && fe.getPosY() == besteGegnerfigur.getFeld().getPosY() ) {
                                bestesFeld = fe;
                                break;
                            }
                        }
                        if (!bestesFeld.getBelegt()) {
                            zuBewegen.bewegen(bestesFeld);
                        } else {
                            zuBewegen.bewegen(bestesFeld.getNaechsterFreierNachbar());
                        }

                    }
                    gesetzt = true;
                    wuerfel.setNull();
                    break;

                }
            }
        }
    }

    //Gibt true zurück, wenn die position des ersten feldes besser ist als die des zweiten
    public boolean istZugGut(Feld moeglich, Feld position) {
        if (moeglich.getPosY() < position.getPosY()) {
            return true;
        }

        if (moeglich.getPosY() > position.getPosY()) {
            return false;
        }
        if (betrag(berechneBesteXposition(position) - moeglich.getPosX()) <
                betrag(berechneBesteXposition(position) - position.getPosX())) {
            return true;
        }

        return false;
    }

    //gibt für ein Feld die X-Position des besten Feldes in der gleichen Reihe aus
    //Dies ist immer das nächstgelegene Feld, von welchem man in die nächsthöhere Zeile gehen kann
    public float berechneBesteXposition(Feld position) {
        //wenn das aktuelle Feld in einer Zwischenreihe liegt, ist das Feld selbst das beste
        float bestXpos = position.getPosX();
        //Für jede Reihe wird ein Array initialisiert, welches die möglichen besten Felder beinhaltet
        float[] bestXpos1 = {(float) 765.75, (float) 584.75, (float) 403.75, (float) 222.75, (float) 41.75};
        float[] bestXpos2 = {(float) 675.25, (float) 494.25, (float) 313.25, (float) 132.25};
        float[] bestXpos3 = {(float) 584.75, (float) 222.75};
        float[] bestXpos4 = {(float) 494.25, (float) 313.25};
        float[] bestXpos5 = {(float) 403.75};
        float[] bestXpos6 = {(float) 765.75, (float) 41.75};

        //hier wird jetzt geprüft, in welcher Reihe das aktuelle Feld liegt und dann wird in dieser Reihe das beste
        //Feld gesucht, in dem das entsprechende Array durchsucht wird
        if (position.getPosY() == 675.25) {
            bestXpos = bestXpos1[0];
            for (float best : bestXpos1) {
                if ((betrag(position.getPosX() - best)) < (betrag(position.getPosX() - bestXpos))) {
                    bestXpos = best;
                }
            }
        } else if (position.getPosY() == 584.75) {
            bestXpos = bestXpos2[0];
            for (float best : bestXpos2) {
                if ((betrag(position.getPosX() - best)) < (betrag(position.getPosX() - bestXpos))) {
                    bestXpos = best;
                }
            }
        } else if (position.getPosY() == 494.25) {
            bestXpos = bestXpos3[0];
            for (float best : bestXpos3) {
                if ((betrag(position.getPosX() - best)) < (betrag(position.getPosX() - bestXpos))) {
                    bestXpos = best;
                }
            }
        } else if (position.getPosY() == 403.75) {
            bestXpos = bestXpos4[0];
            for (float best : bestXpos4) {
                if ((betrag(position.getPosX() - best)) < (betrag(position.getPosX() - bestXpos))) {
                    bestXpos = best;
                }
            }
        } else if ((position.getPosY() == 313.25) || (position.getPosY() == 132.25)) {
            bestXpos = bestXpos5[0];
            for (float best : bestXpos5) {
                if ((betrag(position.getPosX() - best)) < (betrag(position.getPosX() - bestXpos))) {
                    bestXpos = best;
                }
            }
        } else if (position.getPosY() == 222.75) {
            bestXpos = bestXpos6[0];
            for (float best : bestXpos6) {
                if ((betrag(position.getPosX() - best)) < (betrag(position.getPosX() - bestXpos))) {
                    bestXpos = best;
                }
            }
        }
        return bestXpos;
    }

    //berechnet den Betrag einer Zahl
    public float betrag(float zahl) {
        if (zahl < 1) {
            return -zahl;
        }
        return zahl;
    }

    //guckt, ob eine Figur in einem gegebenen array ist, kann auch auf andere Datentypen angewendet werden,
    //wenn man sagt, das diese übergeben werden
    public boolean isInArray(Figur value, Figur[] array) {
        for (Figur f : array) {
            if (f == value) {
                return true;
            }
        }
        return false;
    }



}