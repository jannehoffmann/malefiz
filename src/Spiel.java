import java.util.ArrayList;

public class Spiel {
    // Array in dem alle Felder gespeichert werden
    private Feld[] felder = new Feld[128];
    //Feld, auf dem die Barrikaden angezeigt wird wenn der Spieler eine verschieben kann
    private Feld zwischenlager;
    //Arrays der Figuren der einzelnen Spieler
    private Figur[] figurenGruen = new Figur[4];
    private Figur[] figurenGelb = new Figur[4];
    private Figur[] figurenBlau = new Figur[4];
    private Figur[] figurenRot = new Figur[4];
    //Array mit den Barrikaden
    private Figur[] barrikaden = new Figur[11];
    //Speichert die gerade ausgewählte Figur
    private Computerspieler[] computerspieler = new Computerspieler[4];
    private Figur figAktuell = null;
    //zeigt an, ob gewürfelt wird (0), Figur ausgewählt (1), Figur verschoben wird (2), Barrikade versetzt wird(3)
    private int auswahl = 0;
    //zeigt an, welche Farbe am Zug ist
    private int zug = 0;
    private Wuerfel wuerfel;
    //Speichert 10 während das Spiel läuft und wird dann zur Nummer des Siegers
    private int sieger;
    //zeigt an, ob mit der gewürfelten Zahl überhaupt ein Zug mit irgendeiner Figur des Spielers möglich ist
    private boolean zugMoeglich;


    //Speichert die aktuellen Zugmöglichkeiten
    private ArrayList<Feld> zugmoeglichkeiten = new ArrayList<Feld>();

    //Konstruktor, initialisiert alle wichtigen Objekte, previously known as setup(partially)
    //etwas unkomplizierter für manche Teile, da es jetzt als Objekt existiert
    public Spiel() {
        sieger = 20;
        wuerfel = new Wuerfel();
        setFelder();
        setFiguren();
        setNachbarn();
    }

    //Hier werden alle benötigten Spielfelder mit den richtigen Belegungen erstellt
    public void setFelder() {
        zwischenlager = new Feld(255, 204, 120, (float) 720.5, (float) 313.25);
        felder[0] = new Feld(0, (float) 720.5, (float) 765.75);
        felder[1] = new Feld(0, (float) 630, (float) 765.75);
        felder[2] = new Feld(0, (float) 720.5, (float) 720.5);
        felder[3] = new Feld(0, (float) 630, (float) 720.5);
        felder[4] = new Feld(0, (float) 449, (float) 765.75);
        felder[5] = new Feld(0, (float) 539.5, (float) 765.75);
        felder[6] = new Feld(0, (float) 449, (float) 720.5);
        felder[7] = new Feld(0, (float) 539.5, (float) 720.5);
        felder[8] = new Feld(0, (float) 358.5, (float) 765.75);
        felder[9] = new Feld(0, (float) 268, (float) 765.75);
        felder[10] = new Feld(0, (float) 358.5, (float) 720.5);
        felder[11] = new Feld(0, (float) 268, (float) 720.5);
        felder[12] = new Feld(0, (float) 177.5, (float) 765.75);
        felder[13] = new Feld(0, (float) 87, (float) 765.75);
        felder[14] = new Feld(0, (float) 177.5, (float) 720.5);
        felder[15] = new Feld(0, (float) 87, (float) 720.5);
        for (int i = 16; i <= 32; i++) {
            felder[i] = new Feld(0, (float) (765.75 - ((i - 16) * 45.25)), (float) 675.25);
        }
        for (int i = 33; i <= 37; i++) {
            felder[i] = new Feld(0, (float) (765.75 - ((i - 33) * 4 * (45.25))), 630);
        }
        for (int i = 38; i <= 54; i++) {
            felder[i] = new Feld(0, (float) (765.75 - ((i - 38) * 45.25)), (float) 584.75);
        }
        for (int i = 55; i <= 58; i++) {
            felder[i] = new Feld(0, (float) (675.25 - ((i - 55) * 4 * (45.25))), (float) 539.5);
        }
        for (int i = 59; i <= 71; i++) {
            felder[i] = new Feld(0, (float) (675.25 - ((i - 59) * 45.25)), (float) 494.25);
        }
        for (int i = 72; i <= 73; i++) {
            felder[i] = new Feld(0, (float) (584.75 - ((i - 72) * 8 * (45.25))), 449);
        }
        for (int i = 74; i <= 82; i++) {
            felder[i] = new Feld(0, (float) (584.75 - ((i - 74) * 45.25)), (float) 403.75);
        }
        for (int i = 83; i <= 84; i++) {
            felder[i] = new Feld(0, (float) (494.25 - ((i - 83) * 4 * (45.25))), 358);
        }
        for (int i = 85; i <= 89; i++) {
            felder[i] = new Feld(0, (float) (494.25 - ((i - 85) * (45.25))), (float) 313.25);
        }
        felder[90] = new Feld(0, (float) 403.75, (float) 268);
        for (int i = 91; i <= 107; i++) {
            felder[i] = new Feld(0, (float) (765.75 - ((i - 91) * 45.25)), (float) 222.75);
        }
        for (int i = 108; i <= 109; i++) {
            felder[i] = new Feld(0, (float) (765.75 - ((i - 108) * 16 * 45.25)), (float) 177.5);
        }
        for (int i = 110; i <= 126; i++) {
            felder[i] = new Feld(0, (float) (765.75 - ((i - 110) * 45.25)), (float) 132.25);
        }
        felder[127] = new Feld(255, (float) 403.75, 87);
    }

