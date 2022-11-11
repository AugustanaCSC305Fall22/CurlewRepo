package curlew.gameboardeditor.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import curlew.gameboardeditor.datamodel.TerrainMap;

public class twoDMapEditor {
	
	public int canvasLength;

	public int canvasWidth;
	
	public int numRows;
	
	public int numCols;
	
	public int boxLengthSize;

	public int boxWidthSize;
	
	public TerrainMap map;
	
	public double x;
	
	public double y;
	

	
	public twoDMapEditor(TerrainMap map, Canvas twoDCanvas) {
		this.map = map;
		numRows = map.getRows();
		numCols = map.getColumns();
		canvasLength = (int) twoDCanvas.getHeight();
		canvasWidth = (int) twoDCanvas.getWidth();
		boxLengthSize = canvasLength / numRows;
		boxWidthSize = canvasWidth / numCols;
	
	}
	public int getCanvasLength() {
		return canvasLength;
	}


	public void setCanvasLength(int canvasLength) {
		this.canvasLength = canvasLength;
	}


	public int getCanvasWidth() {
		return canvasWidth;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}
	
	public int getBoxLengthSize() {
		return boxLengthSize;
	}

	public int getBoxWidthSize() {
		return boxWidthSize;
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
				+ ", numCols=" + numCols + ", boxLengthSize=" + boxLengthSize + ", boxWidthSize=" + boxWidthSize
				+ ", map=" + map + ", x=" + x + ", y=" + y + "]";
	}	
	
}
