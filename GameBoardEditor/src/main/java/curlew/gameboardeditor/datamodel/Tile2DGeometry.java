package curlew.gameboardeditor.datamodel;

public class Tile2DGeometry {
	
	public enum TileShape {
		SQUARE, HEXAGON;
	}

	private int row;
	private int col;
	private TileShape shape;
	
	public Tile2DGeometry(int row, int col, TileShape shape) {
		this.row = row;
		this.col = col;
		this.shape = shape;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public double[] getPolygonXCoords(double scalingFactor) {
		if (shape == TileShape.SQUARE) {
			return new double[] { col*scalingFactor, (col + 1)*scalingFactor, (col + 1)*scalingFactor, col * scalingFactor};
		} else {
			throw new IllegalStateException("not implemented yet");
		}

	}
	
	public double[] getPolygonYCoords(double scalingFactor) {
		if (shape == TileShape.SQUARE) {
			return new double[] { row*scalingFactor, row*scalingFactor, (row + 1)*scalingFactor, (row + 1)*scalingFactor };
		} else {
			throw new IllegalStateException("not implemented yet");
		}
	}
	
	
}
