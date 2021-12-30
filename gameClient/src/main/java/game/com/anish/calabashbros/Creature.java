package game.com.anish.calabashbros;

import java.awt.Color;

public class Creature extends Thing implements Runnable{

    protected boolean isPaused = false;

    Creature(Color color, char glyph, World world) {
        super(color, glyph, world);
    }

    public void moveTo(int xPos, int yPos) {
        this.world.put(this, xPos, yPos);
    }

    public void setPause() {
        isPaused = true;
    }

    public synchronized void setContinue() {
        isPaused = false;
        notifyAll();
    }

    public void run() {
        
    }
}