    //initialisiert für alle Felder ihre jeweiligen nachbarn
    public void setNachbarn() {
        int[] n0 = {18};
        felder[0].setNachbarn(n0, felder);
        felder[1].setNachbarn(n0, felder);
        felder[2].setNachbarn(n0, felder);
        felder[3].setNachbarn(n0, felder);
        int[] n4 = {22};
        felder[4].setNachbarn(n4, felder);
        felder[5].setNachbarn(n4, felder);
        felder[6].setNachbarn(n4, felder);
        felder[7].setNachbarn(n4, felder);
        int[] n8 = {26};
        felder[8].setNachbarn(n8, felder);
        felder[9].setNachbarn(n8, felder);
        felder[10].setNachbarn(n8, felder);
        felder[11].setNachbarn(n8, felder);
        int[] n12 = {30};
        felder[12].setNachbarn(n12, felder);
        felder[13].setNachbarn(n12, felder);
        felder[14].setNachbarn(n12, felder);
        felder[15].setNachbarn(n12, felder);
        int[] n16 = {33, 17};
        felder[16].setNachbarn(n16, felder);
        for (int i = 17; i <= 19; i++) {
            int[] n111 = {i - 1, i + 1};
            felder[i].setNachbarn(n111, felder);
        }
        for (int i = 21; i <= 23; i++) {
            int[] n111 = {i - 1, i + 1};
            felder[i].setNachbarn(n111, felder);
        }
        for (int i = 25; i <= 27; i++) {
            int[] n111 = {i - 1, i + 1};
            felder[i].setNachbarn(n111, felder);
        }
        for (int i = 29; i <= 31; i++) {
            int[] n111 = {i - 1, i + 1};
            felder[i].setNachbarn(n111, felder);
        }
        for (int i = 64; i <= 66; i++) {
            int[] n111 = {i - 1, i + 1};
            felder[i].setNachbarn(n111, felder);
        }
        for (int i = 77; i <= 79; i++) {
            int[] n111 = {i - 1, i + 1};
            felder[i].setNachbarn(n111, felder);
        }
        int[] n20 = {19, 21, 34};
        felder[20].setNachbarn(n20, felder);
        int[] n24 = {23, 25, 35};
        felder[24].setNachbarn(n24, felder);
        int[] n28 = {27, 29, 36};
        felder[28].setNachbarn(n28, felder);
        int[] n32 = {31, 37};
        felder[32].setNachbarn(n32, felder);
        int[] n33 = {16, 38};
        felder[33].setNachbarn(n33, felder);
        int[] n34 = {20, 42};
        felder[34].setNachbarn(n34, felder);
        int[] n35 = {24, 46};
        felder[35].setNachbarn(n35, felder);
        int[] n36 = {28, 50};
        felder[36].setNachbarn(n36, felder);
        int[] n37 = {32, 54};
        felder[37].setNachbarn(n37, felder);
        int[] n38 = {33, 39};
        felder[38].setNachbarn(n38, felder);
        int[] n39 = {38, 40};
        felder[39].setNachbarn(n39, felder);
        int[] n40 = {39, 41, 55};
        felder[40].setNachbarn(n40, felder);
        int[] n41 = {40, 42};
        felder[41].setNachbarn(n41, felder);
        int[] n42 = {41, 43, 34};
        felder[42].setNachbarn(n42, felder);
        int[] n43 = {42, 44};
        felder[43].setNachbarn(n43, felder);
        int[] n44 = {43, 45, 56};
        felder[44].setNachbarn(n44, felder);
        int[] n45 = {44, 46};
        felder[45].setNachbarn(n45, felder);
        int[] n46 = {45, 47, 35};
        felder[46].setNachbarn(n46, felder);
        int[] n47 = {46, 48};
        felder[47].setNachbarn(n47, felder);
        int[] n48 = {47, 49, 57};
        felder[48].setNachbarn(n48, felder);
        int[] n49 = {48, 50};
        felder[49].setNachbarn(n49, felder);
        int[] n50 = {49, 51, 36};
        felder[50].setNachbarn(n50, felder);
        int[] n51 = {50, 52};
        felder[51].setNachbarn(n51, felder);
        int[] n52 = {51, 53, 58};
        felder[52].setNachbarn(n52, felder);
        int[] n53 = {52, 54};
        felder[53].setNachbarn(n53, felder);
        int[] n54 = {53, 37};
        felder[54].setNachbarn(n54, felder);
        int[] n55 = {40, 59};
        felder[55].setNachbarn(n55, felder);
        int[] n56 = {44, 63};
        felder[56].setNachbarn(n56, felder);
        int[] n57 = {48, 67};
        felder[57].setNachbarn(n57, felder);
        int[] n58 = {52, 71};
        felder[58].setNachbarn(n58, felder);
        int[] n59 = {55, 60};
        felder[59].setNachbarn(n59, felder);
        int[] n60 = {59, 61};
        felder[60].setNachbarn(n60, felder);
        int[] n61 = {60, 62, 72};
        felder[61].setNachbarn(n61, felder);
        int[] n62 = {61, 63};
        felder[62].setNachbarn(n62, felder);
        int[] n63 = {56, 62, 64};
        felder[63].setNachbarn(n63, felder);
        int[] n64 = {63, 65};
        felder[64].setNachbarn(n64, felder);
        int[] n65 = {64, 66};
        felder[65].setNachbarn(n65, felder);
        int[] n66 = {65, 67};
        felder[66].setNachbarn(n66, felder);
        int[] n67 = {57, 66, 68};
        felder[67].setNachbarn(n67, felder);
        int[] n68 = {67, 69};
        felder[68].setNachbarn(n68, felder);
        int[] n69 = {68, 70, 73};
        felder[69].setNachbarn(n69, felder);
        int[] n70 = {69, 71};
        felder[70].setNachbarn(n70, felder);
        int[] n71 = {58, 70};
        felder[71].setNachbarn(n71, felder);
        int[] n72 = {61, 74};
        felder[72].setNachbarn(n72, felder);
        int[] n73 = {69, 82};
        felder[73].setNachbarn(n73, felder);
        int[] n74 = {72, 75};
        felder[74].setNachbarn(n74, felder);
        int[] n75 = {74, 76};
        felder[75].setNachbarn(n75, felder);
        int[] n76 = {75, 77, 83};
        felder[76].setNachbarn(n76, felder);
        int[] n77 = {76, 78};
        felder[77].setNachbarn(n77, felder);
        int[] n78 = {77, 79};
        felder[78].setNachbarn(n78, felder);
        int[] n79 = {78, 80};
        felder[79].setNachbarn(n79, felder);
        int[] n80 = {79, 81, 84};
        felder[80].setNachbarn(n80, felder);
        int[] n81 = {80, 82};
        felder[81].setNachbarn(n81, felder);
        int[] n82 = {73, 81};
        felder[82].setNachbarn(n82, felder);
        int[] n83 = {76, 85};
        felder[83].setNachbarn(n83, felder);
        int[] n84 = {80, 89};
        felder[84].setNachbarn(n84, felder);
        int[] n85 = {83, 86};
        felder[85].setNachbarn(n85, felder);
        int[] n86 = {85, 87};
        felder[86].setNachbarn(n86, felder);
        int[] n87 = {86, 88, 90};
        felder[87].setNachbarn(n87, felder);
        int[] n88 = {87, 89};
        felder[88].setNachbarn(n88, felder);
        int[] n89 = {84, 88};
        felder[89].setNachbarn(n89, felder);
        int[] n90 = {87, 99};
        felder[90].setNachbarn(n90, felder);
        int[] n91 = {92, 108};
        felder[91].setNachbarn(n91, felder);
        for (int i = 92; i <= 98; i++) {
            int[] n92 = {i - 1, i + 1};
            felder[i].setNachbarn(n92, felder);
        }
        int[] n99 = {90, 98, 100};
        felder[99].setNachbarn(n99, felder);
        for (int i = 100; i <= 106; i++) {
            int[] n100 = {i - 1, i + 1};
            felder[i].setNachbarn(n100, felder);
        }
        int[] n107 = {106, 109};
        felder[107].setNachbarn(n107, felder);
        int[] n108 = {91, 110};
        felder[108].setNachbarn(n108, felder);
        int[] n109 = {107, 126};
        felder[109].setNachbarn(n109, felder);
        int[] n110 = {108, 111};
        felder[110].setNachbarn(n110, felder);
        for (int i = 111; i <= 117; i++) {
            int[] n111 = {i - 1, i + 1};
            felder[i].setNachbarn(n111, felder);
        }
        int[] n118 = {117, 119, 127};
        felder[118].setNachbarn(n118, felder);
        for (int i = 119; i <= 125; i++) {
            int[] n119 = {i - 1, i + 1};
            felder[i].setNachbarn(n119, felder);
        }
        int[] n126 = {109, 125};
        felder[126].setNachbarn(n126, felder);
        int[] n127 = {118};
        felder[127].setNachbarn(n127, felder);
    }

