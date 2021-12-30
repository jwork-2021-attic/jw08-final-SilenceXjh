package game.com.anish.calabashbros;

import java.awt.Color;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Calabash extends Creature implements Serializable {

    private static final long serialVersionUID = 4L;

    private int score = 0;

    private int lives = 3;

    KeyEvent key = null;
    int dir = 0;

    private int index;

    public Calabash(Color color, World world, int index) {
        super(color, (char) 200, world);
        this.index = index;
    }

    public boolean isDead() {
        return lives <= 0;
    }

    @Override
    public void run() {
        while (true) {
            if (lives <= 0) {
                world.clear(getX(), getY());
                //world.lose();
                System.out.println("Dead");
                //exec.shutdownNow();
                return;
            }
            if(score >= 2000) {
                world.win(index);
                return;
            }
            if(world.isEnd()) {
                return;
            }
            System.out.print("");
            int l = getLevel();
            if(l <= 7) {
                glyph = (char)(200+l);
            }

            if (dir != 0) {
                // System.out.println("key");
                if (dir == 1) {
                    // System.out.println("down");
                    if (getY() == World.HEIGHT - 1) {
                        dir = 0;
                        continue;
                    }
                        
                    synchronized (world.get(getX(), getY() + 1)) {
                        Thing t = world.get(getX(), getY() + 1);
                        if (t.getClass() == Monster.class) {
                            Monster mon = (Monster) t;
                            if (mon.getLevel() <= getLevel()) {
                                addScore(20 * mon.getLevel() + 20);
                                world.clear(getX(), getY());
                                world.clear(getX(), getY()+1);
                                moveTo(getX(), getY() + 1);
                                mon.die();
                            } else {
                                getHurt();
                            }
                        } else if(t.getClass() == Floor.class) {
                            world.clear(getX(), getY());
                            moveTo(getX(), getY() + 1);
                        }
                    }
                } else if (dir == 2) {
                    if (getY() == 6) {
                        dir = 0;
                        continue;
                    }
                        
                    synchronized (world.get(getX(), getY() - 1)) {
                        Thing t = world.get(getX(), getY() - 1);
                        if (t.getClass() == Monster.class) {
                            Monster mon = (Monster) t;
                            if (mon.getLevel() <= getLevel()) {
                                addScore(20 * mon.getLevel() + 20);
                                
                                mon.die();
                                world.clear(getX(), getY());
                                world.clear(getX(), getY()-1);
                                moveTo(getX(), getY() - 1);
                            } else {
                                getHurt();
                            }
                        } else if(t.getClass() == Floor.class){
                            world.clear(getX(), getY());
                            moveTo(getX(), getY() - 1);
                        }
                    }
                } else if (dir == 3) {
                    if (getX() == 0) {
                        dir = 0;
                        continue;
                    }
                        
                    synchronized (world.get(getX() - 1, getY())) {
                        Thing t = world.get(getX() - 1, getY());
                        if (t.getClass() == Monster.class) {
                            Monster mon = (Monster) t;
                            if (mon.getLevel() <= getLevel()) {
                                addScore(20 * mon.getLevel() + 20);
                                
                                mon.die();
                                world.clear(getX(), getY());
                                world.clear(getX()-1, getY());
                                moveTo(getX() - 1, getY());
                            } else {
                                getHurt();
                            }
                        } else if(t.getClass() == Floor.class) {
                            world.clear(getX(), getY());
                            moveTo(getX() - 1, getY());
                        }
                    }
                } else if (dir == 4) {
                    if (getX() == World.WIDTH - 1) {
                        dir = 0;
                        continue;
                    }
                        
                    synchronized (world.get(getX() + 1, getY())) {
                        Thing t = world.get(getX() + 1, getY());
                        if (t.getClass() == Monster.class) {
                            Monster mon = (Monster) t;
                            if (mon.getLevel() <= getLevel()) {
                                addScore(20 * mon.getLevel() + 20);
                                
                                mon.die();
                                world.clear(getX(), getY());
                                world.clear(getX()+1, getY());
                                moveTo(getX() + 1, getY());
                            } else {
                                getHurt();
                            }
                        } else if(t.getClass() == Floor.class) {
                            world.clear(getX(), getY());
                            moveTo(getX() + 1, getY());
                        }
                    }
                }
                dir = 0;
            }

        }
    }

    public void setKey(KeyEvent key) {

        this.key = key;
        // System.out.println("set key");
        // System.out.println(key != null);
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public int getLevel() {
        return score / 200;
    }

    public int getScore() {
        return score;
    }

    public synchronized void getHurt() {
        lives--;
        world.put(new Wall(world), lives, 2*index-2);
    }

    public synchronized void addScore(int score) {
        this.score += score;
        if(this.score > world.getGlobalScore()) {
            world.setGlobalScore(this.score);
        }
        world.setScore(this.score,index);
    }
}
