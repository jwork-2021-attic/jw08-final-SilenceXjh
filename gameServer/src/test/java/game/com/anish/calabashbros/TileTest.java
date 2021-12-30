package game.com.anish.calabashbros;

import org.junit.Test;

public class TileTest {

    private Tile tile = new Tile();

	@Test
	public void testGetThing() {
        tile.setThing(new Floor(new World()));
        assert(tile.getThing().getClass() == Floor.class);
	}

	@Test
	public void testGetxPos() {
        tile.setxPos(0);
        assert(tile.getxPos() == 0);
	}

	@Test
	public void testGetyPos() {
        tile.setyPos(0);
        assert(tile.getyPos() == 0);
	}

}