    //initialisiert die Figuren, welche im Spiel verwendet werden.
    //Figuren haben keine eigene Grafik, sondern verändern nur das Aussehen ihres Feldes
    public void setFiguren() {
        figurenGruen[0] = new Figur(0, 255, 0, felder[0]);
        figurenGruen[1] = new Figur(0, 255, 0, felder[1]);
        figurenGruen[2] = new Figur(0, 255, 0, felder[2]);
        figurenGruen[3] = new Figur(0, 255, 0, felder[3]);
        figurenGelb[0] = new Figur(255, 255, 0, felder[4]);
        figurenGelb[1] = new Figur(255, 255, 0, felder[5]);
        figurenGelb[2] = new Figur(255, 255, 0, felder[6]);
        figurenGelb[3] = new Figur(255, 255, 0, felder[7]);
        figurenBlau[0] = new Figur(0, 0, 255, felder[8]);
        figurenBlau[1] = new Figur(0, 0, 255, felder[9]);
        figurenBlau[2] = new Figur(0, 0, 255, felder[10]);
        figurenBlau[3] = new Figur(0, 0, 255, felder[11]);
        figurenRot[0] = new Figur(255, 0, 0, felder[12]);
        figurenRot[1] = new Figur(255, 0, 0, felder[13]);
        figurenRot[2] = new Figur(255, 0, 0, felder[14]);
        figurenRot[3] = new Figur(255, 0, 0, felder[15]);
        barrikaden[0] = new Figur(102, 51, 0, felder[38]);
        barrikaden[1] = new Figur(102, 51, 0, felder[42]);
        barrikaden[2] = new Figur(102, 51, 0, felder[46]);
        barrikaden[3] = new Figur(102, 51, 0, felder[50]);
        barrikaden[4] = new Figur(102, 51, 0, felder[54]);
        barrikaden[5] = new Figur(102, 51, 0, felder[76]);
        barrikaden[6] = new Figur(102, 51, 0, felder[80]);
        barrikaden[7] = new Figur(102, 51, 0, felder[87]);
        barrikaden[8] = new Figur(102, 51, 0, felder[90]);
        barrikaden[9] = new Figur(102, 51, 0, felder[99]);
        barrikaden[10] = new Figur(102, 51, 0, felder[118]);
    }

