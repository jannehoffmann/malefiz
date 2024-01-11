import java.util.ArrayList;

public class Figur {
    private int farbeR;
    private int farbeG;
    private int farbeB;
    private Feld feld;
    private Feld startFeld;

    //Konstruktor für Figur, wenn die Figur die Farbe einer barrikade hat, wird die entsprechende Variable auf
    //true gesetzt, sonst false
    public Figur(int farbeR, int farbeG, int farbeB, Feld feld) {
        this.farbeR = farbeR;
        this.farbeG = farbeG;
        this.farbeB = farbeB;
        this.feld = feld;
        this.startFeld = feld;
        this.feld.setFarbe(this.farbeR, this.farbeG, this.farbeB);

    }

    //falls das Feld auf das wir die Figur bewegen wollen belegt ist, wird die Figur die auf diesem Feld steht
    //auf ihr Startfeld bewegt. Achtung: es ist möglich seine eigenen Figuren zu schlagen.
    public void bewegen(Feld feld, Figur[] figuren, Figur[] barrikaden, Feld zwischenlager) {
        if (feld.getBelegt()) {
            if (!feld.getBarrikade()) {
                for (Figur f : figuren) {
                    if (f.getFeld() == feld) {
                        f.bewegen(f.getStartFeld());
                        break;
                    }
                }
            } else {
                for (Figur f : barrikaden) {
                    if (f.getFeld() == feld) {
                        f.bewegen(zwischenlager);
                    }
                }
            }
        }
        this.bewegen(feld);

    }

    //bewegt eine Figur auf das übergebene Feld, ohne Überprüfung ob das Feld belegt war
    public void bewegen(Feld feld) {
        this.feld.setRand(0);
        this.feld.setFarbe(0);
        this.feld = feld;
        this.feld.setFarbe(this.farbeR, this.farbeG, this.farbeB);
    }

    public Feld getFeld() {
        return feld;
    }

    public Feld getStartFeld() {
        return startFeld;
    }

    //wird als erstes aufgerufen, um die möglichen Zielfelder zu erhalten, ruft dann die untere Methode auf
    //die möglichen Zielfelder sind abhängig vom aktuellen Feld der Figur und der gewürfelten Zahl (schritte)
    public ArrayList<Feld> berechneWeg(int schritte) {
        Feld aktuellesFeld = this.feld;
        ArrayList<Feld> res = new ArrayList<>();

        for (Feld f : aktuellesFeld.getNachbarn()) {
            res = berechneWeg(schritte - 1, f, aktuellesFeld, res);

        }
        return res;
    }

    //gibt eine Liste (moeglich) zurück, in welcher die möglichen Zielfelder enthalten sind
    public ArrayList<Feld> berechneWeg(int schritte, Feld aktuellesFeld, Feld vorherigesFeld, ArrayList<Feld> moeglich) {
        if (schritte == 0) {
            moeglich.add(aktuellesFeld);
            return moeglich;
        }
        if (aktuellesFeld.getBarrikade()) {
            return moeglich;
        }
        for (Feld f : aktuellesFeld.getNachbarn()) {
            if (f != vorherigesFeld ) {
                moeglich = berechneWeg(schritte - 1, f, aktuellesFeld, moeglich);
            }
        }
        return moeglich;
    }
}



