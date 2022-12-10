package curlew.gameboardeditor.datamodel;

import curlew.gameboardeditor.datamodel.Tile2DGeometry.TileShape;

/**
 * 
 * @author Team Curlew
 * This class holds information regarding the map/data model and is responsible for editing it.
 *
 */
public class TerrainMap implements UndoRedoAble {


	private Tile2DGeometry.TileShape tileShape; //= Tile2DGeometry.TileShape.HEXAGON;
	
	private double[][] heightArray;
	
	private final static double INITIAL_DEPTH = 2;
	private final static double DEPTH_CHANGE = 2;
	private final static double MAX_HEIGHT = 6*DEPTH_CHANGE;
	private final static double MIN_HEIGHT = 0;
	
	/**
	 * this makes an array to keep track of the height and iterates through each tile in
	 * the 2d array and sets the height to INITIAL_DEPTH
	 */
	public TerrainMap(int numRows, int numColumns,Tile2DGeometry.TileShape shape) {
		heightArray = new double[numRows][numColumns];
		tileShape = shape;
		for(int row=0; row<numRows;row++) {
			for(int col=0;col<numColumns;col++) {
			heightArray[row][col]= INITIAL_DEPTH;
			}
		}
	}
	
	/**
	 * Returns the shape of the map
	 * @return The current shape of the map
	 */
	public Tile2DGeometry.TileShape getTileShape() {
		return tileShape;
	}
	
	/**
	 * Sets the map shape to the shape provided in the argument
	 * @param shape The shape to set the map to
	 */
	public void setTileShape(Tile2DGeometry.TileShape shape) {
		tileShape = shape;
	}
	
	/**
	 * Returns the Tile2DGeometry at a given row and column
	 * @param row Number of row
	 * @param col Number of col
	 * @return The Tile2DGeometry at a given row and column
	 */
	public Tile2DGeometry getShapeAt(int row, int col) {
		return new Tile2DGeometry(row, col, tileShape);
	}

	/**
	 * For a point x,y in space, which tile (represented by a TileGeometry object) is that point inside?
	 * @param x
	 * @param y
	 * @param scalingFactor
	 */
	public Tile2DGeometry getTileGeometryContaining(double x, double y, double scalingFactor) {
		if (tileShape == TileShape.SQUARE) {
			return new Tile2DGeometry((int) (y/scalingFactor), (int) (x/scalingFactor), tileShape);
		} else {
			return getHexContaining(x,y,scalingFactor);
		}
	}
	
	// Return the Tile2DGeometry which woul contain the x and y coordinates
	private Tile2DGeometry getHexContaining(double x, double y, double scalingFactor) {
		int probableCol = (int) (x/scalingFactor);
		double heightFactor = 3*scalingFactor/Math.tan(Math.PI/3);
		int probableRow =((int)(y/heightFactor))*2;
		double mindis = 10000;
		Tile2DGeometry selectedHex = getShapeAt(-1,-1);
		System.out.println(probableRow +" "+ probableCol);
		for(int row=probableRow-1;row<=probableRow+1;row++) {
			for(int col=probableCol-1;col<=probableCol;col++) {
				Tile2DGeometry hexGeometry = this.getShapeAt(row, col);
				double[] xCoord = hexGeometry.getPolygonXCoords(scalingFactor);
				double[] yCoord = hexGeometry.getPolygonYCoords(scalingFactor);
				
				double midx = (xCoord[1]+xCoord[4])/2;
				double midy = (yCoord[1]+yCoord[4])/2;
				double distance = Math.sqrt((midx-x)*(midx-x)+(midy-y)*(midy-y));
				
				System.out.println(distance);
				if(distance <= mindis) {
					mindis = distance;
					selectedHex = hexGeometry;
				}
			}
		}
		return selectedHex;
	}
	
	
	/**
	 * sets elevation of the tile at the specified row and column
	 * @param row
	 * @param column
	 * @param newHeight 
	 */
	public void setHeightAt(int row, int column, double newHeight) {
		if(newHeight>MAX_HEIGHT||newHeight<MIN_HEIGHT) {
			throw new IllegalArgumentException("You have reached the maximium height");
		}
		try {
		heightArray[row][column]= newHeight; 
		} catch (IndexOutOfBoundsException e){}
	}
	
	/**
	 * raises the elevation of the specified tile by DEPTH_CHANGE
	 * @param row
	 * @param column
	 */
	public void increaseHeightAt(int row, int column) {
		setHeightAt(row,column, heightArray[row][column] +DEPTH_CHANGE);
	}
	
