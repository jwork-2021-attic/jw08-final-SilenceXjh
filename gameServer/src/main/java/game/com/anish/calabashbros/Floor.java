package game.com.anish.calabashbros;

import java.awt.Color;
import java.io.Serializable;

public class Floor extends Thing implements Serializable {

    private static final long serialVersionUID = 6L;

    Floor(World world) {
        super(Color.yellow, (char) 250, world);
    }

}
