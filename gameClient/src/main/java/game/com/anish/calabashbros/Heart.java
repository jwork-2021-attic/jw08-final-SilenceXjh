package game.com.anish.calabashbros;

import java.awt.Color;
import java.io.Serializable;

public class Heart extends Thing implements Serializable {

    private static final long serialVersionUID = 7L;
    
    Heart(World world) {
        super(Color.red, (char) 3, world);
    }

}