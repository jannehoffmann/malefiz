import java.util.Random;

public class Wuerfel {
    private int zahl;

    //setzt die würfelzahl auf null damit die spieler wissen, das noch nicht gewürfelt wurde
    public Wuerfel() {
        this.zahl = 0;
    }

    //generiert die Würfelzahl
    public void wuerfeln() {
        Random wuerfel = new Random();
        this.zahl = wuerfel.nextInt(1, 7);
    }

    //getters
    public int getZahl() {
        return zahl;
    }

    //wird genutzt, um Würfelzahl nach jedem Zug wieder auf 0 zu setzen
    public void setNull() {
        this.zahl = 0;
    }
}
