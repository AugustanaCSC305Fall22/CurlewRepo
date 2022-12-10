package curlew.gameboardeditor.datamodel;

/**
 * 
 * @author Team Curlew
 * This class is responsible for doing all calculations regarding tiles in the Terrain Map.
 *
 */
public class Tile2DGeometry {
	
	/**
	 * The shapes of tiles in the Terrain Map
	 * @author Team Curlew
	 *
	 */
	public enum TileShape {
		SQUARE, HEXAGON;
	}
	

	private int row;
	private int col;
	private TileShape shape;
	
	/**
	 * Constructs a Tile2DGeometry object
	 * @param row The row where the its located in the Terrain Map
	 * @param col The col where the its located in the Terrain Map
	 * @param shape The shape of the tile
	 */
	public Tile2DGeometry(int row, int col, TileShape shape) {
		this.row = row;
		this.col = col;
		this.shape = shape;
	}
	
	/**
	 * Return the Row where the Tile2DGeometry is located in the Terrain Map
	 * @return the Row where the Tile2DGeometry is located in the Terrain Map
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Return the Col where the Tile2DGeometry is located in the Terrain Map
	 * @return the Col where the Tile2DGeometry is located in the Terrain Map
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Returns X Coordinates of vertices of the tile
	 * @param scalingFactor How big the tiles should be
	 * @return X Coordinates of vertices of the tile
	 */
	public double[] getPolygonXCoords(double scalingFactor) {
		if (shape == TileShape.SQUARE) {
			return new double[] { col*scalingFactor, (col + 1)*scalingFactor, (col + 1)*scalingFactor, col * scalingFactor};
		} else {
			return this.getHexXCoord(scalingFactor);
		}

	}
	
	/**
	 * Returns y Coordinates of vertices of the tile
	 * @param scalingFactor How big the tiles should be
	 * @return y Coordinates of vertices of the tile
	 */
	public double[] getPolygonYCoords(double scalingFactor) {
		if (shape == TileShape.SQUARE) {
			return new double[] { row*scalingFactor, row*scalingFactor, (row + 1)*scalingFactor, (row + 1)*scalingFactor };
		} else {
			return this.getHexYCoord(scalingFactor);
		}
	}
	
	/**
	 * Returns the shape of the Tile
	 * @return the shape of the Tile
	 */
	public TileShape getShape() {
		return this.shape;
	}
	
	
	@Override
	public int hashCode() {
		return Integer.hashCode(col)+Integer.hashCode(row);
	}
	
	@Override
	public boolean equals(Object object) {
		if(this==object) {
			return true;
		}
		else if(! (object instanceof Tile2DGeometry)) {
			return false;
		}else {
			Tile2DGeometry other = (Tile2DGeometry) object;
			return(this.row == other.getRow() && this.col == other.getCol());
		}
	}
	
	
	private double[] getXCoordOfTopLeftHex(double scale) {
		return new double[] {0.0, scale/2, scale, scale, scale/2, 0};
	}
	
	private double[] getYCoordOfTopLefttHex(double scale) {
		double sideLength = scale/(Math.tan(Math.PI/3));	
		return new double [] {sideLength/2, 0.0, sideLength/2, 3*sideLength/2, 2*sideLength,3*sideLength/2};
	}
	
	
	private double[] getHexYCoord(double scale) {
		double offSet = 3*(scale/(Math.tan(Math.PI/3)));
		double[] firstHexYCoord = getYCoordOfTopLefttHex(scale);
		int multiplyingFactor = row/2;
		double[] yCoord = new double[6];
		if(row % 2 == 0) {
			for(int i =0;i<6;i++) {
				yCoord[i]= firstHexYCoord[i]+multiplyingFactor*offSet;
			}
		} else {
			for(int i=0;i<6;i++) {
				yCoord[i]= firstHexYCoord[i]+offSet/2 + multiplyingFactor*offSet;
			}
		}
		return yCoord;
	}
	
	private double[] getHexXCoord(double scale) {
		double[] firstHexXCoord = getXCoordOfTopLeftHex(scale);
		double[] xCoord = new double[6];
		if(row %2 ==0) {
			for(int i=0;i<6;i++) {
				xCoord[i]= firstHexXCoord[i]+col*scale;
			}
		}else {
			for(int i=0;i<6;i++) {
				xCoord[i]= firstHexXCoord[i]+ (scale/2) + col*scale;
			}
		}
		return xCoord;
	}
	
	
	
	
}
