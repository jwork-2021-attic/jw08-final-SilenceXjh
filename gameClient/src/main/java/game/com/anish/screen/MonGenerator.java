package game.com.anish.screen;

import java.awt.Color;
import java.io.Serializable;

import game.com.anish.calabashbros.Calabash;
import game.com.anish.calabashbros.Monster;
import game.com.anish.calabashbros.World;

public class MonGenerator implements Runnable, Serializable {
    
    private static final long serialVersionUID = 9L;
    private World world;
    private Color[] colors;

    public MonGenerator(World world) {
        this.world = world;
        colors = new Color[7];
        colors[0] = new Color(204, 0, 0);
        colors[1] = new Color(255, 165, 0);
        colors[2] = new Color(252, 233, 79);
        colors[3] = new Color(78, 154, 6);
        colors[4] = new Color(50, 175, 255);
        colors[5] = new Color(114, 159, 207);
        colors[6] = new Color(173, 127, 168);
    }


    public void run() {
        while(true) {
            if(world.isEnd()) {
                return;
            }
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {

            }
            Monster mon;
            int level = 0;
            double ra = Math.random();
            if(world.getGlobalScore() <= 200) {
                level = (int)(ra * 2);
            }
            else if(world.getGlobalScore() <= 600) {
                level = (int)(ra * 4);
            }
            else {
                level = (int)(ra * 7);
            }
            double d = Math.random();
            int row = (int)(d * 18);
            int dir = (int)(d * 2);
            if(dir == 0) {
                mon = new Monster(colors[level], world, level, false);
                world.put(mon, 0, row+6);
            }
            else {
                mon = new Monster(colors[level], world, level, true);
                world.put(mon, World.WIDTH - 1, row+6);
            }
            new Thread(mon).start();
        }
    }

}