    public void setFigAktuell(Figur figAktuell) {
        this.figAktuell = figAktuell;
    }

    //gibt das array mit den Feldern aus
    public Feld[] getFelder() {
        return felder;
    }

    //tests if a Player has won and changes the winner variable accordingly
    public void checkWinner(Figur[] figuren) {
        for (Figur f : figuren) {
            if (f.getFeld().getPosY() == 87) {
                sieger = zug;
                break;
            }
        }
    }

    //holt die aktuelle gewürfelte Zahl von würfel
    public int getWurf() {
        return wuerfel.getZahl();
    }

    //here: Getters
    public int getZug() {
        return zug;
    }

    public int getSieger() {
        return sieger;
    }

    public Feld getZwischenlager() {
        return zwischenlager;
    }

    //gibt ein Array zurück, wo alle Figuren sortiert drin sind
    public Figur[] getFiguren() {
        Figur[] figuren = new Figur[16];
        for (int i = 0; i < figurenGruen.length; i++) {
            figuren[i] = figurenGruen[i];
        }
        for (int i = 0; i < figurenGelb.length; i++) {
            figuren[i + 4] = figurenGelb[i];
        }
        for (int i = 0; i < figurenBlau.length; i++) {
            figuren[i + 8] = figurenBlau[i];
        }
        for (int i = 0; i < figurenRot.length; i++) {
            figuren[i + 12] = figurenRot[i];
        }
        return figuren;
    }

