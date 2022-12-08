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
			return this.getHexXCoord(scalingFactor);
		}

	}
	
	public double[] getPolygonYCoords(double scalingFactor) {
		if (shape == TileShape.SQUARE) {
			return new double[] { row*scalingFactor, row*scalingFactor, (row + 1)*scalingFactor, (row + 1)*scalingFactor };
		} else {
			return this.getHexYCoord(scalingFactor);
		}
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
	
	
	private double[] getXCoordOfFirstHex(double scale) {
		return new double[] {0.0, scale/2, scale, scale, scale/2, 0};
	}
	
	private double[] getYCoordOfFirstHex(double scale) {
		double sideLength = scale/(Math.tan(Math.PI/3));	
		return new double [] {sideLength/2, 0.0, sideLength/2, 3*sideLength/2, 2*sideLength,3*sideLength/2};
	}
	
	private double[] getHexYCoord(double scale) {
		double offSet = 3*(scale/(Math.tan(Math.PI/3)));
		double[] firstHexYCoord = getYCoordOfFirstHex(scale);
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
		double[] firstHexXCoord = getXCoordOfFirstHex(scale);
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
	
	public TileShape getShape() {
		return this.shape;
	}
	
}
