package curlew.gameboardeditor.datamodel;

public class TerrainMap  {

	private double[][] heightArray;
	private final static double INITIAL_DEPTH = 2;
	private final static double DEPTH_CHANGE = 2;
	private final static double MAX_HEIGHT = 6*DEPTH_CHANGE;
	private final static double MIN_HEIGHT = 0;
	
	/**
	 * Constuctor for the TerrainMap object, this makes an array to keep track of the height and iterates through each tile in
	 * the 2d array and sets the height to INITIAL_DEPTH
	 * @param length
	 * @param width
	 */
	public TerrainMap(int length, int width) {
		heightArray = new double[length][width];
		for(int i=0; i<length;i++) {
			for(int j=0;j<width;j++) {
			heightArray[i][j]= INITIAL_DEPTH;
			}
		}
	}
	/**
	 * lowers the elevation of the tile at the selected row and column
	 * @param row
	 * @param column
	 */
	public void lowerTile(int row, int column) {
		build(row,column,heightArray[row][column]- DEPTH_CHANGE);
	}
	/**
	 * raises the elevation of the tile at the selected row and column to the newDepth
	 * @param row
	 * @param column
	 * @param newDepth 
	 */
	public void build(int row, int column, double newDepth) {
		if(newDepth>MAX_HEIGHT||newDepth<MIN_HEIGHT) {
			throw new IllegalArgumentException("You have reached the maximium height");
		}
		try {
		heightArray[row][column]= newDepth; 
		} catch (IndexOutOfBoundsException e){}
	}
	
	/**
	 * raises the elevation of the tile at the selected row and column by DEPTH_CHANGE
	 * @param row
	 * @param column
	 */
	public void build(int row, int column) {
		build(row,column, heightArray[row][column] +DEPTH_CHANGE);
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
	

	
}
