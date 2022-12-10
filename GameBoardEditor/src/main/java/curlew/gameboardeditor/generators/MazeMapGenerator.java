package curlew.gameboardeditor.generators;

import java.util.Random;

import curlew.gameboardeditor.datamodel.TerrainMap;

public class MazeMapGenerator {
	private static double ALIVE = 8;
	private static double DEAD = 2;
	private TerrainMap map;
	
	/**
	 * Creates a object of MazeMapGenerator
	 * @param map The map on which the maze is to be build
	 */
	public MazeMapGenerator(TerrainMap map) {
		this.map = map;
	}
	
	/**
	 * Creates a randomized maze.
	 */
	public void genrateMaze() {
		Random rand = new Random();
		
		for(int i=0; i<map.getRows()*3; i++) {
			map.setHeightAt(rand.nextInt(map.getRows()), rand.nextInt(map.getColumns()), ALIVE);
		}
		for(int i=0;i<10000;i++) {
			nexGeneration();
		}	
	}
	
	private void nexGeneration() {
		double[][] nextGen = new double[map.getRows()][map.getColumns()];
		for (int i=0;i<map.getRows();i++) {
			for(int j=0; j<map.getColumns();j++ ) {
				int aliveNeibrs=countAliveNeighbours(i,j);
				if(map.getHeight(i, j)==ALIVE) {
					
					if(aliveNeibrs==0||aliveNeibrs>=5) {
						nextGen[i][j]=DEAD;
					}else {
						nextGen[i][j]=ALIVE;
					}
				}else {
					if(aliveNeibrs==3) {
						nextGen[i][j]=ALIVE;
					}
					else {
						nextGen[i][j]=DEAD;
					}
				}
			}
		}
		map.setHeightArray(nextGen);
	}
	
	private int countAliveNeighbours(int row, int column) {
		int count=0;
		for(int i= row-1; i<=row+1;i++) {
			for(int j=column-1;j<=column+1;j++) {
				try {
					double height =map.getHeight(i, j);
					if(height==ALIVE) {
						count++;
					}
				}catch (IndexOutOfBoundsException e) {}
			}
		}
		if(map.getHeight(row, column)==ALIVE) {
			count--;
		}
		return count;
	}
}
