package curlew.gameboardeditor.datamodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.UndoRedoAble;
import curlew.gameboardeditor.datamodel.Tile2DGeometry.TileShape;


class TerrainMapTest {

	private TerrainMap makeTerrainMap() {
		TerrainMap map = new TerrainMap(20, 20, TileShape.SQUARE);
		return map;
	}

	@Test
	void testSetHeightAt() {
		TerrainMap map = makeTerrainMap();
		map.setHeightAt(2, 2, 4);
		map.setHeightAt(4, 4, 8);
		assertEquals(8, ((int) map.getHeight(4, 4)));
		assertEquals(4, ((int) map.getHeight(2, 2)));
	}

	@Test
	void testIncreaseHeightAt() {
		TerrainMap map = makeTerrainMap();
		int prevHeight = (int) map.getHeight(1, 1);
		map.increaseHeightAt(1, 1);
		assertNotEquals(prevHeight, map.getHeight(1, 1));
		assertEquals(prevHeight + 2, map.getHeight(1, 1));
	}

	@Test
	void testDecreaseHeightAt() {
		TerrainMap map = makeTerrainMap();
		int prevHeight = (int) map.getHeight(1, 1);
		map.decreaseHeightAt(1, 1);
		assertNotEquals(prevHeight, map.getHeight(1, 1));
		assertEquals(prevHeight - 2, map.getHeight(1, 1));
	}

	@Test
	void testGetHeight() {
		TerrainMap map = makeTerrainMap();
		assertEquals(2, ((int) map.getHeight(2, 2)));
	}

	@Test
	void testGetRows() {
		TerrainMap map = makeTerrainMap();
		assertEquals(20, map.getRows());
	}

	@Test
	void testGetColumns() {
		TerrainMap map = makeTerrainMap();
		assertEquals(20, map.getColumns());
	}

	@Test
	void testGetInitialDepth() {
		TerrainMap map = makeTerrainMap();
		assertEquals(2, (int) map.getInitialDepth());
	}

	@Test
	void testGetDepthChange() {
		TerrainMap map = makeTerrainMap();
		assertEquals(2, (int) map.getDepthChange());
	}

	@Test
	void testSetHeightArray() {
		TerrainMap map = makeTerrainMap();
		double[][] newSizeArray= new double[10][10];
		map.setHeightArray((newSizeArray));
		double[][] testArray = map.getHeightArray();
		for (int i = 0; i < newSizeArray.length; i++) {
			for (int j = 0; j < newSizeArray[0][i]; j++) {
				assertEquals(newSizeArray[i][j], testArray[i][j]);	
			}
		}
	}

	@Test
	void testGetHeightArray() {
		TerrainMap map = makeTerrainMap();
		double[][] newSizeArray= new double[20][20];
		double[][] testArray = map.getHeightArray();
		assertEquals(newSizeArray[0].length, testArray[0].length);
		assertEquals(newSizeArray.length, testArray.length);
		
		
	}

	@Test
	void testAddRow() {
		TerrainMap map = makeTerrainMap();
		double[][] testArray = map.getHeightArray();
		map.addRow(1);
		assertNotEquals(testArray.length, map.getRows());
		assertEquals(testArray.length +1, map.getRows());
	}

	@Test
	void testDeleteRow() {
		TerrainMap map = makeTerrainMap();
		double[][] testArray = map.getHeightArray();
		map.deleteRow(1);
		assertNotEquals(testArray.length, map.getRows());
		assertEquals(testArray.length -1, map.getRows());
	}

	@Test
	void testAddColumn() {
		TerrainMap map = makeTerrainMap();
		double[][] testArray = map.getHeightArray();
		map.addColumn(1);
		assertNotEquals(testArray[0].length, map.getColumns());
		assertEquals(testArray[0].length + 1, map.getColumns());
	}

	@Test
	void testDeleteColumn() {
		TerrainMap map = makeTerrainMap();
		double[][] testArray = map.getHeightArray();
		map.deleteColumn(1);
		assertNotEquals(testArray[0].length, map.getColumns());
		assertEquals(testArray[0].length - 1, map.getColumns());
	}

	@Test
	void testGetClone() {
		TerrainMap map = makeTerrainMap();
		UndoRedoAble clone = map.getClone();
		map.setHeightAt(2, 2, 4);
		map.setHeightAt(4, 4, 8);
		int mapHeight22 = (int) map.getHeight(2, 2);
		int cloneHeight22 = (int) ((TerrainMap) clone).getHeight(2, 2);
		int mapHeight44 = (int) map.getHeight(4, 4);
		int cloneHeight44 = (int) ((TerrainMap) clone).getHeight(4, 4);
		assertNotEquals(mapHeight22, cloneHeight22);
		assertEquals(mapHeight22, cloneHeight22 + 2);
		assertNotEquals(mapHeight44, cloneHeight44);
		assertEquals(mapHeight44, cloneHeight44 + 6);
	}

	@Test
	void testSetState() {
		TerrainMap map = makeTerrainMap();		
		UndoRedoAble clone = map.getClone();
		map.addColumn(2);
		map.addRow(2);
		map.setState(clone);
		TerrainMap castClone = (TerrainMap) clone;
		assertEquals(map.getRows(), castClone.getRows());
		assertEquals(map.getColumns(), castClone.getColumns());
	}

}
