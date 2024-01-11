public class Feld {

    //Klassenvariablen
    private int farbeR;
    private int farbeG;
    private int farbeB;
    private int rand;
    private float posX;
    private float posY;
    private boolean barrikade;
    private boolean belegt;
    private Feld[] nachbarn;


    //Konstruktor für Farben
    public Feld(int farbeR, int farbeG, int farbeB, float posX, float posY) {
        setFarbe(farbeR, farbeG, farbeB);
        this.posX = posX;
        this.posY = posY;
        this.rand = 0;
    }

    //Konstruktor für schwarz/weiß
    public Feld(int farbe, float posX, float posY) {
        setFarbe(farbe, farbe, farbe);
        this.posX = posX;
        this.posY = posY;
    }

    //setters
    public void setFarbe(int farbeR, int farbeG, int farbeB) {
        this.farbeR = farbeR;
        this.farbeG = farbeG;
        this.farbeB = farbeB;
        isBarrikade();
        isBelegt();
    }

    public void setFarbe(int farbe) {
        this.farbeR = farbe;
        this.farbeG = farbe;
        this.farbeB = farbe;
        isBarrikade();
        isBelegt();
    }

    //ist wahr wenn das Feld die Farbe von barrikaden hat
    public void isBarrikade() {
        if (this.farbeR == 102 && this.farbeG == 51 && this.farbeB == 0) {
            this.barrikade = true;
        } else {
            this.barrikade = false;
        }
    }

    //ist wahr, wenn das feld nicht schwarz ist
    public void isBelegt() {
        if (this.farbeR == 0 && this.farbeG == 0 && this.farbeB == 0) {
            this.belegt = false;
        } else {
            this.belegt = true;
        }
    }

    //verändert die farbe des Randes, da wir nur schwarz und weiß benutzen ist es nur in greyscale
    public void setRand(int rand) {
        this.rand = rand;
    }

    //setters
    public void setNachbarn(int[] nachbarn, Feld[] felder) {
        this.nachbarn = new Feld[nachbarn.length];
        for (int i = 0; i < nachbarn.length; i++) {
            this.nachbarn[i] = felder[nachbarn[i]];
        }
    }

    //getters
    public int getFarbeR() {
        return farbeR;
    }

    public int getFarbeG() {
        return farbeG;
    }

    public int getFarbeB() {
        return farbeB;
    }

    public int getRand() {
        return rand;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public boolean getBarrikade() {
        return barrikade;
    }

    public boolean getBelegt() {
        return belegt;
    }

    public Feld[] getNachbarn() {
        return nachbarn;
    }

    //primitive Methode um den nächsten freien Nachbarn zu berechnen, funktioniert nur richtig, wenn 2 entfernt das
    //erste probierte Feld frei ist, da sonst nur ein Zweig abgetastet wird
    public Feld getNaechsterFreierNachbar() {
        for (Feld n : this.nachbarn) {
            if (!n.getBelegt()) {
                return n;
            }
        }
        for (Feld n : this.nachbarn) {
            return n.getNaechsterFreierNachbar();
        }
        return this;
    }
}
