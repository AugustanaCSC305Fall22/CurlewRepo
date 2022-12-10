package curlew.gameboardeditor.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.Tile2DGeometry;
import curlew.gameboardeditor.datamodel.UndoRedoHandler;
import curlew.gameboardeditor.generators.LandformGenerator;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import curlew.gameboardeditor.datamodel.Tile2DGeometry.TileShape;

class TwoDMapEditorTest {

	private TwoDMapEditor makeTwoDMapEditor() {
		Canvas canvas = new Canvas();
		canvas.setHeight(400.0);
		canvas.setWidth(400);
		TerrainMap map = makeTerrainMap();
		App.setMap(map);
		TwoDMapEditor mapEditor = new TwoDMapEditor(canvas);
		return mapEditor;
	}

	private void addToShapeSet(TwoDMapEditor mapEditor, int row, int column) {
		HashSet<Tile2DGeometry> selectedShapeSet = mapEditor.getSelectedShapeSet();
		Tile2DGeometry tile = new Tile2DGeometry(row, column, TileShape.SQUARE);
		selectedShapeSet.add(tile);
		
	}
	private TerrainMap makeTerrainMap() {
		TerrainMap map = new TerrainMap(20, 20, TileShape.SQUARE);
		return map;
	}
	
	@Test
	void testTwoDMapEditor() {
		Canvas canvas = new Canvas();
		canvas.setHeight(400.0);
		canvas.setWidth(400);
		TwoDMapEditor mapEditorTest = new TwoDMapEditor(canvas);
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		assertTrue(mapEditorTest instanceof TwoDMapEditor);
		assertTrue(mapEditor instanceof TwoDMapEditor);
	}

	@Test 
	void testRaiseTile() {
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 1, 1);
		addToShapeSet(mapEditor, 3, 3);
		addToShapeSet(mapEditor, 5, 5);
		mapEditor.raiseTile();
		TerrainMap map = App.getMap();
		assertEquals(4, map.getHeight(5, 5));
		assertEquals(3, mapEditor.getSelectedShapeSet().size());		
	}

	@Test
	void testRaiseTileInt() {
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 5, 5);
		mapEditor.raiseTile(6);
		TerrainMap map = App.getMap();
		assertEquals(1, mapEditor.getSelectedShapeSet().size());
		assertEquals(6, map.getHeight(5, 5));
	}

	@Test
	void testLowerTile() {
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 1, 1);
		addToShapeSet(mapEditor, 3, 3);
		addToShapeSet(mapEditor, 5, 5);
		mapEditor.lowerTile();
		TerrainMap map = App.getMap();
		assertEquals(0, map.getHeight(5, 5));
		assertEquals(3, mapEditor.getSelectedShapeSet().size());
	}

	@Test
	void testUnSelectAllPoints() {
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 1, 1);
		addToShapeSet(mapEditor, 3, 3);
		addToShapeSet(mapEditor, 5, 5);
		assertEquals(3, mapEditor.getSelectedShapeSet().size());
		mapEditor.unSelectAllPoints();
		assertEquals(0, mapEditor.getSelectedShapeSet().size());
	}

	@Test
	void testAddRow() {
		TerrainMap map = makeTerrainMap();
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 2, 2);
		mapEditor.addRow();
		map = App.getMap();
		assertEquals(21, map.getRows());
	}

	@Test
	void testDeleteRow() {
		TerrainMap map = makeTerrainMap();
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 2, 2);
		mapEditor.deleteRow();
		map = App.getMap();
		assertEquals(19, map.getRows());
	}

	@Test
	void testAddColumn() {
		TerrainMap map = makeTerrainMap();
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 2, 2);
		mapEditor.addColumn();
		map = App.getMap();
		assertEquals(21, map.getColumns());
	}
	
	@Test
	void testDeleteColumn() {
		TerrainMap map = makeTerrainMap();
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 2, 2);
		mapEditor.deleteColumn();
		map = App.getMap();
		assertEquals(19, map.getColumns());
	}

	@Test
	void testSelectSameHeight() {
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 1, 1);
		addToShapeSet(mapEditor, 2, 2);
		addToShapeSet(mapEditor, 3, 3);
		mapEditor.raiseTile();
		mapEditor.unSelectAllPoints();
		addToShapeSet(mapEditor, 1, 1);
		mapEditor.selectSameHeight();
		assertEquals(3, mapEditor.getSelectedShapeSet().size());
	}

	@Test
	void testUndo() {
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 5, 5);
		mapEditor.raiseTile(6);
		TerrainMap map = App.getMap();
		assertEquals(6, map.getHeight(5, 5));
		mapEditor.undo();
		map = App.getMap();
		assertEquals(2, map.getHeight(5, 5));
	}

	@Test
	void testRedo() {
		TwoDMapEditor mapEditor = makeTwoDMapEditor();
		addToShapeSet(mapEditor, 5, 5);
		mapEditor.raiseTile(6);
		mapEditor.undo();
		TerrainMap map = App.getMap();
		assertEquals(2, map.getHeight(5, 5));
		mapEditor.redo();
		map = App.getMap();
		assertEquals(6, map.getHeight(5, 5));
		
	}

}
