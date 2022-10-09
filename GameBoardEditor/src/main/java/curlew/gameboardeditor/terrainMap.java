package curlew.gameboardeditor;

import javafx.scene.shape.Box;

public class terrainMap {
	private Box[][] boxArray;
	private final double BOX_LENGHT = 2;
	private final double BOX_WIDTH = 2;
	private final double BOX_DEPTH = 2;
	private final double DEPTH_CHANGE = 2;
	private final double MAX_HEIGHT = 8;
	private final double MIN_HEIGHT = 0;
	
	public terrainMap(int length, int width) {
		boxArray = new Box[length][width];
		for (Box[] row:boxArray) {
			for(Box box:row) {
				box = new Box(BOX_WIDTH, BOX_LENGHT, BOX_DEPTH);
			}
		}	
	}
	
	public void dig(int row, int column, double decreaseDepth) {
		if(boxArray[row][column].getDepth()<=MIN_HEIGHT) {
			throw new IllegalArgumentException("You have reached the minimium depth");
		}
		boxArray[row][column].setDepth(boxArray[row][column].getDepth() - decreaseDepth); 
	}
	
	public void dig(int row, int column) {
		dig(row,column, DEPTH_CHANGE);
	}
	
	public void build(int row, int column, double increaseDepth) {
		if(boxArray[row][column].getDepth()>=MAX_HEIGHT) {
			throw new IllegalArgumentException("You have reached the maximium height");
		}
		boxArray[row][column].setDepth(boxArray[row][column].getDepth() + increaseDepth); 
	}
	
	public void build(int row, int column) {
		dig(row,column, DEPTH_CHANGE);
	}
	
	public Box getBox(int row, int column) {
		return boxArray[row][column];
	}
	

}
