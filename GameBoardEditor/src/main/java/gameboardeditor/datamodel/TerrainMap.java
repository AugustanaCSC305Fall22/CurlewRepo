package curlew.gameboardeditor;

import java.util.Random;

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
		if(heightArray[row][column]+ increaseDepth>MAX_HEIGHT) {
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
		return heightArray[indexRow][indexCol];
//		double value = 0;
//		 for(int row = heightArray.length ; row > heightArray.length ; row-- ) {
//			 if (row == indexRow) {		 
//				 for(int col = 0 ; col < heightArray[row].length ; col++ ) {
//					 if(col == indexCol) {
//						 value = heightArray[row][col];
//					 }
//				 }
//			 }
//		 }
//		return value;
	}
	
	public TerrainMap genRandMap() {
		TerrainMap randomMap = new TerrainMap(8,8);
		Random rand = new Random();
		int row = randomMap.getRow();
		int col = randomMap.getColoumn();
		for (int i = 0; i < 1; i++) {
			int randRowIndex = rand.nextInt(row);
			int randColIndex = rand.nextInt(col);
			randomMap.build(randRowIndex, randColIndex);
		}
		for (int j = 0; j < 1; j++) {
			int randRowIndex = rand.nextInt(row);
			int randColIndex = rand.nextInt(col);
			randomMap.dig(randRowIndex, randColIndex);
		}
		return randomMap;
	}
	
	public double getInitialDepth() {
		return INITIAL_DEPTH;
	}
	
	public double getDepthChange() {
		return DEPTH_CHANGE;
	}
	
}
