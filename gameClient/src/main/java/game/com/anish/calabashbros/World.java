package game.com.anish.calabashbros;

import java.io.Serializable;

public class World implements Serializable {

    private static final long serialVersionUID = 2L;
    public static final int WIDTH = 22;
    public static final int HEIGHT = 24;

    private Tile<Thing>[][] tiles;

    private boolean end = false;

    public World() {

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        for (int i = 0; i < World.WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
            }
        }

        for(int i = 0; i < World.WIDTH; ++i) {
            for(int j = 0; j < 6; ++j) {
                tiles[i][j].setThing(new Wall(this));
            }
        }

        for(int i = 0; i < World.WIDTH; ++i) {
            for(int j = 6; j < World.HEIGHT; ++j) {
                tiles[i][j].setThing(new Floor(this));
            }
        }

    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

    public void clear(int x, int y) {
        this.tiles[x][y].setThing(new Floor(this));
    }

    public void setScore(int score,int index) {
        for(int i = 0; i < 4; ++i) {
            int d = score % 10;
            score = score / 10;
            put(new Digit(this, (char)(d+'0')), World.WIDTH - 1 - i, 2 * (index - 1));
        }
    }

    public void setHeart(int index) {
        for(int i = 0; i < 3; ++i) {
            put(new Heart(this), i, 2*index-2);
        }
    }

    public void win(int index) {
        put(new Digit(this, 'P'), 4, 5);
        put(new Digit(this, 'L'), 5, 5);
        put(new Digit(this, 'A'), 6, 5);
        put(new Digit(this, 'Y'), 7, 5);
        put(new Digit(this, 'E'), 8, 5);
        put(new Digit(this, 'R'), 9, 5);
        put(new Digit(this, (char)('0'+index)), 10, 5);

        put(new Digit(this, 'W'), 12, 5);
        put(new Digit(this, 'I'), 13, 5);
        put(new Digit(this, 'N'), 14, 5);
        put(new Digit(this, '!'), 15, 5);

        end = true;
    }

    public boolean isEnd() {
        return end;
    }

    public void lose() {
        put(new Digit(this, 'Y'), 6, 1);
        put(new Digit(this, 'O'), 7, 1);
        put(new Digit(this, 'U'), 8, 1);
        put(new Digit(this, 'L'), 10, 1);
        put(new Digit(this, 'O'), 11, 1);
        put(new Digit(this, 'S'), 12, 1);
        put(new Digit(this, 'E'), 13, 1);
        put(new Digit(this, '!'), 14, 1);
    }

    private int globalScore = 0;

    public int getGlobalScore() {
        return globalScore;
    }

    public void setGlobalScore(int score) {
        this.globalScore = score;
    }

}
