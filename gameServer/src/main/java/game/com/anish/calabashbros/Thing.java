package game.com.anish.calabashbros;

import java.awt.Color;
import java.io.Serializable;

public class Thing implements Serializable {

    private static final long serialVersionUID = 10L;

    protected World world;

    public Tile<? extends Thing> tile;

    public int getX() {
        return this.tile.getxPos();
    }

    public int getY() {
        return this.tile.getyPos();
    }

    public void setTile(Tile<? extends Thing> tile) {
        this.tile = tile;
    }

    Thing(Color color, char glyph, World world) {
        this.color = color;
        this.glyph = glyph;
        this.world = world;
    }

    protected Color color;

    public Color getColor() {
        return this.color;
    }

    protected char glyph;

    public char getGlyph() {
        return this.glyph;
    }

}
