package game.asciiPanel;

import java.io.Serializable;

/**
 * This class holds provides all available Fonts for the AsciiPanel. Some
 * graphics are from the Dwarf Fortress Tileset Wiki Page
 * 
 * @author zn80
 *
 */
public class AsciiFont implements Serializable {

    public static final AsciiFont CP437_9x16 = new AsciiFont("game/resources/cp437_9x16.png", 9, 16);
    public static final AsciiFont TALRYTH_15_15 = new AsciiFont("game/resources/talryth_square_15x15.png", 15, 15);
    public static final AsciiFont TALRYTH_32_32 = new AsciiFont("game/resources/talryth_square_32x32.png", 32, 32);
    public static final AsciiFont FLOOR = new AsciiFont("game/resources/floor.png", 32, 32);
    public static final AsciiFont BRO1 = new AsciiFont("game/resources/bro1.jpg", 32, 32);
    public static final AsciiFont BRO2 = new AsciiFont("game/resources/bro2.jpg", 32, 32);
    public static final AsciiFont BRO3 = new AsciiFont("game/resources/bro3.jpg", 32, 32);
    public static final AsciiFont BRO4 = new AsciiFont("game/resources/bro4.jpg", 32, 32);
    public static final AsciiFont BRO5 = new AsciiFont("game/resources/bro5.jpg", 32, 32);
    public static final AsciiFont BRO6 = new AsciiFont("game/resources/bro6.jpg", 32, 32);
    public static final AsciiFont BRO7 = new AsciiFont("game/resources/bro7.jpg", 32, 32);
    public static final AsciiFont BRO8 = new AsciiFont("game/resources/bro8.jpg", 32, 32);
    public static final AsciiFont MON1 = new AsciiFont("game/resources/mon1.png", 32, 32);
    public static final AsciiFont MON2 = new AsciiFont("game/resources/mon2.png", 32, 32);
    public static final AsciiFont MON3 = new AsciiFont("game/resources/mon3.png", 32, 32);
    public static final AsciiFont MON4 = new AsciiFont("game/resources/mon4.png", 32, 32);
    public static final AsciiFont MON5 = new AsciiFont("game/resources/mon5.png", 32, 32);
    public static final AsciiFont MON6 = new AsciiFont("game/resources/mon6.png", 32, 32);
    public static final AsciiFont MON7 = new AsciiFont("game/resources/mon7.png", 32, 32);

    private String fontFilename;

    public String getFontFilename() {
        return fontFilename;
    }

    private int width;

    public int getWidth() {
        return width;
    }

    private int height;

    public int getHeight() {
        return height;
    }

    public AsciiFont(String filename, int width, int height) {
        this.fontFilename = filename;
        this.width = width;
        this.height = height;
    }
}