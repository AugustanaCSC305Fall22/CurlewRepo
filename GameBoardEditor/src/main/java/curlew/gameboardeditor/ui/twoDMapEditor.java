package curlew.gameboardeditor.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import curlew.gameboardeditor.datamodel.TerrainMap;

public class twoDMapEditor {
	
	private int canvasLength;

	private int canvasWidth;
	
	public int numRows;
	
	public int numCols;
	
	public int tileLengthSize;

	public int tileWidthSize;
	
	public TerrainMap map;
	
	private double x;
	
	private double y;
	

	/**
	 * Constructs twoDMapEditor by combining the terrainMap and the twoDMapEditor
	 * Then it sets the data fields from both objects to this object to be used in the MapEditorController
	 * @param map
	 * @param twoDCanvas
	 */
	public twoDMapEditor(TerrainMap map, Canvas twoDCanvas) {
		this.map = map;
		numRows = map.getRows();
		numCols = map.getColumns();
		canvasLength = (int) twoDCanvas.getHeight();
		canvasWidth = (int) twoDCanvas.getWidth();
		tileLengthSize = canvasLength / numRows;
		tileWidthSize = canvasWidth / numCols;
	
	}
	/**
	 * @return the canavsLength data field
	 */
	public int getCanvasLength() {
		return canvasLength;
	}

	/**
	 * Sets the canvas length to the data field
	 * @param canvasLength
	 */
	public void setCanvasLength(int canvasLength) {
		this.canvasLength = canvasLength;
	}

	/**
	 * @return the canvasWidth data field
	 */
	public int getCanvasWidth() {
		return canvasWidth;
	}
	/**
	 * @return the number of rows
	 */
	public int getNumRows() {
		return numRows;
	}
	/**
	 * @return the numer of columns
	 */
	public int getNumCols() {
		return numCols;
	}
	/**
	 * @return the tileLengthSize data field
	 */
	public int getTileLengthSize() {
		return tileLengthSize;
	}

	public int getTileWidthSize() {
		return tileWidthSize;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	@Override
	public String toString() {
		return "twoDMapEditor [canvasLength=" + canvasLength + ", canvasWidth=" + canvasWidth + ", numRows=" + numRows
				+ ", numCols=" + numCols +  tileLengthSize + tileWidthSize
				+ ", map=" + map + ", x=" + x + ", y=" + y + "]";
	}	
	
}
