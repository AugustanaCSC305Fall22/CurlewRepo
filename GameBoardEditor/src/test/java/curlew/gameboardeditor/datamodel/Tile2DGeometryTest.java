package curlew.gameboardeditor.datamodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import curlew.gameboardeditor.datamodel.Tile2DGeometry.TileShape;

class Tile2DGeometryTest {

	private Tile2DGeometry makeTile2DGeometry(int type) {
		Tile2DGeometry newTile;
		if (type == 0) {
			newTile = new Tile2DGeometry(1,1, TileShape.SQUARE);
		} else {
			newTile = new Tile2DGeometry(1,1, TileShape.HEXAGON);
		}
		return newTile;
		
	}
	
	@Test
	void testTile2DGeometry() {
		Tile2DGeometry newHexagonTile = makeTile2DGeometry(1);
		assertEquals(TileShape.HEXAGON, newHexagonTile.getShape());
		Tile2DGeometry newSquareTile = makeTile2DGeometry(0);
		assertEquals(TileShape.SQUARE, newSquareTile.getShape());
	}

	@Test
	void testGetRow() {
		Tile2DGeometry newHexagonTile = makeTile2DGeometry(1);
		assertEquals(TileShape.HEXAGON, newHexagonTile.getShape());
		assertEquals(1, newHexagonTile.getRow());
		Tile2DGeometry newSquareTile = makeTile2DGeometry(0);
		assertEquals(TileShape.SQUARE, newSquareTile.getShape());
		assertEquals(1, newSquareTile.getRow());
	}

	@Test
	void testGetCol() {
		Tile2DGeometry newHexagonTile = makeTile2DGeometry(1);
		assertEquals(TileShape.HEXAGON, newHexagonTile.getShape());
		assertEquals(1, newHexagonTile.getCol());
		Tile2DGeometry newSquareTile = makeTile2DGeometry(0);
		assertEquals(TileShape.SQUARE, newSquareTile.getShape());
		assertEquals(1, newSquareTile.getCol());
	}

}
