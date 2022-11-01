package curlew.gameboardeditor.datamodel;

public class TerrainMap  {
	/**
	 * 
	 */
	private double[][] heightArray;
	private final static double INITIAL_DEPTH = 2;
	private final static double DEPTH_CHANGE = 2;
	private final static double MAX_HEIGHT = 6*DEPTH_CHANGE;
	private final static double MIN_HEIGHT = 0;
	
	
	public TerrainMap(int length, int width) {
		heightArray = new double[length][width];
		for(int i=0; i<length;i++) {
			for(int j=0;j<width;j++) {
			heightArray[i][j]= INITIAL_DEPTH;
			}
		}
	}
	
	public void dig(int row, int column) {
		build(row,column,heightArray[row][column]- DEPTH_CHANGE);
	}
	
	public void build(int row, int column, double newDepth) {
		if(newDepth>MAX_HEIGHT||newDepth<MIN_HEIGHT) {
			throw new IllegalArgumentException("You have reached the maximium height");
		}
		try {
		heightArray[row][column]= newDepth; 
		} catch (IndexOutOfBoundsException e){}
	}
	
	
	public void build(int row, int column) {
		build(row,column, heightArray[row][column] +DEPTH_CHANGE);
	}
	
	
	public double getHeight(int row, int column) {
		return heightArray[row][column];
	}
	
	public int getRows() {
		return heightArray.length;
	}
	public int getColumns() {
		return heightArray[0].length;	
	}
	public double getValue(int indexRow, int indexCol) {
		return heightArray[indexRow][indexCol];

	}
	
	
	public double getInitialDepth() {
		return INITIAL_DEPTH;
	}
	
	public double getDepthChange() {
		return DEPTH_CHANGE;
	}
	
}