    //genutzt, um zu überprüfen, ob das Spiel noch läuft
    public boolean isOngoing() {
        if (sieger == 10) {
            return true;
        }
        return false;
    }

    //überprüft, ob schon eine Spieleranzahl ausgewählt wurden
    public boolean isMenu() {
        if (sieger == 20) {
            return true;
        }
        return false;
    }

    //Die Essenz des Spiels. Hier spielen sich die Züge ab.
    public void mouseclick(float mouseX, float mouseY) {

        Figur[] figuren = new Figur[4]; //Zwischenspeicher für Figuren des Spielers der an der Reihe ist

        switch (zug) { //Wer ist am Zug
            case 0: //grün
                figuren = figurenGruen;
                break;
            case 1: //gelb
                figuren = figurenGelb;
                break;
            case 2: //blau
                figuren = figurenBlau;
                break;
            case 3: //rot
                figuren = figurenRot;
                break;
        }
        // würfelt wenn ich in der entsprechenden Phase den Würfel auswähle
        if (auswahl == 0 && ((800 - 70 <= mouseX) && (mouseX <= 800 - 20)
                && (20 <= mouseY) && (mouseY <= 70))) {
            wuerfel.wuerfeln();
            auswahl = 1;

        //wählt die Figur aus wenn der Spieler der gerade an der Reihe ist in der entsprechenden
        //phase seine Figur anklickt
        } else if (auswahl == 1) {
            zugMoeglich = false;
            for (Figur f : figuren) {
                zugmoeglichkeiten = f.berechneWeg(wuerfel.getZahl());
                if (zugmoeglichkeiten.size() > 0) {
                    zugMoeglich = true;
                    break;
                }
            }
            if (zugMoeglich) {
                for (Figur f : figuren) {
                    if (((f.getFeld().getPosX() - 15) <= mouseX) && (mouseX <= (f.getFeld().getPosX() + 15))
                            && ((f.getFeld().getPosY() - 15) <= mouseY) && (mouseY <= (f.getFeld().getPosY() + 15))) {
                        f.getFeld().setRand(255);
                        figAktuell = f; // Speichert die ausgewählte figur
                        zugmoeglichkeiten = figAktuell.berechneWeg(wuerfel.getZahl());
                        for (int i = 0; i < zugmoeglichkeiten.size(); i++) {
                            zugmoeglichkeiten.get(i).setRand(255);
                        }
                        auswahl = 2;
                        break;
                    }
                }
            } else {
                zug++;
                if (zug == 4) {
                    zug = 0;
                }
                wuerfel.setNull();
            }
        // bewegt die Figur, die der Spieler ausgewählt hat, wenn der Spieler ein gültiges Feld auswählt hat und
        // nicht den Zurückknopf ausgewählt hat, in dem Fall wird die auswahlt der Figur rückgängig gemacht
        } else if (auswahl == 2) {
            if ((363.75 <= mouseX) && (mouseX <= 443.75) && (20 <= mouseY) && (mouseY <= 60)) {
                figAktuell.getFeld().setRand(0);
                figAktuell = null;
                for (Feld f : zugmoeglichkeiten) {
                    f.setRand(0);
                }
                while (0 < zugmoeglichkeiten.size()) {
                    zugmoeglichkeiten.remove(0);
                }
                auswahl = 1;
            } else {
                for (int i = 0; i < zugmoeglichkeiten.size(); i++) {
                    if (((zugmoeglichkeiten.get(i).getPosX() - 15) <= mouseX)
                            && (mouseX <= (zugmoeglichkeiten.get(i).getPosX() + 15))
                            && ((zugmoeglichkeiten.get(i).getPosY() - 15) <= mouseY)
                            && (mouseY <= (zugmoeglichkeiten.get(i).getPosY() + 15))) {
                        figAktuell.bewegen(zugmoeglichkeiten.get(i), getFiguren(), barrikaden, zwischenlager);// Bewegt die ausgewählte Figur auf ein neues Feld
                        for (Feld fi : felder) {
                            fi.setRand(0);
                        }
                        switch (zug) {
                            case 0:
                                checkWinner(figurenGruen);
                                break;
                            case 1:
                                checkWinner(figurenGelb);
                                break;
                            case 2:
                                checkWinner(figurenBlau);
                                break;
                            case 3:
                                checkWinner(figurenRot);
                                break;
                        }
                        if (zwischenlager.getBarrikade()) {
                            for (Figur b : barrikaden) {
                                if (b.getFeld() == zwischenlager) {
                                    figAktuell = b;
                                    break;
                                }
                            }
                            auswahl = 3;
                        } else {
                            auswahl = 0;
                            zug++;
                            if (zug == 4) {
                                zug = 0;
                            }
                            wuerfel.setNull();
                        }
                    }
                }
            }
            //wenn eine Barrikade geschmissen wurde kann der Spieler sie hier nun neu platzieren
        } else if (auswahl == 3) {
            for (int i = 0; i < felder.length; i++) {
                if (((felder[i].getPosX() - 15) <= mouseX
                        && (mouseX <= felder[i].getPosX() + 15)
                        && (felder[i].getPosY() - 15) <= mouseY)
                        && (mouseY <= felder[i].getPosY() + 15) && !felder[i].getBelegt()) {
                    figAktuell.bewegen(felder[i]);
                    zwischenlager.setFarbe(255, 204, 120);
                    auswahl = 0;
                    zug++;
                    if (zug == 4) {
                        zug = 0;
                    }
                    wuerfel.setNull();
                    break;
                }
            }
        }
        while (computerspieler[zug] != null) {
            wuerfel.wuerfeln();
            computerspieler[zug].machZug(getFiguren(), barrikaden, felder, zwischenlager);
            switch (zug) {
                case 0:
                    checkWinner(figurenGruen);
                    break;
                case 1:
                    checkWinner(figurenGelb);
                    break;
                case 2:
                    checkWinner(figurenBlau);
                    break;
                case 3:
                    checkWinner(figurenRot);
                    break;
            }
            zug++;
            if (zug == 4) {
                zug = 0;
            }
            zwischenlager.setFarbe(255, 204, 120);
        }

    }

    //initialisiert die Computerspieler
    public void auswaehlen(float mouseX, float mouseY) {
        if (10 <= mouseX && mouseX <= 110 && 375 <= mouseY && mouseY <= 425) {
            computerspieler[3] = new Computerspieler(figurenRot, wuerfel);
            computerspieler[2] = new Computerspieler(figurenBlau, wuerfel);
            computerspieler[1] = new Computerspieler(figurenGelb, wuerfel);
            sieger = 10;
        } else if (236 <= mouseX && mouseX <= 336 && 375 <= mouseY && mouseY <= 425) {
            computerspieler[2] = new Computerspieler(figurenBlau, wuerfel);
            computerspieler[3] = new Computerspieler(figurenRot, wuerfel);
            sieger = 10;
        } else if (462 <= mouseX && mouseX <= 562 && 375 <= mouseY && mouseY <= 425) {
            computerspieler[3] = new Computerspieler(figurenRot, wuerfel);
            sieger = 10;
        } else if (690 <= mouseX && mouseX <= 790 && 375 <= mouseY && mouseY <= 425) {
            sieger = 10;
        }

    }
}
