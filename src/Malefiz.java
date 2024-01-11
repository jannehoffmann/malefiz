import processing.core.PApplet;

public class Malefiz extends PApplet {
    //Hier wird eine Variable für das Spiel erstellt was gespielt werden soll
    private static Spiel spiel;

    // main methode, ruft nur die main von PApplet auf
    public static void main(String[] args) {
        PApplet.main("Malefiz");
    }

    //legt Größe des Fensters fest
    public void settings() {
        size(800, 800);
    }

    //hier werden die Grundeinstellungen vorgenommen
    public void setup() {
        background(255, 204, 120);
        strokeWeight(3);
        spiel = new Spiel();
    }

    //malt das Spiel entsprechend der entsprechenden Spielphase
    public void draw() {
        if (spiel.isMenu()) {
            auswahlSpieler();
        } else if (spiel.isOngoing()) {
            drawField(spiel);
            showTurn(spiel);
            showDice(spiel);
            showReturnButton();
        } else {
            drawField(spiel);
            showWinnerMessage(spiel.getSieger());
            noLoop();
        }
    }

    //zeigt das auswahlmenü für die Anzahl der Mitspieler an
    public void auswahlSpieler(){
        fill(255);
        stroke(0);
        rect((float) 10, (float) 375, (float) 100, (float) 50);
        rect((float) 236, (float) 375, (float) 100, (float) 50);
        rect((float) 462, (float) 375, (float) 100, (float) 50);
        rect((float) 690, (float) 375, (float) 100, (float) 50);
        fill(0);
        textSize(20);
        textAlign(CENTER);
        text("1 Spieler", (float) 60, (float) 405);
        text("2 Spieler", (float) 286, (float) 405);
        text("3 Spieler", (float) 512, (float) 405);
        text("4 Spieler", (float) 740, (float) 405);
    }

    //Zeigt oben in der rechten Ecke den Würfel mit der aktuellen Augenzahl an
    public void showDice(Spiel spiel) {
        fill(255);
        stroke(0);
        square(800 - 70, 20, 50);
        fill(0);
        textSize(45);
        textAlign(CENTER);
        text(spiel.getWurf(), 800 - 70 + 25, 60);
    }

    //Zeigt den returnbutton an
    public void showReturnButton() {
        fill(255);
        stroke(0);
        rect((float) 403.75 - 40, (float) 20, (float) 80, (float) 40);
        fill(0);
        textSize(20);
        textAlign(CENTER);
        text("Zurück", (float) 403.75, (float) 47);
    }

    //Zeigt in der oberen linken Ecke ein Feld in der Farbe des aktuellen Spielers an
    public void showTurn(Spiel spiel) {
        switch (spiel.getZug()) {
            case 0:
                fill(0, 255, 0);
                stroke(0, 255, 0);
                break;
            case 1:
                fill(255, 255, 0);
                stroke(255, 255, 0);
                break;
            case 2:
                fill(0, 0, 255);
                stroke(0, 0, 255);
                break;
            case 3:
                fill(255, 0, 0);
                stroke(255, 0, 0);
                break;
        }
        square(20, 20, 50);
    }

    //Gibt eine Nachricht aus wer gewonnen hat
    public void showWinnerMessage(int sieger) {

        String message = "";
        switch (sieger) {
            case 0:
                message = "Grün hat gewonnen!";
                fill(0, 255, 0);
                break;
            case 1:
                message = "Gelb hat gewonnen!";
                fill(255, 255, 0);
                break;
            case 2:
                message = "Blau hat gewonnen!";
                fill(0, 0, 255);
                break;
            case 3:
                message = "Rot hat gewonnen!";
                fill(255, 0, 0);
                break;
        }

        textSize(80);
        textAlign(CENTER);
        text(message, 400, 400);
    }

    //zeichnet alle Spielfelder auf das Fenster
    public void drawField(Spiel spiel) {
        for (Feld f : spiel.getFelder()) {
            stroke(f.getRand());
            fill(f.getFarbeR(), f.getFarbeG(), f.getFarbeB());
            circle(f.getPosX(), f.getPosY(), 30);
        }
        fill(spiel.getZwischenlager().getFarbeR(), spiel.getZwischenlager().getFarbeG(), spiel.getZwischenlager().getFarbeB());
        circle(spiel.getZwischenlager().getPosX(), spiel.getZwischenlager().getPosY(), 30);

    }

    //ruft bei einem Mausklick die entsprechende Methode in Spiel auf, je nach der aktuellen Spielphase
    public void mouseClicked() {
        if (spiel.isMenu()) {
            spiel.auswaehlen(mouseX, mouseY);
            background(255, 204, 120);
        } else {
            spiel.mouseclick(mouseX, mouseY);
        }
    }

}

