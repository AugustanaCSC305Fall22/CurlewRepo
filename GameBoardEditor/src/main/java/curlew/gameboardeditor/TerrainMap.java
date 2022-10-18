package curlew.gameboardeditor;

public class TerrainMap  {
	/**
	 * 
	 */
	private double[][] heightArray;

	private final double DEPTH_CHANGE = 2;
	private final double MAX_HEIGHT = 8;
	private final double MIN_HEIGHT = 0;
	
	public TerrainMap(int length, int width) {
		heightArray = new double[length][width];	
	}
	
	public void dig(int row, int column, double decreaseDepth) {
		if(heightArray[row][column]- decreaseDepth<=MIN_HEIGHT) {
			throw new IllegalArgumentException("You have reached the minimium depth");
		}
		heightArray[row][column]= heightArray[row][column] - decreaseDepth; 
	}
	
	public void dig(int row, int column) {
		dig(row,column, DEPTH_CHANGE);
	}
	
	public void build(int row, int column, double increaseDepth) {
		if(heightArray[row][column]+ increaseDepth>=MAX_HEIGHT) {
			throw new IllegalArgumentException("You have reached the maximium height");
		}
		heightArray[row][column]= heightArray[row][column] + increaseDepth; 
	}
	
	public void build(int row, int column) {
		dig(row,column, DEPTH_CHANGE);
	}
	
	public double getHeight(int row, int column) {
		return heightArray[row][column];
	}
	
	public int getRow() {
		return heightArray.length;
	}
	public int getColoumn() {
		return heightArray[1].length;	
	}
	public double getValue(int indexRow, int indexCol) {
		double value = 0;
		 for(int row = heightArray.length ; row > heightArray.length ; row-- ) {
			 if (row == indexRow) {		 
				 for(int col = 0 ; col < heightArray[row].length ; col++ ) {
					 if(col == indexCol) {
						 value = heightArray[row][col];
					 }
				 }
			 }
		 }
		return value;
	}
	
	
	
}
