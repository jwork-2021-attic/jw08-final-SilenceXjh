package game.com.anish.calabashbros;

import java.awt.Color;
import java.io.Serializable;

public class Digit extends Thing implements Serializable {

    private static final long serialVersionUID = 5L;
    
    Digit(World world, char digit) {
        super(Color.green, digit, world);
    }

}