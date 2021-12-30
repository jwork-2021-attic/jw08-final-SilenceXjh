package game.com.anish.calabashbros;

import org.junit.Test;

public class WorldTest {

        private World world = new World();

        @Test
        public void testClear() {
                world.clear(0, 0);
                Thing t = world.get(0, 0);
                assert (t.getClass() == Floor.class);
        }

        @Test
        public void testGet() {
                Thing t = world.get(World.WIDTH - 1, World.HEIGHT - 1);
                assert (t.getClass() == Floor.class);
        }

        @Test
        public void testPut() {
                world.put(new Floor(world), 0, 0);
                Thing t = world.get(0, 0);
                assert (t.getClass() == Floor.class);
        }

        @Test
        public void testSetScore() {
                world.setScore(1000, 1);
                Thing t = world.get(World.WIDTH - 1, 0);
                assert (t.getGlyph() == '0');
        }

}
