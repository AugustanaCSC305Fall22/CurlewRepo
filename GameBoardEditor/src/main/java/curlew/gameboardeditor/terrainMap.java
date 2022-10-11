package curlew.gameboardeditor;

import java.io.Serializable;

import javafx.scene.shape.Box;

public class terrainMap  {
	/**
	 * 
	 */
	private double[][] heightArray;

	private final double DEPTH_CHANGE = 2;
	private final double MAX_HEIGHT = 8;
	private final double MIN_HEIGHT = 0;
	
	public terrainMap(int length, int width) {
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
	
	public int getLength() {
		return heightArray.length;
	}
	public int getWidth() {
		return heightArray[1].length;
	}
	
}
