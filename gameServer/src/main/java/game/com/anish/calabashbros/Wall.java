package game.com.anish.calabashbros;

import java.io.Serializable;

import game.asciiPanel.AsciiPanel;

public class Wall extends Thing implements Serializable {

    private static final long serialVersionUID = 9L;

    Wall(World world) {
        super(AsciiPanel.cyan, (char) 177, world);
    }

}