	/**
	 * lowers the elevation of the tile at the selected row and column
	 * @param row
	 * @param column
	 */
	public void decreaseHeightAt(int row, int column) {
		setHeightAt(row,column,heightArray[row][column]- DEPTH_CHANGE);
	}

	/**
	 * @param row
	 * @param column
	 * @return the height from the heightArray at the selected row and column
	 */
	public double getHeight(int row, int column) {
		return heightArray[row][column];
	}
	/**
	 * @return the total number of rows
	 */
	public int getRows() {
		return heightArray.length;
	}
	/**
	 * @return the total number of columns
	 */
	public int getColumns() {
		return heightArray[0].length;	
	}
	/**
	 * @return the INITIAL_DEPTH constant
	 */
	public double getInitialDepth() {
		return INITIAL_DEPTH;
	}
	/**
	 * @return the get DEPTH_CHANGE constant
	 */
	public double getDepthChange() {
		return DEPTH_CHANGE;
	}
	/**
	 * sets the height array to the array data field
	 * @param array
	 */
	public void setHeightArray(double[][] array) {
		heightArray = array;
	}
	
	/**
	 * Returns the height Array
	 * @return the heightArray
	 */
	public double[][] getHeightArray(){
		return heightArray;
	}
	
	/**
	 * Adds a row after the given row
	 * @param row The number of row where we need to add a new row
	 */
	public void addRow(int row) {
		if(row<0||row>=getRows()) {
			throw new IllegalArgumentException();
		}
		double[][] newArray = new double[getRows()+1][getColumns()];
		
		for(int i=0;i<=row;i++) {
			for(int j=0;j<getColumns();j++) {
				newArray[i][j]=heightArray[i][j];
			}
		}
		
		for(int i=row+1;i<getRows();i++) {
			for(int j=0;j<getColumns();j++) {
				newArray[i+1][j]=heightArray[i][j];
			}
		}
		heightArray = newArray;
	}
	
	/**
	 * Deletes the given row
	 * @param row The row to delete
	 */
	public void deleteRow(int row) {
		if(row<0||row>=getRows()) {
			throw new IllegalArgumentException();
		}
		
		double[][] newArray = new double[getRows()-1][getColumns()];
			
		for(int i=0;i<row;i++) {
			for(int j=0;j<getColumns();j++) {
				newArray[i][j]=heightArray[i][j];
			}
		}
			
		for(int i=row+1;i<getRows();i++) {
			for(int j=0;j<getColumns();j++) {
				newArray[i-1][j]=heightArray[i][j];
			}
		}
		heightArray = newArray;
	}
	
	/**
	 * Adds a column after the given column.
	 * @param column The column after which we add new column
	 */
	public void addColumn(int column) {
		if(column<0||column>=getColumns()) {
			throw new IllegalArgumentException();
		}
		double[][] newArray = new double[getRows()][getColumns()+1];
		
		for(int i=0;i<getRows();i++) {
			for(int j=0;j<=column;j++) {
				newArray[i][j]=heightArray[i][j];
			}
			for(int j=column+1;j<getColumns();j++) {
				newArray[i][j+1]=heightArray[i][j];
			}
		}
		
		
		heightArray = newArray;
	}
	
	
	/**
	 * Deletes the given column
	 * @param column The column to delete
	 */
	public void deleteColumn(int column) {
		
		if(column<0||column>=getColumns()) {
			throw new IllegalArgumentException();
		}
		double[][] newArray = new double[getRows()][getColumns()-1];
		
		for(int i=0;i<getRows();i++) {
			for(int j=0;j<column;j++) {
				newArray[i][j]=heightArray[i][j];
			}
			for(int j=column+1;j<getColumns();j++) {
				newArray[i][j-1]=heightArray[i][j];
			}
		}
		
		
		heightArray = newArray;
		
	}
	

	@Override
	public UndoRedoAble getClone() {
		TerrainMap clone = new TerrainMap(getRows(),getColumns(), tileShape);
		for(int i=0;i<getRows();i++) {
			for(int j=0;j<getColumns();j++) {
				clone.setHeightAt(i, j, heightArray[i][j]);
			}
		}
		return clone;
	}

	@Override
	public void setState(UndoRedoAble state) {
		TerrainMap newMap = (TerrainMap) state;
		heightArray=newMap.getHeightArray();
		tileShape = newMap.getTileShape(); 

	}

	
}
