package game.com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.asciiPanel.AsciiPanel;
import game.com.anish.calabashbros.*;

public class WorldScreen implements Screen, Serializable {

    private static final long serialVersionUID = 1L;
    private World world;
    private Calabash bro1 = null;
    private Calabash bro2 = null;
    private Calabash bro3 = null;
    private MonGenerator mongen = null;

    public WorldScreen() {
        world = new World();
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    @Override
    public Screen respondToUserInput(int dir, int index) {

        if(index == 1) bro1.setDir(dir);
        else if(index == 2) bro2.setDir(dir);
        else bro3.setDir(dir);

        return this;
    }

    @Override
    public void addBro(int index) {
        if(index == 1) {
            bro1 = new Calabash(new Color(204, 0, 0), world, index);
            world.put(bro1, 9, 14);
            mongen = new MonGenerator(world);
            new Thread(bro1).start();
            new Thread(mongen).start();
        }
        else if(index == 2) {
            bro2 = new Calabash(new Color(204, 0, 0), world, index);
            for(int i = 11; i < World.WIDTH; i++) {
                for(int j = 14; j >= 6; --j) {
                    synchronized(world.get(i, j)) {
                        if(world.get(i,j).getClass() == Floor.class) {
                            world.put(bro2, i, j);
                            new Thread(bro2).start();
                            world.setHeart(index);
                            world.setScore(0, index);
                            return;
                        }
                    }
                }
            }
        }
        else {
            bro3 = new Calabash(new Color(204, 0, 0), world, index);
            for(int i = 11; i < World.WIDTH; i++) {
                for(int j = 15; j < World.HEIGHT; ++j) {
                    synchronized(world.get(i, j)) {
                        if(world.get(i,j).getClass() == Floor.class) {
                            world.put(bro3, i, j);
                            new Thread(bro3).start();
                            world.setHeart(index);
                            world.setScore(0, index);
                            return;
                        }
                    }
                }
            }
        }
        world.setHeart(index);
        world.setScore(0, index);
    }

}
