package game.com.anish.calabashbros;

import java.awt.Color;

import org.junit.*;

public class MonsterTest {

        private World world = new World();
        private Monster mon = new Monster(new Color(204, 0, 0), world, 1, false);
        private Monster mon2 = new Monster(new Color(204, 0, 0), world, 2, false);

        @Test
        public void testGetDirection() {
                assert (mon.getDirection() == false);
        }

        @Test
        public void testGetLevel() {
                assert (mon.getLevel() == 1);
        }

        @Test
        public void testSwap() {
                world.put(mon, 1, 1);
                assert (mon.getY() == 1);
                world.put(mon2, 2, 2);
                mon.swap(mon2);
                assert (mon.getX() == 2);
        }
}
